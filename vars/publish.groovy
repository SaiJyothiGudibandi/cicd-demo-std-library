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
    }
}
