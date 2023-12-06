package sia.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    protected SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange(
                        request ->
                                request.pathMatchers( HttpMethod.POST,"/data-api/ingredients").hasRole("ADMIN")
                                        .pathMatchers(HttpMethod.DELETE,"/data-api/ingredients/**").hasRole("ADMIN")
                                        .pathMatchers(HttpMethod.POST,"/api/ingredients")
                                        .hasAuthority("SCOPE_writeIngredients")
                                        .pathMatchers(HttpMethod.DELETE,"/api/ingredients")
                                        .hasAuthority("SCOPE_deleteIngredients"))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .build();
    }
}
