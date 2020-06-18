package com.binus.project.forumuaa.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.Objects;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private SecurityConfigurableProperties securityConfigurableProperties;

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers(String.join(",", securityConfigurableProperties.getUnsecuredUris()))
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .requestMatchers().antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access", "/logout");
    }
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        web.ignoring().antMatchers(securityConfigurableProperties.getUnsecuredUris().toArray(new String[0]));
        if (Objects.nonNull(securityConfigurableProperties.getUnsecuredEndpoints())) {
            securityConfigurableProperties.getUnsecuredEndpoints().getGet().forEach(getEndpoint->web.ignoring()
                    .antMatchers(HttpMethod.GET, getEndpoint));
            securityConfigurableProperties.getUnsecuredEndpoints().getPost().forEach(postEndpoint->web.ignoring()
                    .antMatchers(HttpMethod.POST, postEndpoint));
            securityConfigurableProperties.getUnsecuredEndpoints().getPut().forEach(putEndpoint->web.ignoring()
                    .antMatchers(HttpMethod.PUT, putEndpoint));
            securityConfigurableProperties.getUnsecuredEndpoints().getDelete().forEach(deleteEndpoint->web.ignoring()
                    .antMatchers(HttpMethod.DELETE, deleteEndpoint));
            securityConfigurableProperties.getUnsecuredEndpoints().getPatch().forEach(patchEndpoint->web.ignoring()
                    .antMatchers(HttpMethod.PATCH, patchEndpoint));
        }
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(securityConfigurableProperties.getCors().getAllowedOrigins());
        config.setAllowedMethods(securityConfigurableProperties.getCors().getAllowedMethods());
        config.setAllowedHeaders(Collections.singletonList("*"));
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<org.springframework.web.filter.CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}