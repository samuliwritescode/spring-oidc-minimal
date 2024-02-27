FROM maven:3.9.5-eclipse-temurin-21 AS BUILD
COPY . /build/
WORKDIR /build/
RUN mvn -C clean package

FROM eclipse-temurin:21
COPY --from=BUILD /build/target/*.jar /app/
WORKDIR /app
EXPOSE 8080
ENV VAADIN.PRODUCTIONMODE=true
ENTRYPOINT java -jar *.jar