package com.jwt.example.config;

import com.jwt.example.security.JwtAuthenticationEntryPoint;
import com.jwt.example.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

   @Autowired
   private JwtAuthenticationEntryPoint point;

   @Autowired
   private JwtAuthenticationFilter filter;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

       //configuration
       http.csrf(csrf->csrf.disable())                                                      //csrf configuration
               .cors(cors->cors.disable())                                                  //cors configuration
               .authorizeHttpRequests(                                                                                //authorization
                       auth->
                       auth.requestMatchers("/home/**")
                               .authenticated()
                               .requestMatchers("/auth/login").permitAll().anyRequest().authenticated())
               .exceptionHandling(ex->ex.authenticationEntryPoint(point))
               .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       ;

       http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
       return http.build();

   }
}
