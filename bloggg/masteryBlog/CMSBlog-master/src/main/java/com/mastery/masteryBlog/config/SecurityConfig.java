/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mastery.masteryBlog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author shirley
 */
/* @Configuration - lets Spring know that this class is being used for
configuration
 */
@Configuration
/*@EnableWebSecurity - indicates that Spring Security should use this class
to set itself up */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetails;

    @Autowired
    public void configureGlobalInDB(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /* antMatchers - match against paths into our app & indicating
                the type of security for that path
                
                hasRole - security method that limits to a specific role while
                permitsAll lets everyone access a path
                
                anyRequest - used at the end of the section, its indicating the
                security for any request that doesn't match an existing pattern
                 */
                .authorizeRequests()
                .antMatchers("/admin-post", "/employee-post", "/user-management", "/editPost", "/pending-posts", "/submit-pending").hasRole("ADMIN")
                .antMatchers("/employee-post").hasAnyRole("CONTENTMANAGER", "ADMIN")
                .antMatchers("/", "/home", "/categories", "/show-category", "/static").permitAll()
                .antMatchers("/css/**", "/js/**","/images/**", "/fonts/**").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?login_error=1")
                .permitAll()
                .and()
                /**
                 * Below, we are setting up the login procedure - the login page
                 * and failure URL && logging out page where it redirects when
                 * you have successfully logged out.
                 */
                .logout()
                .logoutSuccessUrl("/")
                .permitAll();
    }

}
