def call(body){
    body()
    node {
        stage(deploy) {
            sh "echo 'deploy stage"
        }
    }
}