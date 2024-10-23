
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
         stage('Upload to Nexus') {
            steps {
                script {
                    echo "Deploying to Nexus..."
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: "192.168.150.128:9001",
                        groupId: 'com.bezkoder',
                        artifactId: 'CoConsult',
                        version: '1.0',
                        repository: "maven-central-repository",
                        credentialsId: "nexus-credentials",
                        artifacts: [
                            [
                                artifactId: 'CoConsult',
                                classifier: '',
                                file: 'target/CoConsult.jar', 
                                type: 'jar'
                            ]
                        ]
                    )
                    echo "Deployment to Nexus completed!"
                }
            }
    }

    
    }

