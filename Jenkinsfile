pipeline {
    agent any

    environment {
        SPRING_DATASOURCE_PASSWORD = credentials('mysql-password')
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out RideEase repository'
            }
        }

        stage('Build Ride Service') {
            steps {
                dir('ride-service') {
                    bat 'mvnw.cmd clean package'
                }
            }
        }

        stage('Build Location Service') {
            steps {
                dir('location-service') {
                    bat 'mvnw.cmd clean package'
                }
            }
        }

        stage('Build Matching Service') {
            steps {
                dir('matching-service') {
                    bat 'mvnw.cmd clean package'
                }
            }
        }
    }

    post {
        success {
            echo 'All RideEase microservices built successfully!'
        }
        failure {
            echo 'RideEase build failed. Check console output.'
        }
    }
}
