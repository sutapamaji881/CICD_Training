pipeline {
  agent {
    node {
      label 'master'
    }

  }
  stages {
    stage('build') {
      steps {
        git(credentialsId: 'GitlabCredentials', url: 'https://git.epam.com/sowmya_moturu/devopsbasics.git')
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
              label 'JenkinsNode1'
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

  
   
    stage('SonarQube Analysis') {
 
        environment {
            scannerHome = tool 'SonarScanner'
        }	
        steps {
 
            withSonarQubeEnv (credentialsId: 'sonartoken', installationName: 'SonarQube') {
                sh ''' \
                        ${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectName=MyProject \
                            -Dsonar.projectVersion=1.0 \
                            -Dsonar.sourceEncoding=UTF-8 \
                            -Dsonar.projectKey=my-java-maven-sonar \
                            -Dsonar.sources=server/src \
                            -Dsonar.java.binaries=server/target/classes \
                            '''
            }
      
        }
        
    }

    stage('Quality Gate') {
      steps {
        timeout(time: 1, unit: 'MINUTES') {
      waitForQualityGate abortPipeline: true
        }

      }
    }


  stage('Nexus publish') {
      steps {
        nexusPublisher(nexusInstanceId: 'nexusserver', nexusRepositoryId: 'jenkins-repo', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'webapp/target/webapp.war']], mavenCoordinate: [artifactId: 'webapp', groupId: 'com.myproject', packaging: 'war', version: '3.0']]])
      }
    }

    stage('Pull from Nexus') {
      steps {
        sh 'wget --user=nexus --password=pa55w0rd!  http://10.245.128.180:8081/repository/jenkins-repo/com/myproject/webapp/3.0/webapp-3.0.war'
      }
    }

   stage('Deploy') {
      steps {
       deploy adapters: [tomcat9(credentialsId: 'tomcatcredentials', path: '', url: 'http://10.245.128.134:8090/')], contextPath: null, war: 'webapp-3.0.war'
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
      emailext(subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:', body: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:  Check console output at $BUILD_URL to view the results.', attachLog: true, from: 'sowmya_moturu@epam.com', to: 'sowmya_moturu@epam.com')
       cleanWs()
    }
   

  }
}