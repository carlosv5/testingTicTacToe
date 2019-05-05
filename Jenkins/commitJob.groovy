node {
stage ('Checkout') {
    //git url: 'http://10.0.2.15:8080/TicTacToe2'
    //git fetch $GIT_URL $GERRIT_REFSPEC && git checkout FETCH_HEAD
    //def IP = sh "docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' codeurjc-forge-gerrit"
    IP = sh(returnStdout: true, script: "docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' codeurjc-forge-gerrit").trim()
    git url: "http://" + IP + ":8080/TicTacToe2"
}
 stage ('Build') {
     docker.image('maven:3-jdk-8-alpine').inside('-v $HOME/.m2:/root/.m2 '
     +'-v /var/run/docker.sock:/var/run/docker.sock') {
        sh 'mvn -Dmaven.test.failure.ignore=true -Dtest=BoardParametrizedTest,TicTacToeGameTest test'     }
     step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
     
 }
 stage ('Sonar') {
     docker.image('maven:3-jdk-8-alpine').inside('-v $HOME/.m2:/root/.m2 ' + '--network=ci-network --link codeurjc-forge-sonarqube:sonarqube '
     +'-v /var/run/docker.sock:/var/run/docker.sock') {
        sh 'mvn sonar:sonar -Dsonar.jdbc.url=jdbc:h2:tcp://sonarqube:9092/sonar -Dsonar.host.url=http://sonarqube:9000'
 }
 }
 }
