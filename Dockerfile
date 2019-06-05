#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /var/jenkins_home/workspace/Merge@2/src
COPY pom.xml /var/jenkins_home/workspace/Merge@2/
RUN mvn -f /var/jenkins_home/workspace/Merge@2/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /var/jenkins_home/workspace/Merge@2/target/carlos.vegagarci-sergio.sanchez.ruiz-0.0.1-SNAPSHOT.jar /usr/local/lib/TicTacToe.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/TicTacToe.jar"]

