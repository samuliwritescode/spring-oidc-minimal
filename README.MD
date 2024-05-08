# A minimal OpenID Connect (OIDC) enforced Vaadin Flow application with Spring Boot

So you want or need to build a Java based web application that is secured by Single Sign-On (SSO) login. This is very
typical use case that you have part of application that is not public, but requires authenticated user to access it. You
could build login mechanism by yourself, but implementing security by yourself would be handling all security issues,
that will eventually rise, by yourself and if there are no compelling reasons to do so, you should go with existing
tools.

The tech stack might look like this:

- OpenID Connect or OIDC
    - Identity authentication protocol that is an extension of open authorisation 2 or oauth2.
- Keycloak
    - Free and open source identity and access management system that supports OIDC
- Spring boot
    - Convention over configuration extension for Spring java platform.
- Vaadin
    - Open source Java framework for building web applications.

## But you don’t know where to start from

Examples that you will find online are either incomplete or overwhelming. If they are incomplete, then they are not
fully working apps that you can play around. If they are overwhelming, then you can play around. but you easily get lost
what does exactly what.

## Start simple

Ideally it would be really nice if you have a starting point that gives you minimal but working application. Then you
are much closer to the point of starting from scratch, where you understand every line that goes into your application.
This minimal code projects series is all about taking something complex and making it simple and understandable.

So let’s try to make it simple.

## In practical terms

Project layout contains 3 necessary files:

- [Maven pom.xml with minimal deps](pom.xml)
- [Application entry point](src/main/java/org/samuliwritescode/minimal/oidc/Application.java)
- [Properties for Keycloak server](src/main/resources/application.properties)

Everything else will be either generated or are not strictly required.

What application does, it has 2 routes. First is unsecured main page and second secured page that displays username of
the logged-in user.

## Prerequisites: OpenID Connect server

You will have to have some instance of OpenID connect server running.
If you don't, then just use Keycloak development Docker image like:

`docker run -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:23.0.7 start-dev`

and follow the guide for setting up realm, client-id and user like
so https://www.keycloak.org/getting-started/getting-started-docker

## To run it

application.properties expects Keycloak server running with client ID "minimal-spring-oidc-client"
at URL http://localhost:8081/realms/minimal-spring-oidc

Run the application by

`mvn spring-boot:run`

then open your browser at http://localhost:8080/