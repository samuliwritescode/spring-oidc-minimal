package org.samuliwritescode.minimal.oidc;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@SpringBootApplication // <-- So that you may run this as a Spring Boot application
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args); // <-- So that you may run this directly as a Java application
    }

    @Route("secured")
    @PermitAll
    public static class SecuredRoute extends Div {
        public SecuredRoute() {
            OidcUser user = (OidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            add(new Paragraph("This is a secured route and you are the user '%s'".formatted(user.getName())));
            Anchor logoutLink = new Anchor("/logout", "logout");
            logoutLink.setRouterIgnore(true); // <-- /logout is handled by spring and not Vaadin
            add(logoutLink);
        }
    }

    @Route("unsecured")
    @RouteAlias("")
    @AnonymousAllowed
    public static class UnsecuredRoute extends Div {
        public UnsecuredRoute() {
            add(new Paragraph("Welcome to unsecured route. This you may access without logging in."));
            Anchor linkToSecuredPage = new Anchor("/secured", "This route will require you to login");
            linkToSecuredPage.setRouterIgnore(true); // <-- So that spring security web filter will catch it
            add(linkToSecuredPage);
        }
    }

    @EnableWebSecurity
    @Configuration
    public static class SecurityConfiguration extends VaadinWebSecurity {
        private final OidcClientInitiatedLogoutSuccessHandler logoutSuccessHandler;

        public SecurityConfiguration(@Autowired ClientRegistrationRepository clientRegistrationRepository) {
            logoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
            logoutSuccessHandler.setPostLogoutRedirectUri("http://localhost:8080/unsecured"); // <-- Where Keycloak will redirect after logging out
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.oauth2Login(Customizer.withDefaults()); // <-- This is important to let Spring Security to know to redirect to external login page.
            http.logout(c -> c.logoutSuccessHandler(logoutSuccessHandler)); // <-- Logout with oauth2 must be handled with Keycloak
            super.configure(http);
        }
    }
}
