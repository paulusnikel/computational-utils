#!/usr/bin/env groovy

podTemplate(volumes: [
		hostPathVolume(hostPath: '/var/run/docker.sock', mountPath: '/var/run/docker.sock')
],containers: [
containerTemplate(name: 'ci', image: 'asia.gcr.io/shared-281908/ci-image:1.7', ttyEnabled: true)
], serviceAccount: 'jenkins') {
    try {
		node(POD_LABEL) {
			container('ci'){
				def repoName = scm.getUserRemoteConfigs()[0].getUrl().tokenize('/').last().split("\\.")[0]
					stage('Checkout') {
						timeout(time: 3, unit: 'MINUTES') {
							deleteDir()
							checkout scm
						}
					}

				def revision = env.BRANCH_NAME + "-0.0.1-SNAPSHOT";
				revision = revision.replaceFirst(/\//, "-")

				stage('Configure') {
					sh """
						gcloud config set project shared-281908
						gcloud auth configure-docker
					"""
				}

				stage('Sonar scan and Push artifact to Nexus') {
					if (env.BRANCH_NAME == "develop2.0" || env.BRANCH_NAME == "staging" || env.BRANCH_NAME == "preprod" || env.BRANCH_NAME == "master") {
						withCredentials([string(credentialsId: 'sonar-admin-token', variable: 'sonarAdminToken')]){
							sh """
								mvn clean install -DskipTests -Drevision=${revision} -s /settings.xml
								mvn sonar:sonar  -Dsonar.projectKey=${repoName} -Dsonar.host.url=http://sonarqube-sonarqube:9000 -Dsonar.login=${sonarAdminToken} -s /settings.xml
								mvn deploy -Drevision=${revision} -DskipTests -Dmaven.install.skip=true -DskipTests -s /settings.xml 
							"""
						}
					}
				}
			}
		}
    } catch (Exception e) {
        // If there was an exception thrown, the build failed
        currentBuild.result = "FAILED"

        // Since we're catching the exception in order to report on it,
        // we need to re-throw it, to ensure that the build is marked as failed
        throw e
    } finally {
        // Success or failure, always send notifications
        notifyBuild(currentBuild.result)
    }
}


def notifyBuild(String buildStatus) {
  // build status of null means successful
  // the value of ${currentBuild.result}" is null when the job succeeds
  buildStatus = buildStatus ?: 'SUCCESS'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def summary = "${buildStatus} \nJOB: ${env.JOB_NAME} \nBUILD #: ${env.BUILD_NUMBER} \nURL: ${env.BUILD_URL}"

  // Override default values based on build status
  if (buildStatus == 'FAILED') {
    color = 'RED'
    colorCode = '#FF0000'
	// Send notifications
	slackSend (color: colorCode, message: summary)
  }
}
