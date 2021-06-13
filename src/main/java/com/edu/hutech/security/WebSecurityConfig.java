package com.edu.hutech.security;

import com.edu.hutech.config.UserAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()

                .antMatchers("/css/**", "/js/**", "/img/**", "/file/upload/**").permitAll()

                .antMatchers("/trainee-management/**").hasAnyAuthority("ROLE_TRAINEE", "ROLE_ADMIN")
                .antMatchers("/trainer-management/**").hasAnyAuthority("ROLE_TRAINER", "ROLE_ADMIN")
                .antMatchers("/dashboard/**","/class-management/**","/").hasAnyAuthority("ROLE_TRAINER", "ROLE_ADMIN","ROLE_TRAINEE")

                .and()
                .exceptionHandling().authenticationEntryPoint(new UserAccessDeniedHandler())
                .accessDeniedPage("/404")

                .and()
                .formLogin() // Submit URL of login page.
                .loginPage("/login")//
                .permitAll().successHandler(authenticationSuccessHandler)
//                .usernameParameter("username")
//                .passwordParameter("password")
                .failureUrl("/login?error=true")

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();



    }
}
