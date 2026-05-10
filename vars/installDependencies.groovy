def call(String appType) {
    echo "Installing dependencies for ${appType} app"
    script {
        if (appType == 'node') {
            sh 'npm install'

        } else if (appType == 'maven') {
            sh 'mvn clean install -DskipTests'

        } else if (appType == 'python') {
            sh 'pip install -r requirements.txt'

        } else if (appType == 'gradle') {
            sh './gradlew build -x test'

        } else {
            echo "Unknown appType: ${appType} — skipping install"
        }
    }
}