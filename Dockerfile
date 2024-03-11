FROM openjdk:17
EXPOSE 8080
WORKDIR /app
COPY target/*.jar /app/
CMD java -jar $(ls /app/*.jar | head -n 1)
