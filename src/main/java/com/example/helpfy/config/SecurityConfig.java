package com.example.helpfy.config;

import com.example.helpfy.filters.JwtFilter;
import com.example.helpfy.services.user.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtFilter jwtFilter;

    private final UserService userDetailService;

    private static final String[] SWAGGER_AUTH_WHITE_LIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
    };

    public SecurityConfig(JwtFilter jwtFilter, UserService userDetailService) {
        this.jwtFilter = jwtFilter;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable().authorizeRequests()
                .antMatchers(SWAGGER_AUTH_WHITE_LIST).permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.POST,"/users/**").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers(HttpMethod.GET,"/questions/{questionId}").permitAll()
                .anyRequest()
                .authenticated().and().exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//        http.cors().configurationSource(corsConfigurationSource());
    }
    
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
    }
}
