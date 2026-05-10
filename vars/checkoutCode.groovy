def call(String repoUrl, String branch = 'main') {
    echo "Checking out code from ${repoUrl} branch: ${branch}"
    git branch: branch, url: repoUrl
}