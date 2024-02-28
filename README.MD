# A minimal OpenID Connect enforced Vaadin Flow application with Spring Boot

Project layout contains 3 necessary files:
- pom.xml (with minimal deps)
- src/main/java/org/samuliwritescode/minimal/oidc/Application.java (Application entry point)
- src/main/resources/application.properties (Properties for Keycloak server)

Everything else will be either generated or are not strictly required. 
Dockerfile is provided for convenience.

What application does, it has 2 routes. First is unsecured main page and second secured page that displays username of the logged-in user.

## Prerequisites: OpenID Connect server
You will have to have some instance of OpenID connect server running. 
If you don't, then just use Keycloak development Docker image like:

`docker run -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:23.0.7 start-dev`

and follow the guide for setting up realm, client-id and user https://www.keycloak.org/getting-started/getting-started-docker

## To run it
application.properties expects Keycloak server running with client ID "minimal-spring-oidc-client" 
at URL http://localhost:8081/realms/minimal-spring-oidc

Run the application by

`mvn spring-boot:run`

or

`docker build -t spring-oidc-minimal .` and then `docker run -p 8080:8080 spring-oidc-minimal:latest`

then open your browser at http://localhost:8080/