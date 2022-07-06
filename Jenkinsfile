pipeline {
    agent any 
    environment {
       DOCKER = tool 'docker' 
       DOCKER_EXEC = '$DOCKER/docker'
       SCA = '/Users/clagosu/Documents/dependency-check/bin/dependency-check.sh'
       TRIVY = '/Users/clagosu/Documents/trivy_0.29.2_macOS-64bit/trivy'
    }
    stages {
      
      stage('SCM') {
         steps {
            figlet 'SCM'
            checkout scm // clonacion de codigo en nodo
         }
      }
        
      stage('BUILD') {
         steps {
            figlet 'BUILD'
            sh 'set +x; chmod 777 mvnw'
            //sh './mvnw clean build'
          //archiveArtifacts artifacts: "build/libs/testing-web-*.jar"
         }
      }
        
      stage('SAST') {
         steps {
            withCredentials([string(credentialsId: 'sonarcloud', variable: 'SONARPAT')]) {
                 figlet 'SAST'
                 sh('set +x; ./mvnw sonarqube -Dsonar.login=$SONARPAT -Dsonar.branch.name=feature-jenkins')
            }
         }
      }
        
   //    stage('SCA') {
   //      steps {
   //          figlet 'SCA'
   //          //sh "$SCA --project 'spring-clinic' --scan '${WORKSPACE}/build/libs/pet-clinic-2.6.0.jar'"
   //          echo '************* SBOM CYCLONEDX *************'
   //          //sh "./gradlew cyclonedxBom -info"
   //      }
   //   }
        
      // stage('Build Image') {
      //    steps {
      //       figlet 'BUILD IMAGE'
      //       //sh "$DOCKER_EXEC build . -t clagosu/pet-clinic:$currentBuild.number"
      //       //sh "$TRIVY image clagosu/pet-clinic:$currentBuild.number"
      //     //sh "$DOCKER_EXEC push clagosu/spring-clinic:$currentBuild.number"
      //       }
      //   }
        
   //      stage('DAST') {
   //      steps {
   //          figlet 'DAST'
   //          //sh "${DOCKER_EXEC} run --rm -v ${WORKSPACE}:/zap/wrk/:rw -t owasp/zap2docker-stable zap-baseline.py -t http://localhost -r DAST_Report.html"
   //          //sh "${DOCKER_EXEC} run --rm -v ${WORKSPACE}:/zap/wrk/:rw -t owasp/zap2docker-stable zap-api-scan.py -t http://localhost/api-docs -f openapi -r DAST_Report.html"
   //      }
   //   }
        
   }
}
