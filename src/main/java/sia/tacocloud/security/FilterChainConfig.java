package sia.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.ServerHttpSecurity;
@Configuration
@EnableWebFluxSecurity
public class FilterChainConfig {
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable).authorizeExchange(
                        requests ->
                                requests.pathMatchers(("/design"), ("/orders")).hasRole("USER")
                                        .anyExchange().permitAll()
                )
                .formLogin(form ->
                        form.loginPage("/login"));
        return http.build();
    }
}
