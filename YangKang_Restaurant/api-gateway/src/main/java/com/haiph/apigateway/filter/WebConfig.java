//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.reactive.config.EnableWebFlux;
//
//import java.util.List;
//
//@Configuration
//@EnableWebFlux Security
//public class SecurityConfig {
//
//    @Bean
//    public org.springframework.security.web.server.SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
//        http
//                .cors().configurationSource(request -> {
//                    CorsConfiguration configuration = new CorsConfiguration();
//                    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
//                    configuration.setAllowedMethods(List.of("*"));
//                    configuration.setAllowedHeaders(List.of("*"));
//                    return configuration;
//                });
//        http.csrf().disable();
//        return http.build();
//    }