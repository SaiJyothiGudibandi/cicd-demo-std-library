def call(body){
    node {
        stage(deploy) {
            sh "echo 'deploy stage"
        }
    }
}