package com.alexbegt.ghostkitchen.spring;

import com.alexbegt.ghostkitchen.security.google2fa.TwoFactorAuthenticationProvider;
import com.alexbegt.ghostkitchen.security.google2fa.TwoFactorWebAuthenticationDetailsSource;
import com.alexbegt.ghostkitchen.security.location.DifferentLocationChecker;
import com.alexbegt.ghostkitchen.security.login.GhostKitchenRememberMeServices;
import com.alexbegt.ghostkitchen.util.Defaults;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

@Configuration
@ComponentScan(basePackages = { "com.alexbegt.ghostkitchen.security" })
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private DataSource dataSource;

  @Autowired
  private AuthenticationSuccessHandler myAuthenticationSuccessHandler;

  @Autowired
  private LogoutSuccessHandler myLogoutSuccessHandler;

  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;

  @Autowired
  private TwoFactorWebAuthenticationDetailsSource authenticationDetailsSource;

  @Autowired
  private DifferentLocationChecker differentLocationChecker;

  public SecSecurityConfig() {
    super();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider());
  }

  @Override
  public void configure(final WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/resources/**", "/css/**", "/images/**", "/webjars/**");
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable()
      .authorizeRequests()
      .antMatchers("/h2/**").permitAll() // to enable access to H2 db's console
      .antMatchers("/login*", "/forgot-password*", "/reset-password*",
        "/sign-up*", "/confirm-registration*", "/two-factor-qr*", "/invalid-verification-token*", "/new-login-location-detected*",
        "/api/reset-password*", "/api/save-password*", "/api/register-user*", "/api/change-password*",
        "/api/disable-two-factor-authentication*", "/api/change-address*", "/api/change-credit-card",
        "/css/**", "/webjars/**", "/images/**", "/favicon.ico").permitAll()
      .antMatchers("/change-password*").hasAuthority(Defaults.CHANGE_PASSWORD_PRIVILEGE)
      .anyRequest().hasAuthority(Defaults.READ_PRIVILEGE)
      .and()
      .formLogin()
      .loginPage("/login")
      .defaultSuccessUrl("/home")
      .failureUrl("/login?error")
      .successHandler(this.myAuthenticationSuccessHandler)
      .failureHandler(this.authenticationFailureHandler)
      .authenticationDetailsSource(this.authenticationDetailsSource)
      .permitAll()
      .and()
      .sessionManagement()
      .invalidSessionUrl("/login?expired")
      .maximumSessions(1).sessionRegistry(this.sessionRegistry()).and()
      .sessionFixation().none()
      .and()
      .logout()
      .logoutSuccessHandler(this.myLogoutSuccessHandler)
      .invalidateHttpSession(false)
      .logoutSuccessUrl("/login?loggedOut")
      .deleteCookies("JSESSIONID")
      .permitAll()
      .and()
      .rememberMe().rememberMeServices(this.rememberMeServices()).key("ghostKitchenRememberMe")
      .and()
      .headers().frameOptions().disable(); // this is needed to access the H2 db's console
  }

  @Bean
  public DaoAuthenticationProvider authProvider() {
    final TwoFactorAuthenticationProvider authProvider = new TwoFactorAuthenticationProvider();

    authProvider.setUserDetailsService(this.userDetailsService);
    authProvider.setPasswordEncoder(this.encoder());
    authProvider.setPostAuthenticationChecks(this.differentLocationChecker);

    return authProvider;
  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder(11);
  }

  @Bean
  public SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  public RememberMeServices rememberMeServices() {
    JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
    tokenRepositoryImpl.setDataSource(dataSource);
    tokenRepositoryImpl.setCreateTableOnStartup(true);

    return new GhostKitchenRememberMeServices("ghostKitchenRememberMe", this.userDetailsService, tokenRepositoryImpl);
  }

  @Bean
  public DatabaseReader databaseReader() throws IOException, GeoIp2Exception {
    final File resource = new File(this.getClass().getClassLoader().getResource("maxmind/GeoLite2-Country.mmdb").getFile());

    return new DatabaseReader.Builder(resource).build();
  }
}
