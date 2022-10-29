def GIT_REPOSITORY = 'https://github.com/RSANZANA-DEVSECOPS-USACH/spring-boot-kubernetes.git'

pipeline {

  options {

    timeout(20)
    buildDiscarder(logRotator(numToKeepStr: '10'))

  }
  
	agent any
		environment {

                      // DAST

                      DOCKER_EXEC = 'podman'
                      ZAP_BASE = 'zap-baseline.py'
                      ZAP_FULL = 'zap-full-scan.py'
                      NAME = "${JOB_BASE_NAME}"
                      REPORT_DAST = "REPORT_DAST_${NAME}_${BUILD_NUMBER}_${zap}.html"

                      // SCA

                      BUILD_TARGET_DIR_NAME = "${WORKSPACE}"
                      BUILD_FILE_PATH = "${BUILD_TARGET_DIR_NAME}"
                      REPORT_SCA = "REPORT_SCA_${NAME}_${BUILD_NUMBER}.html"
                      SCA = '/previred_3/dependency-check/bin/dependency-check.sh'

                      //SAST
                      SONAR_URL = 'https://sonarcloud.io/'
                      SONAR_LOGIN = '3c847113f9c8487d0890268839acb874e348789b'
                      SONAR_PROJECT_ORG = 'rsanzana-devsecops-usach'
                      SONAR_PROJECT_KEY = 'RSANZANA-DEVSECOPS-USACH_spring-boot-kubernetes'
					            SONAR_PROJECT_NAME = 'spring-boot-kubernetes'
                      SONAR_SERVER = 'SonarCloud'
                      SONAR_FILE_NAME = "sonar-project.properties"
                      SONAR_JAVA_HOME = tool "jdk-11"
                      SONAR_SCANNER_HOME = tool "SONAR_LATEST"

                      // BUILDING
    				          JAVA_HOME = tool "jdk1.8.0_181"
    				          MAVEN_HOME = tool "maven_3.6.0"

                      // Variables GIT

    				          GIT_CREDENTIALS = ''
                      GIT_CHECKOUT_SRC_DIR = 'source_code'
                      GIT_BRANCH = '*/final-lab-rsanzana'

                      // rutas con archivos de integracion
    				          CICD_PROJECT_NAME = 'DevSecOps'
    				          CICD_REPOSITORY_NAME = 'Owasp'
                      CICD_HOME_PATH = "${WORKSPACE}/${CICD_PROJECT_NAME}/${CICD_REPOSITORY_NAME}"
					            CICD_SONAR_FILE_PATH = "${CICD_HOME_PATH}/sonar/sonar-project.properties"
                      VERSION = 'none'

                      // variables con rutas de codigo fuente
                      BUILD_CONTEXT_DIR = '.'
                      BUILD_SOURCE_DIR_PATH = "${GIT_CHECKOUT_SRC_DIR}/${BUILD_CONTEXT_DIR}"

    				}

 stages {


//INICIO CODIGO ------------------------------------------------------------------------------------


stage('Descargando Codigo') {

//	when { expression { option == "CODE"} }

     steps {

		figlet 'DOWNLOAD - CODE'

        echo " --- PRINT VARIABLE DE ENTORNO --- "
        echo (
          """
          Env:
            pipeline: ${env.JOB_BASE_NAME}
          Params:
            BRANCH: ${GIT_BRANCH}
          OCP:
  				  GIT_REPOSITORY: ${GIT_REPOSITORY}
  				  VERSION: ${VERSION}
          """
        )

        echo " --- CLONANDO REPOSITORIO DE CODIGO FUENTE--- "


        echo "Descargando Tag: ${GIT_BRANCH}"
        checkout([
          $class: 'GitSCM',
          branches: [[name: "${GIT_BRANCH}"]],
          doGenerateSubmoduleConfigurations: false,
          extensions: [
          [$class: 'RelativeTargetDirectory',
          relativeTargetDir: "${GIT_CHECKOUT_SRC_DIR}"
          ]
          ],
          submoduleCfg: [],
          userRemoteConfigs: [
          [
//          [credentialsId: "${GIT_CREDENTIALS}",
          url: "${GIT_REPOSITORY}"]
          ]
          ])


      }
    }

//TERMINO BUILD ------------------------------------------------------------------------------------

//INICIO CODIGO ------------------------------------------------------------------------------------


stage('BUILD') {

         steps {

            figlet 'BUILD'

            echo " --- COMPILANDO CODIGO FUENTE--- "

            dir("${BUILD_SOURCE_DIR_PATH}"){

              withEnv(["JAVA_HOME=${JAVA_HOME}"]){
                sh "${MAVEN_HOME}/bin/mvn clean package"
              }
            }

      }
    }

//TERMINO BUILD ------------------------------------------------------------------------------------


//INICIO SAST ------------------------------------------------------------------------------------

    stage('SAST') {

        			steps {

        				figlet 'SAST - SONAR'

                 			dir("${BUILD_SOURCE_DIR_PATH}"){
                            withEnv(["JAVA_HOME=${SONAR_JAVA_HOME}"]){
                            sh "${SONAR_SCANNER_HOME}/bin/sonar-scanner -X -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                            -Dsonar.host.url=${SONAR_URL} \
                            -Dsonar.login=${SONAR_LOGIN} \
                            -Dsonar.projectName=${SONAR_PROJECT_NAME} -Dsonar.organization=${SONAR_PROJECT_ORG} \
                            -Dsonar.java.binaries=."
						}
          }
  			}
  		}

//TERMINO SAST ------------------------------------------------------------------------------------

//INICIO SCA ---------------------------------------------------------------------------------------

    stage('SCA') {

        steps {
            figlet 'SCA - DC'

		echo " --- COMPILANDO CODIGO FUENTE--- "

        dir("${BUILD_SOURCE_DIR_PATH}"){

            sh "${MAVEN_HOME}/bin/mvn clean package"

        }
//            sh "sudo $SCA --scan ${WORKSPACE}/${zap} -o ${REPORT_SCA} -f HTML --failOnCVSS 8"
//            sh "sudo $SCA --scan ${WORKSPACE}/source_code/target/spring-boot-kubernetes-0.0.1-SNAPSHOT.jar -o ${REPORT_SCA} -f HTML --failOnCVSS ${zap}"
            sh "sudo $SCA --scan ${WORKSPACE}/source_code/target/spring-boot-kubernetes-0.0.1-SNAPSHOT.jar -o ${REPORT_SCA} -f HTML --failOnCVSS 8"

        }

        post {
              always {
               	dir("${WORKSPACE}"){
                	echo "Generando Reporte HTML"
                	sh "sudo chown ${USER}.${USER} ${WORKSPACE}/${REPORT_SCA}"
               		archiveArtifacts "${REPORT_SCA}"
                    }
                 }
             }
       }

//TERMINO SCA ------------------------------------------------------------------------------------

  }
}
