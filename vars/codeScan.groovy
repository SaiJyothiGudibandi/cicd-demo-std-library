def call(body){
    body()
    node {
        stage("Code-Scan") {
            echo("Code Scan Stage")
        }
    }
}