FROM openjdk:8
VOLUME /tmp
ADD huahuo-bank-1.0-SNAPSHOT.jar /huahuo-bank.jar
EXPOSE 51608
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/huahuo-bank.jar"]
