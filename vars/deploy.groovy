def call(helm_chart_url, helm_docker_img, branch) {
    stage("Fetch-Helm-Chart-from-Artifactory") {
        // fetch  helm_chart_url
        echo "Fetching Helm chart ${helm_chart_url} from Helm Artifactory"
        echo "Unzip ${helm_chart_url}"
        //quality gate - read value.yaml file & get the img url, if branch is not feature and the img url is prefix with feature then error out.
        //branch is not feature then error out that u r ref to the feature branch image.
    }
    stage("Deploy-to-GKE") {
        echo "Read values.yaml after unzipping"
        println helm_docker_img
        def helm_docker_img_label = helm_docker_img.substring(helm_docker_img.lastIndexOf("/") + 1)
        helm_docker_img_label = helm_docker_img_label.substring(0, helm_docker_img_label.indexOf('-'))
        println helm_docker_img_label
        if (helm_docker_img_label == "feature"){
            if (branch.startsWith("feature")){
                //Run helm command to deploy
                echo "Deploying Helm chart ${helm_chart_url} to Lower GKE cluster"
            } else {
                println "Can't deploy ${helm_chart_url} to GKE, because you are refering to Feature branch image in Helm Chart values file."
                exit 0
            }
        }
        else {
            //Run helm command to deploy
            echo "Deploying Helm chart ${helm_chart_url} to GKE cluster"
        }
    }
}