pipeline {
    agent any 

    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling from Git...'
                git branch: 'feature-aziz',
                    url: 'https://github.com/chaimaktari/5se1-g7-coconsult.git'
            }
        }

        stage('Compile') {
            steps {
                script {
                    sh 'mvn clean compile'
                }
            }
        }
    }
}
