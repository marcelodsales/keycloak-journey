package com.mediterranean.logistics;

import java.util.List;
import java.util.Set;

import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.saml2.core.Assertion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.saml2.provider.service.authentication.OpenSaml5AuthenticationProvider;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.saml2.provider.service.authentication.Saml2AuthenticatedPrincipal;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        OpenSaml5AuthenticationProvider provider = new OpenSaml5AuthenticationProvider();
        provider.setResponseAuthenticationConverter(responseToken -> {
            Saml2Authentication auth = OpenSaml5AuthenticationProvider
                .createDefaultResponseAuthenticationConverter()
                .convert(responseToken);

            Assertion assertion = responseToken.getResponse().getAssertions().get(0);
            List<SimpleGrantedAuthority> authorities = assertion.getAttributeStatements()
                .stream()
                .flatMap(stmt -> stmt.getAttributes().stream())
                .filter(attr -> Set.of("Role", "member-of").contains(attr.getName()))
                .flatMap(attr -> attr.getAttributeValues().stream())
                .map(val -> new SimpleGrantedAuthority(((XSString) val).getValue()))
                .toList();

            return new Saml2Authentication(
                (Saml2AuthenticatedPrincipal) auth.getPrincipal(),
                auth.getSaml2Response(),
                authorities
            );
        });

        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/tracking")
                    .hasAuthority("/mediterranean/logistics/warehouse")
                .requestMatchers("/costs")
                    .hasAuthority("/mediterranean/logistics/managers")
                .requestMatchers("/shared-documents")
                    .hasAuthority("/mediterranean/logistics/partners")
                .anyRequest().authenticated()
            )
            .saml2Login(saml -> saml
                .authenticationManager(new ProviderManager(provider))
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/")
            );
        return http.build();
    }
}
