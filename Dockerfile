FROM eclipse-temurin:21-jdk
EXPOSE 8080
ADD target/springbootendtoendapp-docker-pipeline.jar springbootendtoendapp-docker-pipeline.jar
ENTRYPOINT ["java","-jar","/springbootendtoendapp-docker-pipeline.jar"]