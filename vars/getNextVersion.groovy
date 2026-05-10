def call(String repoUrl) {
    echo "🔢 Getting next version..."

    withCredentials([usernamePassword(
        credentialsId: 'github-credentials',
        usernameVariable: 'GH_USER',
        passwordVariable: 'GH_TOKEN'
    )]) {
        sh """
            git config user.email "jenkins@techaxis.com.np"
            git config user.name  "Jenkins"

            # fetch tags using credentials
            git fetch --tags https://\${GH_USER}:\${GH_TOKEN}@\$(echo ${repoUrl} | sed 's|https://||')
        """

        def latestTag = sh(
            script: """
                git tag -l 'v*.*' \
                | sort -V \
                | tail -1
            """,
            returnStdout: true
        ).trim()

        echo "📌 Latest tag found: ${latestTag ?: 'none'}"

        def nextVersion

        if (!latestTag) {
            nextVersion = "1.1"
        } else {
            def versionParts = latestTag
                .replace('v', '')
                .tokenize('.')

            def major = versionParts[0].toInteger()
            def minor = versionParts[1].toInteger()

            if (minor >= 99) {
                major = major + 1
                minor = 1
            } else {
                minor = minor + 1
            }

            nextVersion = "${major}.${minor}"
        }

        echo "✅ Next version: ${nextVersion}"
        return nextVersion
    }
}