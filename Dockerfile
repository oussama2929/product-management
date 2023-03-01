FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY target/productsmanagment-1.0.0.jar productsmanagment-1.0.0.jar
ENTRYPOINT ["java","-jar","/productsmanagment-1.0.0.jar"]
