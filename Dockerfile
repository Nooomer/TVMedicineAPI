ARG APP_NAME

FROM busybox as pre-build
WORKDIR /src/
COPY . .
RUN mkdir -p /poms && find . -name pom.xml -exec cp --parents {} /poms \;

FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8090:8090
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/ktor-docker-sample.jar
ENTRYPOINT ["java","-jar","/app/ktor-docker-sample.jar"]

FROM nginx as web
RUN mkdir -p /srv/www
ADD nginx/nginx.conf /etc/nginx/conf.d/default.conf

FROM ${APP_NAME}