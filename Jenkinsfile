pipeline {
    agent any

    stages {
        stage ('Build') {
            steps {
            }
        }

        stage ('Deploy') {
            steps {
                withCredentials([[$class: 'UsernamePasswordMultiBinding',
                                 credentialsID: 'pcf_login',
                                 usernameVariable: 'USERNAME',
                                 passwordVariable: 'PASSWORD']]) {
                    sh 'usr/local/bin/cf login -a http://api.run.pivotal.io -u $USERNAME -p $PASSWORD'
                    sh 'usr/local/bin/cf push'
                }
            }

        }
    }
}