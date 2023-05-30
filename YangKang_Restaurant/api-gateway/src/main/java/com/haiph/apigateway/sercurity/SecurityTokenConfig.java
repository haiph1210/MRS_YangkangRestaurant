//package com.haiph.apigateway.sercurity;
//
//import com.haiph.apigateway.filter.AuthenticationFilter;
//import jakarta.servlet.Filter;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//
//@EnableWebSecurity
//public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().and()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .and()
//                .addFilter((Filter) new AuthenticationFilter())
//                .authorizeRequests()
//                .dispatcherTypeMatchers(HttpMethod.POST, jwtConfig().getUri()).permitAll()
//                .anyRequest().authenticated();
//    }
//
//    @Bean
//    public JwtConfig jwtConfig() {
//        return new JwtConfig();
//    }
//
//}
