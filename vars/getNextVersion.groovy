def call(String repoUrl) {
    echo "🔢 Getting next version..."

    // Configure git
    sh """
        git config user.email "jenkins@techaxis.com.np"
        git config user.name  "Jenkins"
    """

    // Fetch all tags from remote
    sh "git fetch --tags"

    // Get latest version tag
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
        // ── No tag exists → start from 1.1
        nextVersion = "1.1"

    } else {
        // Remove 'v' prefix → split by '.'
        def versionParts = latestTag
            .replace('v', '')
            .tokenize('.')

        def major = versionParts[0].toInteger()
        def minor = versionParts[1].toInteger()

        if (minor >= 99) {
            // ── 1.99 → 2.1
            // ── 2.99 → 3.1
            // ── 3.99 → 4.1
            major = major + 1
            minor = 1
        } else {
            // ── 1.1 → 1.2
            // ── 1.98 → 1.99
            minor = minor + 1
        }

        nextVersion = "${major}.${minor}"
    }

    echo "✅ Next version: ${nextVersion}"
    return nextVersion
}