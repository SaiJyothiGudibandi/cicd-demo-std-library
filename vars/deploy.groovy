def call(body){
    body()
    node {
        stage("Deploy") {
            sh "echo 'deploy stage"
        }
    }
}