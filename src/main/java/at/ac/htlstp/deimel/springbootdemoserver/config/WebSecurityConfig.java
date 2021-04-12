package at.ac.htlstp.deimel.springbootdemoserver.config;

import at.ac.htlstp.deimel.springbootdemoserver.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private final CustomUserDetailsService customUserDetailsService;

    public WebSecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()
                .csrf().disable()
                .headers()
                .frameOptions().disable()
                .cacheControl().disable()
                .and()
                .authorizeRequests()
                .antMatchers(Endpoints.css + "/**").permitAll()
                .antMatchers(Endpoints.login).permitAll()
                .antMatchers(Endpoints.demo).hasAuthority("ROLE_GUEST")
                .antMatchers(Endpoints.datenbankMVC).hasAuthority("ROLE_USER")
                .antMatchers(Endpoints.datenbankRest).hasAuthority("ROLE_USER")
                .antMatchers(Endpoints.maxima).hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage(Endpoints.accesDenied)
                .and()
                .formLogin().loginPage(Endpoints.login).and()
                .logout().invalidateHttpSession(true).logoutUrl(Endpoints.logout)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation()
                .newSession()
        ;
    }
}
