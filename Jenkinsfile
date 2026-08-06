pipeline {
    agent any
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: 'master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/javatechivkp/JavaRealtimeApplicationEndtoEndLt.git']]])
                bat 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t thrishank99/springbootendtoendapp-docker-pipeline .'
                }
            }
        
        }
       stage('Push image to Hub'){
            steps{
                script{
                  withCredentials([usernamePassword(credentialsId: 'srinudockerhub', passwordVariable: 'srinudockerhub', usernameVariable: 'thrishank99')]) {
                  bat "docker login -u ${env.thrishank99} -p ${env.srinudockerhub}"
}
                  bat 'docker push thrishank99/springbootendtoendapp-docker-pipeline'
                }
            }
        }
        stage('build & SonarQube Analysis'){
            steps{
              withSonarQubeEnv('sonarqubescannernew'){
              bat 'mvn clean package sonar:sonar'
            }
}
        }     
   }
}