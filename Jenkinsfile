

pipeline {
    agent any
    environment {
            BRANCH_NAME = "feature-AnisFETOUI"

            DOCKER_IMAGE = "anisfetoui-5se1-devdynamos"
            DOCKERHUB_CREDENTIALS = 'dockerhub-anis-credentials'
            DOCKER_NAME = 'anisfetoui'

            SONAR_CREDENTIAL_ID = "sonar-anis-credentials"

            NEXUS_VERSION = "nexus3"
            NEXUS_PROTOCOL = "http"
            NEXUS_REPOSITORY = "DevDynamos"
            NEXUS_CREDENTIAL_ID = "nexus-anis-credentials"
        }

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git...'
                git branch: BRANCH_NAME,
                url: 'https://github.com/chaimaktari/5se1-g7-coconsult-backend.git'
            }
        }


        stage('Clean & Package') {
            steps {
                script {
                    sh '''
                    mvn clean package -DskipTests
                    '''
                }
            }
        }


     stage('Verify JAR exists') {
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



    stage('Sonar Analysis') {
            steps {
                script {
                withCredentials([
                string(credentialsId: SONAR_CREDENTIAL_ID, variable: 'TOKEN' ),
                string(credentialsId: 'SONAR_URL', variable: 'SONAR_URL')
                ]) {
                    sh '''
                        mvn sonar:sonar \
                         -Dsonar.url=''' + SONAR_URL + ''' \
                         -Dsonar.login=''' + TOKEN + ''' \
                        -Dsonar.projectName=DevDynamos \
                        -Dsonar.java.binaries=. \
                        -Dsonar.projectKey=DevDynamos \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                    }
                }
            }
        }

        stage('Upload to Nexus') {
                    steps {
                        script {
                            pom = readMavenPom file: "pom.xml";
                            filesByGlob = findFiles(glob: "target/*.${pom.packaging}");
                            artifactPath = filesByGlob[0].path;

                            echo "Deploying to Nexus..."
                            withCredentials([string(credentialsId: 'NEXUS_URL', variable: 'NEXUS_URL')]) {
                            nexusArtifactUploader(
                                nexusVersion: NEXUS_VERSION,
                                protocol: NEXUS_PROTOCOL,
                                nexusUrl: "${NEXUS_URL}",
                                groupId: pom.groupId,
                                version: "${BUILD_NUMBER}",
                                repository: NEXUS_REPOSITORY,
                                credentialsId: NEXUS_CREDENTIAL_ID,
                                artifacts: [
                                    [
                                        artifactId: 'DevDynamos',
                                        classifier: '',
                                        file: 'target/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar',
                                        type: 'jar'
                                    ]
                                ]
                            )
                            echo "Deployment to Nexus completed!"
                        }
                    }
                }
            }



    stage('Docker Build & Push') {
        steps {
            script {
                withDockerRegistry(credentialsId: DOCKERHUB_CREDENTIALS){
                    sh "docker build -t ${DOCKER_NAME}/${DOCKER_IMAGE}:${BUILD_NUMBER} ."
                //  sh "docker push ${DOCKER_NAME}/${DOCKER_IMAGE}:${BUILD_NUMBER}"
            }
            }
        }
    }

            stage('Docker compose (BackEnd MySql)') {
                steps {
                    script {
                        sh 'docker-compose up -d'
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
