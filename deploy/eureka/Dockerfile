FROM java:8

VOLUME /tmp

ADD eureka-0.0.1.jar app.jar

EXPOSE 17501

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
