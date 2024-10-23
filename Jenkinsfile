pipeline {
    agent any
    environment {
            DOCKER_IMAGE = "anisfetoui-5se1-devdynamos"
            BRANCH_NAME = "feature-AnisFETOUI"
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
                    sh '''
                    mvn clean package -DskipTests
                    mvn jacoco:report
                    '''
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
                withDockerRegistry(credentialsId: 'dockerhub-anis-credentials'){
                    sh "docker build -t anisfetoui/${DOCKER_IMAGE}:${BUILD_NUMBER} ."
                 //   sh "docker push anisfetoui/${DOCKER_IMAGE}:${BUILD_NUMBER}"
            }
            }
        }
    }

    stage('Sonar Analysis') {
            steps {
                script {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.url=http://192.168.33.10:9000/ \
                        -Dsonar.login=squ_4eff4cf86e03b423a9e187646586f80b538aecc1 \
                        -Dsonar.projectName=DevDynamos \
                        -Dsonar.java.binaries=. \
                        -Dsonar.projectKey=DevDynamos \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    """
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
