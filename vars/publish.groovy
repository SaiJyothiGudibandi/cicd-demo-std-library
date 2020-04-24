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
            echo("Executing ${it["name"]}")
            echo "end exec"
        }
    }
}