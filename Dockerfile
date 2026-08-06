FROM openjdk:21
EXPOSE 8080
ADD target/springbootendtoendapp-docker-pipeline.jar springbootendtoendapp-docker-pipeline.jar
ENTRYPOINT ["java","-jar","/springbootendtoendapp-docker-pipeline.jar"]