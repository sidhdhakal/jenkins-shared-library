def call(String version, String repoUrl) {
    echo "🏷️ Pushing tag v${version} to GitHub..."

    withCredentials([usernamePassword(
        credentialsId: 'ghcr-credentials',
        usernameVariable: 'GH_USER',
        passwordVariable: 'GH_TOKEN'
    )]) {
        sh """
            # Configure git user
            git config user.email "jenkins@techaxis.com.np"
            git config user.name  "Jenkins"

            # Create tag locally
            git tag v${version} -m "Release v${version} by Jenkins"

            # Push tag to GitHub
            git push https://\${GH_TOKEN}@\$(echo ${repoUrl} | sed 's|https://||') v${version}
        """
    }

    echo "✅ Tag v${version} pushed to GitHub successfully!"
}