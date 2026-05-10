def call() {
    echo "🔍 Running OWASP Dependency Check..."
    dependencyCheck(
        additionalArguments: '''
            --scan ./
            --format HTML
            --format XML
            --out ./owasp-report
        ''',
        odcInstallation: 'OWASP-DC'
    )
    dependencyCheckPublisher(
        pattern: 'owasp-report/dependency-check-report.xml',
        stopBuild: false,          // ← don't stop pipeline
        failedTotalCritical: 1
    )
}