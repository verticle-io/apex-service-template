FROM java:8-jdk-alpine
VOLUME /tmp
ADD apex-service-template-0.5.0.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 9006
VOLUME ["/static"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","-classpath", "/static"]
