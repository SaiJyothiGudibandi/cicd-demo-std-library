def call(){
    stage('Test') {
        parallel 'static': {
            sh "echo 'shell scripts to run static tests...'"
        },
                'unit': {
                    sh "echo 'shell scripts to run unit tests...'"
                },
                'integration': {
                    sh "echo 'shell scripts to run integration tests...'"
                }
    }
}