
pipeline {
    agent any 

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git'
                git branch: 'feature-imen',
                    url: 'https://github.com/chaimaktari/5se1-g7-coconsult-backend.git'
            }
        }

       stage('Clean, Build & Test') {
            steps {
                sh '''
                    mvn clean install
                    mvn jacoco:report
                '''
            }
        }
        stage('Static Analysis') {
            environment {
                SONAR_URL = "http://192.168.150.128:9000/"
            }
            steps {
                withCredentials([string(credentialsId: 'sonar-credentials', variable: 'SONAR_TOKEN')]) {
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.login=${SONAR_TOKEN} \
                        -Dsonar.host.url=${SONAR_URL} \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
        success {
            echo "Build succeeded!!"
        }
        failure {
            echo "Build failed!"
        }
    }
}
