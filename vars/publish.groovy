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
        echo $build_info
        echo "end publish"
    }
}