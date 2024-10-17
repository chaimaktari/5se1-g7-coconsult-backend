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

     stage('Docker Login') {
         steps {
             script {
                 withCredentials([usernamePassword(credentialsId: 'anisfetoui', usernameVariable: 'DOCKERHUB_USER', passwordVariable: 'DOCKERHUB_PSW')]) {
                     sh "docker login -u $DOCKERHUB_USER -p $DOCKERHUB_PSW"
                 }
             }
         }


        stage('Build and Push Docker Image') {
                    steps {
                        script {
                            sh "docker build -t $DOCKERHUB_USER/my-app:latest ."
                            sh "docker push $DOCKERHUB_USER/my-app:latest"
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
