#build
FROM maven:3.9.11-amazoncorretto-21-debian AS build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

#run
FROM amazoncorretto:21.0.7
WORKDIR /app

COPY --from=build ./build/target/*.jar ./libraryapi.jar

EXPOSE 8080
EXPOSE 9090

ENV DATASOURCE_URL=jdbc:postgresql://localhost:5432/postgres
ENV DATASOURCE_USERNAME=postgres
ENV DATASOURCE_PASSWORD=postgres
ENV GOOGLE_CLIENT_ID='client-id'
ENV GOOGLE_CLIENT_SECRET='123'

ENV SPRING_PROFILES_ACTIVE=production
ENV TZ='America/Sao_Paulo'

ENTRYPOINT ["java", "-jar", "libraryapi.jar"]
