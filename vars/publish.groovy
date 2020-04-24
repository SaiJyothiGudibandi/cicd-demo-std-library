def call(body) {
    def config = [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()
    def yaml_file = config.yamlConfig
    def build_info = []
    node {
        echo("YAML FILE ${yaml_file}")
        if (yaml_file == ""){
            build_info = readYaml file: "test-info.yaml"
        } else {
            build_info = readYaml file: yaml_file
        }
        executeBuildConfig(build_info)

    }
}
def executeBuildConfig(build_info) {
    build_info.eachWithIndex { it, i ->
            if(it["name"] == "docker" && it["image"].containsKey("name") && it["image"].containsKey("tag") && it["artifactory"].containsKey('url')){
                stage("Build-Docker-Image") {
                    def docker_img = it["image"]["name"]
                    def docker_tag = it["image"]["tag"]
                    echo "docker build -t ${docker_img}:${docker_tag} ."
                }
                stage("Publish-Docker-Image-To-Artifactory"){
                    def docker_arti_url = it["artifactory"]["url"]
                    echo "Publish Docker image to ${docker_arti_url}"
                }


            }
            if(it["name"] == "helm" && it["chart"].containsKey("name") && it["image"].containsKey("version")){
                stage("Build-Helm-Chart") {
                    def helm_chart_name = it["chart"]["name"]
                    def helm_chart_version = it["chart"]["version"]
                    echo("Helm chart name ${helm_chart_name}")
                    echo("Helm chart name ${helm_chart_version}")
                    echo "Build Helm Chart ${helm_chart_name}:${helm_chart_version}"
                }
                stage("Publish-Helm-Chart-To-Artifactory") {
                    def docker_arti_url = it["artifactory"]["url"]
                    echo "Publish Helm Chart  to ${docker_arti_url}"
                }
            }
    }
}