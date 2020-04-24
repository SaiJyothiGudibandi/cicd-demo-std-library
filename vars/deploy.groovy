def call(){
    node {
        stage(deploy) {
            sh "echo 'deploy stage"
        }
    }
}