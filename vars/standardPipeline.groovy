import java.util.regex.Pattern

def call(Map config) {
	def branch

	node {
		// Clean workspace before doing anything
		deleteDir()

		try {
			branch = env.BRANCH_NAME ? "${env.BRANCH_NAME}" : scm.branches[0].name

			if (branch.startsWith("feature") || branch.startsWith("dev")) {
				build()
				codeScan()
				test()
				publish()
				deploy()
			}
			if (branch.startsWith("rel") || branch.startsWith("master")) {
				deploy()
			}
		}catch (err) {
			currentBuild.result = 'FAILED'
			throw err
		}
	}
}