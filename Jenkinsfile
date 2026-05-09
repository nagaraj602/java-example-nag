pipeline {

    agent any
    environment {

        AWS_REGION = 'us-east-1'

        ECR_REPO = 'java25-demo-app'

        ACCOUNT_ID = '544917027663'

        IMAGE_TAG = "${BUILD_NUMBER}"

        IMAGE_URI = "${ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${ECR_REPO}:${IMAGE_TAG}"
    }

    stages {

        stage('Checkout') {

            steps {
                cleanWs()
                git branch: 'main', url: 'https://github.com/nagaraj602/java-example-nag-jar.git'

            }
        }

        stage('Build Docker Image') {

            steps {

                sh 'docker build -t java25-demo-app:${BUILD_NUMBER} .'
            }
        }

        stage('Test') {

            steps {

                sh '''
                    wget https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/html.tpl

                    trivy image \
                    --format template \
                    --template "@html.tpl" \
                    -o trivy-image-report.html \
                    java25-demo-app:${BUILD_NUMBER}
                '''
            }
        }

        stage('Login to AWS ECR') {

            steps {

                withCredentials([[
                    $class: 'AmazonWebServicesCredentialsBinding',
                    credentialsId: 'aws-ecr'
                ]]) {

                    sh '''
                        aws ecr get-login-password --region $AWS_REGION | \
                        docker login \
                        --username AWS \
                        --password-stdin \
                        $ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com
                    '''
                }
            }
        }

        stage('Tag Docker Image') {

            steps {

                sh 'docker tag java25-demo-app:${BUILD_NUMBER} $IMAGE_URI'
            }
        }

        stage('Push Docker Image to ECR') {

            steps {

                sh 'docker push $IMAGE_URI'
            }
        }

        stage('Remove Local Image') {

            steps {

                sh 'docker rmi $IMAGE_URI'
            }
        }

        stage('Pull Image from ECR') {

            steps {

                sh 'docker pull $IMAGE_URI'
            }
        }

        stage('Deploy Container') {

            steps {
                sh 'docker stop java25-container'
                sh 'docker rm java25-container'
                sh 'docker run -d --name java25-container -p 8085:8085 $IMAGE_URI'
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: '*.html', fingerprint: true
    
        }
    }
}
