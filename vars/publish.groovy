def call(body){
    body()
    node {
        stage(publish) {
            sh "echo 'publish stage"
        }
    }
}