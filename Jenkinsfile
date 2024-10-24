pipeline {
    agent any
    // environment {
    //         BRANCH_NAME = "feature-nacer"
    //         DOCKERHUB_CREDENTIALS = credentials('docker-credentials-nacer')
    //     }

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

 stage('Build Docker Image') {
    steps {
        script {
            echo 'Building Docker image'
            def imageName = 'nacer_devdynamos' // Nom de votre dépôt Docker Hub
            def dockerHubCredentials = 'docker-credentials-nacer' // Assurez-vous que cela correspond à votre ID d'identification Jenkins
            sh "ls -la target/"

            sh "docker build -t ${dockerHubCredentials}/${imageName}:latest ."


            // withCredentials([usernamePassword(credentialsId: dockerHubCredentials, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
            //     sh "echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin"
            // }


            // sh "docker push ${dockerHubCredentials}/${imageName}:latest"
        }
    }
}




        // stage('Sonar Analysis') {
        //     steps {
        //         script {
        //             sh """
        //                 mvn sonar:sonar \
        //                 -Dsonar.url=http://192.168.33.10:9000/ \
        //                 -Dsonar.login=squ_4eff4cf86e03b423a9e187646586f80b538aecc1 \
        //                 -Dsonar.projectName=DevDynamos \
        //                 -Dsonar.java.binaries=. \
        //                 -Dsonar.projectKey=DevDynamos \
        //                 -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
        //             """
        //         }
        //     }
        // }

    }

    post {
        failure {
            mail to: 'naceur.akacha@esprit.tn',
                subject: "Échec du pipeline Jenkins - ${env.JOB_NAME} numero : #${env.BUILD_NUMBER}",
                body: "Le pipeline Jenkins pour le Job ${env.JOB_NAME} a échoué lors de l'étape de création du livrable.\n\nVoir les détails ici : ${env.BUILD_URL}"
        }
    }
}
