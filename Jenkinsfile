pipeline {
    agent any
    stages {
        stage('Checkout GIT') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: 'main']],
                    userRemoteConfigs: [[url: 'https://github.com/oussama2929/product-management.git']]
                ])
            }
        }
        stage('MVN CLEAN') {

                    steps { withMaven(){
                        bat 'mvn clean compile'
                        }
                    }
                }

                stage('MVN COMPILE') {
                    steps {
                        bat 'mvn compile'
                    }
                }

                stage('MVN PACKAGE') {
                    steps {
                        bat 'mvn package'
                    }
                }

                stage('MVN TEST') {
                    steps {
                        bat 'mvn test'
                    }
                }

}
}