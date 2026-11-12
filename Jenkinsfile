
pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out RideEase repository'
            }
        }

        stage('Build Ride Service') {
            steps {
                dir('ride-service') {
                    bat 'mvnw.cmd clean compile'
                }
            }
        }

        stage('Test Ride Service') {
            steps {
                dir('ride-service') {
                    bat 'mvnw.cmd test'
                }
            }
        }

        stage('Package Ride Service') {
            steps {
                dir('ride-service') {
                    bat 'mvnw.cmd package -DskipTests'
                }
            }
        }
    }
}
