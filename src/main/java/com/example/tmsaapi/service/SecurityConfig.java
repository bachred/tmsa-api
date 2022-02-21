package com.example.tmsaapi.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] PUBLIC_ENDPOINTS = {
            "/api/users/all",
            "/api/users/generate",
            "/api/users/batch",
            "/api/auth"
    };

    private static final String[] SWAGGER_ENDPOINTS = {
        // -- Swagger UI v2
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        // -- Swagger UI v3 (OpenAPI)
        "/v3/api-docs/**",
        "/swagger-ui/**"
        // other public endpoints of your API may be appended to this array
};

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable();

        // Set session management to stateless
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Set permissions on endpoints
        http.authorizeRequests()
                .antMatchers(PUBLIC_ENDPOINTS).permitAll()
                .antMatchers(SWAGGER_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> {
                    response.getWriter().write("Access Denied... Forbidden");
                });

        // filter injected here
        http.addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        
        web.ignoring().antMatchers("/h2-console/**");

    //    web.ignoring().antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**");

    //     web.ignoring().antMatchers("/v2/api-docs",
    //             "/configuration/ui",
    //             "/swagger-resources/**",
    //             "/configuration/security",
    //             "/swagger-ui/**",
    //             "/swagger-ui",
    //             "/swagger-ui.html",
    //             "/v3/api-docs/**",
    //             "/webjars/**");

        // web.ignoring().antMatchers("/v2/api-docs", 
        //                             "/configuration/ui", 
        //                             "/swagger-resources", 
        //                             "/configuration/security", 
        //                             "/swagger-ui.html", 
        //                             "/webjars/**",
        //                             "/swagger-resources/configuration/ui",
        //                             "/swagger-ui.html");
    }

}
