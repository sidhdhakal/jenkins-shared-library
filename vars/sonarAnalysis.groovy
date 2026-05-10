def call(String projectKey, String sonarUrl, String appType) {
    echo "🔎 Running SonarQube Analysis for project: ${projectKey}"
    withSonarQubeEnv('SonarQube') {
        def scannerHome = tool 'SonarScanner'   // ← loads the tool
        script {
            if (appType == 'maven') {
                sh """
                    mvn sonar:sonar \
                      -Dsonar.projectKey=${projectKey} \
                      -Dsonar.host.url=${sonarUrl}
                """
            } else {
                sh """
                    ${scannerHome}/bin/sonar-scanner \
                      -Dsonar.projectKey=${projectKey} \
                      -Dsonar.sources=. \
                      -Dsonar.host.url=${sonarUrl}
                """
            }
        }
    }
}