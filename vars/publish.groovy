def call(){
    node {
        stage(publish) {
            sh "echo 'publish stage"
        }
    }
}