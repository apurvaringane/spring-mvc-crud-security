package org.venom.springmvccrudsecurity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration {
    /*
    //authentication
    @Bean
    public InMemoryUserDetailsManager userDetails(){
        UserDetails pratik= User.builder()
                .username("pratik")
                .password("{noop}123")
                .roles("DEALER","VENDOR","CUSTOMER")
                .build();

        UserDetails venom=User.builder()
                .username("venom")
                .password("{noop}456")
                .roles("VENDOR","CUSTOMER")
                .build();

        UserDetails joker=User.builder()
                .username("joker")
                .password("{noop}789")
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(pratik,venom,joker);
    }
    */

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }
    //authorization
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests()
        http.authorizeHttpRequests(config->
                        config
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/get-product-form").hasRole("DEALER")
                                .requestMatchers("/save-product").hasRole("DEALER")
                                .requestMatchers("/update/{id}").hasRole("VENDOR")
                                .requestMatchers("/updateproduct").hasRole("VENDOR")
                                .requestMatchers("/delete/{id}").hasRole("DEALER")
                                .requestMatchers("/search").permitAll()
                                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .exceptionHandling(config->
                        config.accessDeniedPage("/access-denied"));
        return http.build();
    }
}
