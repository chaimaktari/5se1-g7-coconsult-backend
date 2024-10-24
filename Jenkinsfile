pipeline {
    agent any
    environment {
            BRANCH_NAME = "feature-nacer"
            DOCKERHUB_CREDENTIALS = credentials('docker-credentials-nacer')
        }

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git'
                git branch: 'feature-nacer',
                    url: 'https://github.com/chaimaktari/5se1-g7-coconsult-backend.git',
                    credentialsId: 'nacerID'
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
                    withDockerRegistry(credentialsId: 'docker-credentials-nacer'){
                        sh "docker build -t akacha08/nacer_devdynamos:${BUILD_NUMBER} ."
                        sh "docker push akacha08/nacer_devdynamos:${BUILD_NUMBER}"
                    }
                }
            }
        }


    }

    post {
        failure {
            mail to: 'naceur.akacha@esprit.tn',
                subject: "Échec du pipeline Jenkins - ${env.JOB_NAME} numero : #${env.BUILD_NUMBER}",
                body: "Le pipeline Jenkins pour le Job ${env.JOB_NAME} a échoué lors de l'étape de création du livrable.\n\nVoir les détails ici : ${env.BUILD_URL}"
        }
    }
}
