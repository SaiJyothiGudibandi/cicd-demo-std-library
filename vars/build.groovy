def call(body){
    body()
    node {
        stage("Build") {
            echo "codeBuild code"
        }
    }
}