FROM maven:3.6.0-jdk-11-slim

WORKDIR /app

ADD pom.xml /app

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

ADD . /app

RUN ["mvn", "clean", "package"]

CMD java -jar $(ls ./target/*.jar)
