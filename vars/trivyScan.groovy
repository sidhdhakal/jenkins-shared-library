def call(String fullImage, String tag) {
    echo "🛡️ Running Trivy scan on ${fullImage}:${tag}"
    sh """
        trivy image \
          --exit-code 0 \
          --severity LOW,MEDIUM \
          --format table \
          ${fullImage}:${tag} || true
    """
    // Fail pipeline if HIGH or CRITICAL found
    sh """
        trivy image \
          --exit-code 1 \
          --severity HIGH,CRITICAL \
          --format table \
          --output trivy-report.txt \
          ${fullImage}:${tag}
    """
    archiveArtifacts artifacts: 'trivy-report.txt', allowEmptyArchive: true
    echo "✅ Trivy Scan passed — No HIGH/CRITICAL vulnerabilities!"
}