node {
 docker.image('maven').inside('-v $HOME/.m2:/root/.m2 '+
 '-v /var/run/docker.sock:/var/run/docker.sock') {
 sh 'echo "hola"'
 }
  def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'http://IP:8080/TicTacToe'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.
      mvnHome = tool 'Maven'
   }
   stage('Build') {
      // Run the maven build
      if (isUnix()) {
         //sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
         sh "mvn test"
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
   }

}
