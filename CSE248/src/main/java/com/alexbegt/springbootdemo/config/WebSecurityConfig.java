package com.alexbegt.springbootdemo.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/", "/registration-form", "/registration", "/css/**", "/webjars/**", "/images/**").permitAll()
      .antMatchers("/home").hasAnyAuthority("USER")
      .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
      .antMatchers("/admin/**").hasAuthority("ADMIN")
      .anyRequest().authenticated()
      .and()
      .formLogin()
      .loginPage("/login")
      .successForwardUrl("/home")
      .permitAll()
      .and()
      .logout()
      .logoutUrl("/logout")
      .invalidateHttpSession(true)
      .logoutSuccessUrl("/login?loggedOut")
      .permitAll();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
