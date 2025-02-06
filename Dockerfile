FROM gradle:7.4.1-jdk17 AS builder

VOLUME /tmp

RUN mkdir -p /home/url-shortener

WORKDIR /url-shortener

COPY build/libs/*.jar home/url-shortener/url-shortener.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "home/url-shortener/url-shortener.jar"]