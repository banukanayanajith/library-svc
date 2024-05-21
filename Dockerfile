FROM openjdk:11-jre-slim

WORKDIR /app

COPY target/library-svc.jar library-svc.jar

ENV DB_URL=jdbc:mariadb://mariadb:3306/library_db
ENV DB_USERNAME=dbuser
ENV DB_PASSWORD=dbpassword

CMD ["java", "-jar", "/library-svc.jar"]
