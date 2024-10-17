pipeline {
    agent any
    environment {
            DOCKER_IMAGE = "AnisFETOUI-5se1-DevDynamos"
            BRANCH_NAME = "feature-AnisFETOUI"
            IMAGE_VERSION = "${BUILD_NUMBER}"
            DOCKERHUB_CREDENTIALS = credentials('dockerhub-anis-credentials')
        }

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git...'
                git branch: 'feature-AnisFETOUI',
                    url: 'https://github.com/chaimaktari/5se1-g7-coconsult-backend.git'
            }
        }

        stage('Compile') {
            steps {
                script {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }


     stage('Verify JAR') {
         steps {
             script {
                 def jarFiles = sh(script: 'ls target/*.jar', returnStdout: true).trim()

                 if (jarFiles) {
                     echo "JAR file created: ${jarFiles}"
                 } else {
                     error("JAR file not found!")
                 }
             }
         }
     }


        stage('Build Docker Image') {
            steps {
                echo "Building Docker Image with tag ${IMAGE_VERSION}"
                sh "docker build -t ${DOCKER_IMAGE}:${IMAGE_VERSION} ."
                sh "docker tag ${DOCKER_IMAGE}:${IMAGE_VERSION} ${DOCKER_IMAGE}:latest"
            }
        }

        stage('Docker Login and Push') {
            steps {
                script {
                    echo "Logging into DockerHub..."
                    sh "echo ${DOCKERHUB_CREDENTIALS_PSW} | docker login -u ${DOCKERHUB_CREDENTIALS_USR} --password-stdin"

                    echo "Pushing Docker images to DockerHub..."
                    sh "docker push ${DOCKER_IMAGE}:${IMAGE_VERSION}"
                    sh "docker push ${DOCKER_IMAGE}:latest"
                }
            }
        }
    }
      post {
            failure {
                mail to: 'anisfetoui2000@gmail.com',
                    subject: "Échec du pipeline Jenkins - ${env.JOB_NAME} numero : #${env.BUILD_NUMBER}",
                    body: "Le pipeline Jenkins pour le Job ${env.JOB_NAME} a échoué lors de l'étape de création du livrable.\n\nVoir les détails ici : ${env.BUILD_URL}"
            }
        }
    }
