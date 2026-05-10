def call(String fullImage, String tag) {
    echo "Building Docker image: ${fullImage}:${tag}"
    sh "docker build -t ${fullImage}:${tag} ."
    sh "docker tag  ${fullImage}:${tag} ${fullImage}:latest"
    echo "✅ Docker image built successfully!"
}