def call(body){
    body()
    node {
        stage("Build") {
            echo "build code"
        }
    }
}