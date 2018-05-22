package com.challenge.provider.challengeprovider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.accounts.admin.username}")
    private String adminUsername;

    @Value("${app.accounts.admin.password}")
    private String adminPassword;

    /**
     * Spring security http configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/webjars", "/webjars/**").permitAll()
                .antMatchers("/challengeSources", "/challengeSources/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .httpBasic()
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
//                .logoutSuccessHandler(new LogoutSuccessHandler() {
//                    @Override
//                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//
//                    }
//                })
                .deleteCookies()
//            .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .and()
                .sessionManagement().sessionFixation().none()
            ;
    }

    /**
     * Defines user accounts in memory.
     */
    @Bean
    @Override
    public UserDetailsService userDetailsService() {

//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	    System.out.println(encoder.encode("..."));

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.builder()
                .username(adminUsername)
                .password(adminPassword)
                .roles("ANONYMOUS", "USER", "ADMIN")
                .build());
        return manager;
    }

    /**
     * Spring security password encoder to use.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
