FROM java:8
ADD target/*.jar /root/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/root/app.jar"]
