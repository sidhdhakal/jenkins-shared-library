def call() {
    echo " Running OWASP Dependency Check..."
    dependencyCheck(
        additionalArguments: '''
            --scan ./
            --format HTML
            --format XML
            --out ./owasp-report
            --prettyPrint
        ''',
        odcInstallation: 'OWASP-DC'
    )
    dependencyCheckPublisher(
        pattern: 'owasp-report/dependency-check-report.xml',
        stopBuild: false,
        failedTotalCritical: 1,
        warnedTotalHigh: 5
    )
    publishHTML([
        allowMissing: false,
        reportDir: 'owasp-report',
        reportFiles: 'dependency-check-report.html',
        reportName: 'OWASP Dependency Report'
    ])
}