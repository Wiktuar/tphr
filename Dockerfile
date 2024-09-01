FROM adoptopenjdk/openjdk11:alpine-jre
WORKDIR /opt/app
COPY target/tphr-0.0.1-SNAPSHOT.jar application.jar
ENTRYPOINT [“java","-jar","app.jar"]