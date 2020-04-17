pipeline {
    agent any

    stages {
        stage('Gradle Build') {
            if (isUnix()) {
                sh './gradlew clean build'
            } else {
                bat 'gradlew.bat clean build'
            }
        }

        stage ('Deploy') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding',
                                 credentialsID: 'pcf_login',
                                 usernameVariable: 'USERNAME',
                                 passwordVariable: 'PASSWORD']]) {
                    sh 'cf login -a http://api.run.pivotal.io -u $USERNAME -p $PASSWORD'
                    sh 'cf push'
                }
            }

        }
    }
}