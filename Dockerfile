FROM gaianmobius/openjdk-21-mvn-3.9.6
ARG JAR_FILE=target/matmehBack-1.0-SNAPSHOT.jar
WORKDIR /opt/backend
COPY ${JAR_FILE} backend.jar
ENTRYPOINT ["java","-jar","backend.jar"]
EXPOSE 8091

