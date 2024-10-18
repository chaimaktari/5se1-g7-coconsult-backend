
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

        stage('Compile') {
            steps {
                script {

                    sh 'mvn clean compile '
                }
            }
        }

        stage('Build') {
            steps {
                script {

                    sh 'mvn clean package -DskipTests'
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
