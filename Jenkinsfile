pipeline {
    agent any 

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git'
                git branch: 'feature-chaimaKTARI',
                    url: 'https://github.com/chaimaktari/5se1-g7-coconsult.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    try {
                        // Compilation et packaging du projet en une étape
                        sh 'mvn clean package -X'
                    } catch (Exception e) {
                        error "Build failed: ${e}"
                    }
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
