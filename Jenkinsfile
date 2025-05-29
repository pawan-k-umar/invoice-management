pipeline {
    agent any

    parameters {
        string(name: 'BRANCH', defaultValue: 'integration', description: 'Git branch to build')
        string(name: 'DOCKER_TAG', defaultValue: '0.0.1-SNAPSHOT', description: 'Docker image tag')
        string(name: 'HOST_PORT', defaultValue: '9091', description: 'Host port to expose')
        string(name: 'CONTAINER_PORT', defaultValue: '9091', description: 'Container port the app listens on')
    }

    environment {
        DOCKER_IMAGE = "invoice-app-image"
        CONTAINER_NAME = "invoice-app"
        DOCKER = "docker"  // or full path if needed
        GIT_REPO = 'https://github.com/pawan-k-umar/invoice-management.git'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: "${params.BRANCH}", url: "${GIT_REPO}"
            }
        }

        stage('Build with Maven') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh """
                    echo "🔍 Checking if Docker is installed and running..."

                    if ! command -v ${DOCKER} &> /dev/null; then
                      echo "❌ Docker is not installed. Please install Docker Desktop for Mac."
                      exit 1
                    fi

                    if ! ${DOCKER} info > /dev/null 2>&1; then
                      echo "❌ Docker is installed but not running. Please start Docker Desktop manually."
                      exit 1
                    fi

                    echo "✅ Docker is running. Proceeding to build and run the container..."

                    ${DOCKER} stop ${CONTAINER_NAME} || true
                    ${DOCKER} rm ${CONTAINER_NAME} || true

                    ${DOCKER} build -t ${DOCKER_IMAGE}:${params.DOCKER_TAG} .
                    echo "🚀 Build successful. App should be available for deployment at http://localhost:${params.HOST_PORT}"
                """
            }
        }

         stage('Docker Run') {
            steps {
                sh """
                  ${DOCKER} run -d -p ${params.HOST_PORT}:${params.CONTAINER_PORT} --name ${CONTAINER_NAME} ${DOCKER_IMAGE}:${params.DOCKER_TAG}
                  echo "🚀 Deployment successful. App should be available at http://localhost:${params.HOST_PORT}"
                """
            }
         }
    }
}