package com.shopme.config;

import com.shopme.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH); // Set default
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang"); // URL parameter 'lang'
        return interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(); // Corrected the spelling here
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(authorize -> authorize
                        // Countries related permissions
                        .requestMatchers(HttpMethod.POST, "/api/countries/create").hasAuthority("ADMIN") // Only ADMIN can create
                        .requestMatchers(HttpMethod.PUT, "/api/countries/update/**").hasAnyAuthority("ADMIN", "EDITOR") // ADMIN and EDITOR can update
                        .requestMatchers(HttpMethod.DELETE, "/api/countries/delete/**").hasAuthority("ADMIN") // Only ADMIN can delete
                        .requestMatchers("/api/countries/view/**", "/api/countries/list").permitAll() // Everyone can view and list

                        // States related permissions
                        .requestMatchers(HttpMethod.POST, "/api/states/create").hasAuthority("ADMIN") // Only ADMIN can create states
                        .requestMatchers(HttpMethod.PUT, "/api/states/update/**").hasAnyAuthority("ADMIN", "EDITOR") // ADMIN and EDITOR can update states
                        .requestMatchers(HttpMethod.DELETE, "/api/states/delete/**").hasAuthority("ADMIN") // Only ADMIN can delete states
                        .requestMatchers("/api/states/view/**", "/api/states/list").permitAll() // Everyone can view and list states

                        // Other permissions
                        .anyRequest().authenticated() // All other requests need authentication
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/home", true) // Redirect to /home after successful login
                        .permitAll() // Allow access to the login page
                )
                .logout(logout -> logout.permitAll()) // Allow logout for all users
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/api/countries/403") // Custom access denied page
                );

        return http.build();
    }
}
