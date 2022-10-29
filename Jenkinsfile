def GIT_REPOSITORY = 'https://github.com/PheaSoy/spring-boot-kubernetes.git'

properties([
  parameters([

//----------------------------------------------------------------------------------------------------

//[
//      $class: 'ChoiceParameter',
//      name: 'option',
//      choiceType: 'PT_SINGLE_SELECT',
//      description: 'Seleccione el tipo de prueba',
//      script: [
//        $class: 'GroovyScript',
//        fallbackScript: [classpath: [], sandbox: false, script: '#!groovy return ["error ..."]'],
//        script: [classpath: [],
//                 sandbox: true,
//                 script: '''return [
//                  \'BUILD\',
//                  \'SAST\',
//                  \'SCA\',
//                  \'DAST\'
//                ]'''
//        ]
//      ]
//    ], // ChoiceParameter

//----------------------------------------------------------------------------------------------------

	[
		$class: 'GitParameterDefinition',
        description: 'Repositorio Proyecto',
        name: 'BRANCH',
        defaultValue: 'master',
        useRepository: "${GIT_REPOSITORY}",
        quickFilterEnabled: 'true',
		listSize: '3',
        type: 'Branch'
    ],

//----------------------------------------------------------------------------------------------------

//    [
//      $class: 'CascadeChoiceParameter',
//      name: 'zap',
//      referencedParameters: 'option',
//      choiceType: 'PT_RADIO',
//      description: 'Seleccione el tipo de prueba',
//      script: [
//        $class: 'GroovyScript',
//        fallbackScript: [classpath: [], sandbox: false, script: '#!groovy return ["error ... "]'],
//        script: [classpath: [],
//                 sandbox: true,
//                 script: '''if (option.equals("DAST")) {
//                    return ["BASE:selected", "FULL"]
//                  } else if (option.equals("SCA")) {
//                    return ["5", "7", "10:selected"]
//                  } else if (option.equals("SAST")) {
//                    return [  ]
//                 } else if (option.equals("Disabled")) {
//                    return ["notshow:selected"]
//                  }'''
//        ]
//   	 ]
//  ], // CascadeChoiceParameter zap

//----------------------------------------------------------------------------------------------------



//----------------------------------------------------------------------------------------------------

//  [
//      $class: 'StringParameterDefinition' ,
//      name: 'urls'  ,
//      defaultValue: '' ,
//      description: 'Ingresar la URL a escanear solo si el tipo de prueba es ( DAST ) "Debe ser ej: https://dev1.previred.com / https://qademanda.portalafp.cl"'
//    ], // StringParameterDefinition urls

//----------------------------------------------------------------------------------------------------
//  [
//      $class: 'CascadeChoiceParameter',
//      name: 'urls',
//      referencedParameters: 'option',
//      choiceType: 'PT_CHECKBOX',
//      description: 'Seleccione el Url del servicio a escanear solo para el tipo de prueba  ( DAST ), de lo contrario ingresar de forma manual',
//      script: [
//        $class: 'GroovyScript',
//        fallbackScript: [classpath: [], sandbox: false, script: '#!groovy return ["error ... "]'],
//        script: [classpath: [],
//                 sandbox: true,
//                 script: '''if (option.equals("DAST")) {
//                    return ["https://qa1.previred.com", "https://qa2.previred.com", "https://qa3backoffice.previred.com", "https://qarezagos.portalafp.cl", "https://devtwebws.portalafp.cl/", "https://qademanda.portalafp.cl"]
//                  } else if (option.equals("Disabled")) {
//                    return ["notshow:selected"]
//                  }'''
//        ]
//   	 ]
//  ], // CascadeChoiceParameter urls


  ])
])

pipeline {
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

                      SONAR_PROJECT_ORG = 'arqu1t3cturad3v0ps'
                      SONAR_PROJECT_KEY = 'arquitecturadevops_spring-boot-kubernetes'
					  SONAR_PROJECT_NAME = 'spring-boot-kubernetes'
                      SONAR_SERVER = 'SonarCloud'
                      SONAR_FILE_NAME = "sonar-project.properties"
                      SONAR_JAVA_HOME = tool "jdk-11"
                      SONAR_SCANNER_HOME = tool "sonar_scanner_4.6.0.2311"

                      // BUILDING
    				  JAVA_HOME = tool "jdk1.8.0_181"
    				  MAVEN_HOME = tool "maven_3.6.0"

                      // Variables GIT

    				  GIT_CREDENTIALS = ''
                      GIT_CHECKOUT_SRC_DIR = 'source_code'
//                      GIT_CHECKOUT_CONFIG_DIR = 'none'


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

	parameters {		
                    
//		      booleanParam(defaultValue: true, description: 'Ejecución de analisis con OWASP', name: 'Ejecutar_DAST')
//            booleanParam(defaultValue: false, description: 'Ejecución de analisis con Dependency-Check', name: 'Ejecutar_SCA')

			choice (
                    name: 'option',
                    choices: ['', 'BUILD', 'SCA', 'SAST', 'DAST'],
                    description: 'Seleccione una tarea a ejecutar',
                    )
   
             string (
                     defaultValue: 'https://dev1.previred.com', 
                     name: 'urls',
                     description: 'Ingresar la URL a escanear solo si el tipo de prueba es ( DAST ) "Debe ser ej: https://dev1.previred.com / https://qademanda.portalafp.cl"',
                     trim: true
                   )
   
            choice (
      				name: 'zap',
                    choices: ['BASE', 'FULL'],
                    description: 'Seleccione el modo de escaneo si el tipo de prueba es ( DAST )',
                    )

 		       }

 stages {


//INICIO CODIGO ------------------------------------------------------------------------------------


stage('Descargando Codigo') {

//	when { expression { option == "CODE"} }

     steps {

		figlet 'DOWNLOAD - CODE'

        script{

          def base = params.BRANCH.replaceAll("origin/", "");
            base = base.replaceAll("release/", "");
            base = base.replaceAll("hotfix/", "");
            base = base.replaceAll("feature/", "");
            base = base.replaceAll("heads/", "");
            VERSION = base;

            echo " --- LIMPIEZA VAIRBALE BRANCH PARA SONAR --- "
            BRANCH = "${params.BRANCH}".replace("origin/","")


          }

        echo " --- PRINT VARIABLE DE ENTORNO --- "
        echo (
          """
          Env:
            pipeline: ${env.JOB_BASE_NAME}
          Params:
            BRANCH: ${params.BRANCH}
          OCP:
  				  GIT_REPOSITORY: ${GIT_REPOSITORY}
  				  VERSION: ${VERSION}
          """
        )

        echo " --- CLONANDO REPOSITORIO DE CODIGO FUENTE--- "


        echo "Descargando Tag: ${params.BRANCH}"
        checkout([
          $class: 'GitSCM',
          branches: [[name: "${params.BRANCH}"]],
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

     when { expression { option == "BUILD"} }

         steps {

            figlet 'BUILD'

            echo " --- COMPILANDO CODIGO FUENTE--- "

            dir("${BUILD_SOURCE_DIR_PATH}"){

                sh "${MAVEN_HOME}/bin/mvn clean package"

            }

      }
    }

//TERMINO BUILD ------------------------------------------------------------------------------------


//INICIO SAST ------------------------------------------------------------------------------------

    stage('SAST') {

        		when { expression { option == "SAST"} }

        			steps {

        				figlet 'SAST - SONAR'

                 			dir("${BUILD_SOURCE_DIR_PATH}"){
                            withEnv(["JAVA_HOME=${SONAR_JAVA_HOME}"]){
                             withSonarQubeEnv(credentialsId: 'Sonarcloud-DevOps', installationName: 'SonarCloud') {
                              sh "${SONAR_SCANNER_HOME}/bin/sonar-scanner -Dsonar.projectKey=${SONAR_PROJECT_KEY} -Dsonar.projectName=${SONAR_PROJECT_NAME} -Dsonar.organization=${SONAR_PROJECT_ORG} -Dsonar.java.binaries=."
   							 }
						}
                      }
        			}
        		}

//TERMINO SAST ------------------------------------------------------------------------------------

//INICIO SCA ---------------------------------------------------------------------------------------

    stage('SCA') {

	      when { expression { option == 'SCA'} }



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

//INICIO DAST ------------------------------------------------------------------------------------

	stage('DAST - OWASP') {

      when { expression { option == "DAST"} }

          steps {
                script {

                  if ("${params.zap}"=='BASE') {

                            try {
                                echo "Inicio de Scan Dinamico BASE -------------------------------------------------------------------------------------"
                                figlet 'DAST - OWASP'

                                sh " sudo ${DOCKER_EXEC} run --rm -v ${WORKSPACE}:/zap/wrk/:rw --user root -t owasp/zap2docker-stable zap-baseline.py -t ${params.urls} -r ${REPORT_DAST}"
                                currentBuild.result = 'SUCCESS'
                                }

                            catch (Exception e) {
                                                  //echo e.getMessage()
                                                  //currentBuild.result = 'FAIL'

                                                  println ("Revisar Reporte ZAP. Se encontraron Vulnerabilidades.")

                                                  dir("${WORKSPACE}"){
                                                    echo "Generando Reporte HTML"
                                                    sh "sudo chown ${USER}.${USER} ${WORKSPACE}/${REPORT_DAST}"
                                                    archiveArtifacts "${REPORT_DAST}" }
                                                }
                                          }

                  if ("${params.zap}"=='FULL') {

                              try {
                                  echo "Inicio de Scan Dinamico FULL -------------------------------------------------------------------------------------"
                                  figlet 'DAST'

                                  sh " sudo ${DOCKER_EXEC} run --rm -v ${WORKSPACE}:/zap/wrk/:rw --user root -t owasp/zap2docker-stable zap-full-scan.py -t ${params.urls} -r ${REPORT_DAST}"
                                  currentBuild.result = 'SUCCESS'
                                  }

                              catch (Exception e) {
                                                    //echo e.getMessage()
                                                    //currentBuild.result = 'FAIL'

                                                    println ("Revisar Reporte ZAP. Se encontraron Vulnerabilidades.")

                                                    dir("${WORKSPACE}"){
                                                      echo "Generando Reporte HTML"
                                                      sh "sudo chown ${USER}.${USER} ${WORKSPACE}/${REPORT_DAST}"
                                                      archiveArtifacts "${REPORT_DAST}" }
                                                  }
                                            }

                      }
                }
        }

//TERMINO DAST ------------------------------------------------------------------------------------

//INICIO CLEAN ------------------------------------------------------------------------------------

	stage('Limpieza Workspace') {
           steps {
                 step([$class: 'WsCleanup'])

       }
     }
     
//TERMINO CLEAN ------------------------------------------------------------------------------------

    }
}
