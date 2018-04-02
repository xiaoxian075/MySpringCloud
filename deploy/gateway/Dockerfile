FROM java:8

VOLUME /tmp

ADD gateway-0.0.1.jar app.jar

EXPOSE 17511

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
