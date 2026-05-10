def call(String projectKey, String sonarUrl, String appType) {
    echo "Running SonarQube Analysis for project: ${projectKey}"
    withSonarQubeEnv('SonarQube') {
        script {
            if (appType == 'maven') {
                sh """
                    mvn sonar:sonar \
                      -Dsonar.projectKey=${projectKey} \
                      -Dsonar.host.url=${sonarUrl}
                """
            } else if (appType == 'gradle') {
                sh """
                    ./gradlew sonarqube \
                      -Dsonar.projectKey=${projectKey} \
                      -Dsonar.host.url=${sonarUrl}
                """
            } else {
                // node, python, others
                sh """
                    sonar-scanner \
                      -Dsonar.projectKey=${projectKey} \
                      -Dsonar.sources=. \
                      -Dsonar.host.url=${sonarUrl}
                """
            }
        }
    }
}