def publishStages(){
    def publishers = [:]
    publishers["docker"] = {
        stage("Build-Docker-Image") {
            echo "docker build -t ${docker_img}:${docker_tag} ."
        }
        stage("Publish-Docker-Image-to-Artifactory") {
            echo "Publish docker image - ${docker_img}:${docker_tag} to artifactory"
        }
    }
    publishers["gcr"] = {
        stage("Publish-Docker-Image-to-GCR") {
            echo "Publishing docker image - ${docker_img}:${docker_tag} to GCR"
        }
    }
    publishers["helm-chart"] = {
        //Publish helm chart to artifact
        stage("Publish-Helm-Chart-to-Artifactory") {
            echo "Publish Helm Chart ${helm_chart_url} "
        }
    }
    parallel publishers
}