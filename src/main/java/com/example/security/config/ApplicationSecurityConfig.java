package com.example.security.config;

import com.example.security.jwt.JwtConfig;
import com.example.security.jwt.JwtTokenVerifier;
import com.example.security.jwt.JwtUserNamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.example.security.config.ApplicationUserPermission.*;
import static com.example.security.config.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig {


    private final JwtConfig jwtConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ApplicationSecurityConfig(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(new JwtUserNamePasswordAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtConfig))
            .addFilterAfter(new JwtTokenVerifier(jwtConfig), JwtUserNamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/students/**").hasRole(STUDENT.name())
            .anyRequest()
            .authenticated();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * Creates in memory users
     * @return
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails luke = User.withUsername("Luke")
                .password(passwordEncoder.encode("pass"))
                .roles(STUDENT.name()) // ROLE_STUDENT Role based
                .build();

        UserDetails leia = User.withUsername("Leia")
                .password(passwordEncoder.encode("pass") )
                .authorities(STUDENT.name(), STUDENT_READ.getPermission(),COURSE_READ.getPermission(),
                        STUDENT_WRITE.getPermission(), COURSE_WRITE.getPermission())
                //.roles(ADMIN.name()) // ROLE_ADMIN Authority based
                .build();

        UserDetails tom = User.withUsername("Tom")
                .password(passwordEncoder.encode("pass") )
                .authorities(STUDENT_READ.getPermission(), COURSE_READ.getPermission())
                //.roles(ADMIN_TRAINEE.name()) // ROLE_ADMIN_TRAINEE Authority based
                .build();

        return new InMemoryUserDetailsManager(luke,leia, tom);
    }

}
