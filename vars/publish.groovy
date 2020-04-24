def call(body){
    node {
        stage(publish) {
            sh "echo 'publish stage"
        }
    }
}