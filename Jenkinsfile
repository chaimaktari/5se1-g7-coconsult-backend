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

    stage('Docker Build & Push') {
        steps {
            script {
                withDockerRegistry(credentialsId: 'anisfetoui'){
                    sh "docker build -t anisfetoui/${DOCKER_IMAGE}:${IMAGE_VERSION} ."
                    sh "docker push anisfetoui/${DOCKER_IMAGE}:${IMAGE_VERSION}"
            }
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
