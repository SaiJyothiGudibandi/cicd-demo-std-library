import java.util.regex.Pattern

def call(Map config) {
	def branch
	try {
	node {
		// Clean workspace before doing anything
		deleteDir()

			stage("Checkout"){
				checkout scm
				branch = env.BRANCH_NAME ? "${env.BRANCH_NAME}" : scm.branches[0].name
				sh "echo ${branch}"
			}
		}
		if (branch.startsWith("feature") || branch.startsWith("dev")) {
			sh "echo 'inside'"
			build(config)
			codeScan(config)
			test(config)
			publish(config)
			deploy(config)
		}
		if (branch.startsWith("rel") || branch.startsWith("master")) {
			sh "echo inside master'"
			deploy(config)
		}
	}catch (err) {
		currentBuild.result = 'FAILED'
		throw err
	}
}