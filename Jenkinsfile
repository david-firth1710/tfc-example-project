pipeline {
    agent {
        docker {
            image 'maven:3.5-jdk-8'
            args '-v /var/local/maven:/var/maven --network=host'
        }
    }
    options {
        // Keep the 10 most recent builds
        buildDiscarder(logRotator(numToKeepStr:'10'))
    }
    environment {
        MAVEN_CONFIG = "/var/maven/.m2"
        MAVEN_OPTS = "-Duser.home=/var/maven ${env.JAVA_OPTS}"
        JAVA_TOOL_OPTIONS = "${env.JAVA_OPTS}"
    }
    parameters {
        booleanParam(name: "RELEASE",
                description: "Build a release from current commit.",
                defaultValue: false)
    }
    stages {
        stage('Clean') {
            steps {
                sh 'env | sort'
                sh 'mvn --version'
                sh 'java -version'
                sh 'mvn clean'
            }
        }
        stage('Update Branch Version'){
            when {
                expression { return env.BRANCH_NAME.toUpperCase().contains("TFC") }
            }
            steps {
                sh "mvn --batch-mode release:update-versions -DdevelopmentVersion=${env.BRANCH_NAME.toUpperCase()}-SNAPSHOT"
            }
        }
        stage('Test'){
            steps {
                sh 'mvn install -P coverage'
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('Static Analysis'){
            steps {
                sh "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar -Dsonar.host.url=${env.SONAR_HOST_URL}"
            }
        }
        stage('Publish'){
            steps {
                sh 'mvn clean deploy -Dmaven.test.skip=true -DskipITs'
                archiveArtifacts '**/target/*.jar'
            }
        }
        stage('Release') {
            when {
                allOf {
                    branch "develop"
                    expression { params.RELEASE }
                }
            }
            steps {
                sh 'git remote -v'
                sh 'git branch -av'
                sh 'mvn -B gitflow:release'
            }
        }
    }
}
