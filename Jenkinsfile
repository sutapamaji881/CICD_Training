pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build') {
      steps {
        git(credentialsId: 'GIT', url: 'https://github.com/sutapamaji881/CICD_Training.git')
      }
    }

    stage('compile') {
      steps {
        sh 'mvn compile'
      }
    }

    stage('test') {
      parallel {
        stage('regression') {
          agent {
            node {
              label 'slave1'
            }

          }
          steps {
            sh 'mvn test -Pregression'
          }
        }

        stage('smoke') {
          steps {
            sh 'mvn test -Psmoke'
          }
        }

      }
    }

    stage('package') {
      post {
        success {
          archiveArtifacts '**/*.war'
        }

      }
      steps {
        sh 'mvn package'
      }
    }


    stage('results') {
      steps {
        junit '**/*.xml'
      }
    }

  }
  tools {
    maven 'Maven'
  }
  post {
    always {
      emailext(subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:', body: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:  Check console output at $BUILD_URL to view the results.', attachLog: true, from: 'sutapa.maji2011@gmail.com', to: 'sutapa_maji@epam.com')
       cleanWs()
    }
   

  }
}