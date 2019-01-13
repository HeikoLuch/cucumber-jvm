#!groovy

node {
   def mvnHome
   stage('Prepare') { // for display purposes
   	//clean WS
   		deleteDir()
      // Get some code from a GitHub repository
      git 'https://github.com/avenqo/cucumber4eclipse.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'M3'
   }
   
   stage('Build OSGI Modules') {
      // Run the maven build
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -e clean install org.reficio:p2-maven-plugin:1.3.0:site -f pom_swtbot.xml"
      } else {
          // bat "dir"
         bat(/"${mvnHome}\bin\mvn" -e clean install org.reficio:p2-maven-plugin:1.3.0:site -f pom_swtbot.xml/)
      }
	  
   }
   
   stage('Build P2 Repository') {
   if (isUnix()) {
		 sh "cd cucumber-eclipse"
         sh "'${mvnHome}/bin/mvn' -e clean verify"
      } else {
		 bat """
		 	cd cucumber-eclipse
		 	"${mvnHome}/bin/mvn" -e clean verify
		 """
      }
      zip zipFile: 'cucumber4e.zip', archive: false, dir: 'cucumber-eclipse/releng/io.cucumber.eclipse.update/target/repository'
      archiveArtifacts artifacts: 'cucumber4e.zip', fingerprint: true
      archiveArtifacts allowEmptyArchive: true, onlyIfSuccessful: false, artifacts: '**/surefire-reports/*.xml,**/screenshots/**/*.png'
   }
   
   stage('RCP Example') {
	if (isUnix()) {
		 sh "cd ./examples/rcp-example-appl"
         sh "'${mvnHome}/bin/mvn' -e clean verify"
      } else {
		 bat '''
			cd examples\\rcp-example-appl
			mvn -e clean verify
		 '''
      }
      junit '**/target/surefire-reports/TEST-*.xml'
      archive 'target/*.jar'
	}
}