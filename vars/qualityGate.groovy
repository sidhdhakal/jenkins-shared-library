def call() {
    echo "🚦 Checking SonarQube Quality Gate..."
    timeout(time: 5, unit: 'MINUTES') {
        def result = waitForQualityGate()
        if (result.status != 'OK') {
            error "❌ Quality Gate FAILED: ${result.status} — Stopping pipeline!"
        } else {
            echo "✅ Quality Gate PASSED!"
        }
    }
}