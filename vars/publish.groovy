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
    def artifacts = []
    build_info.eachWithIndex { it, i ->
        stage(it["name"]) {
            if(it["name"] == "docker" && it["image"].containsKey("name") && it["image"].containsKey("tag")){
                def docker_img = it["image"]["name"]
                def docker_tag = it["image"]["tag"]
                echo("docker name ${docker_img}")
                echo("docker name ${docker_tag}")
                echo "docker build -t ${docker_img}:${docker_tag} ."
                echo "end exec"
            }
        }
    }
}