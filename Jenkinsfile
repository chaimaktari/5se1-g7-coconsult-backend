
pipeline {
    agent any 

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git'
                git branch: 'feature-chaimaKTARI',
                    url: 'https://github.com/chaimaktari/5se1-g7-coconsult-backend.git'
            }
        }

       stage('Clean, Build & Test') {
            agent { label 'agent1' }
            steps {
                sh '''
                    mvn clean install
                    mvn jacoco:report
                '''
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
