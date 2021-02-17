package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.withDefaultPasswordEncoder() //nie uzywac przechowuje login haslo jako text // uzywac coderow
                .username("user")
                .password("root")       // Tworzenie uzytkownika z prawami usera
                .roles("USER")
                .build();

        UserDetails mod = User.withDefaultPasswordEncoder()
                .username("mod")
                .password("root")       // Tworzenie uzytkownika z prawami moderatora
                .roles("MODERATOR")
                .build();

        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("root")       // Tworzenie uzytkownika z prawami admina
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin, mod);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{ // metoda sprwadzajaca uprawnienia przed wywołaniem http
        http.httpBasic().and().authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api").permitAll()//przypisujemy uprwanienia do /api z metoda get
                .antMatchers(HttpMethod.POST,"/api").hasAnyRole("MODERATOR","ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api").hasRole("ADMIN")
                .anyRequest().hasRole("ADMIN") // kazde inne rządanie bedzie wymagalo roli admin
                .and()
                .formLogin().permitAll() // budowanie formularza logowania
        .and()
                .csrf().disable(); // pozwalamy na ataki csrf, pozwala uzywac POSTMAN
    }
}
