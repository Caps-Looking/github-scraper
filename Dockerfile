FROM adoptopenjdk/openjdk11:alpine-jre
VOLUME /tmp
COPY target/github-scraper-*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]