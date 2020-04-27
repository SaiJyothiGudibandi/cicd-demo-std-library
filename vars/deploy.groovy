def call(Map config) {
    def yaml_file = config.yamlDeploy
    def yaml_publish_file = config.yamlPublish
    def deploy_info = []
    def publish_info = []
    node {
        echo("YAML FILE ${yaml_file}")
        if (yaml_file == "") {
            echo("Didn't find ${yaml_file}")
            exit 0
        } else {
            deploy_info = readYaml file: yaml_file
            publish_info = readYaml file: yaml_publish_file
            executePublishArtifactory(publish_info, deploy_info)
            //executeDeploy(deploy_info)
        }
    }
}


def executePublishArtifactory(publish_info, List deploy_info) {
    def flag
    publish_info.eachWithIndex { it, i ->
        if (it["name"] == "publish") {
            flag = it["flag"]
            if(flag){
                deploy_info.eachWithIndex { dep, j ->
                    if(dep["name"] == "deploy"){
                        stage("Deploy-To-GKE") {
                            echo "Deploy Helm Chart to GKE Cluster"
                        }
                    }
                }
            }
            else{
                echo "NO DEPLOY"
            }
        }
    }
}

/*
def executeDeploy(deploy_info) {
    deploy_info.eachWithIndex { it, i ->
        if(it["name"] == "deploy"){
                stage("Deploy-To-GKE") {
                        echo "Deploy Helm Chart to GKE Cluster"
                    }
            }
        }
    }
 */