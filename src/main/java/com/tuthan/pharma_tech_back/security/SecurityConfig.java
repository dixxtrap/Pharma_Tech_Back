package com.tuthan.pharma_tech_back.security;

import com.tuthan.pharma_tech_back.filter.CustomAuthentificationfilter;
import com.tuthan.pharma_tech_back.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthentificationfilter customAuthentificationfilter =new CustomAuthentificationfilter(authenticationManagerBean());
           customAuthentificationfilter.setFilterProcessesUrl("/api/login");
       // customAuthentificationfilter.setFilterProcessesUrl("/api/token/refresh");
        http.csrf().disable();
       http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("**").permitAll();
        http.authorizeRequests().antMatchers("/api/login/**","/api/token/refresh/**").permitAll();
//       http.authorizeRequests().antMatchers(GET,"/api/user/**").hasAnyAuthority("Role_Admin");
//       http.authorizeRequests().antMatchers(POST,"/api/user/save/**").hasAnyAuthority("Role_Pharmacien");
       http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthentificationfilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{

        return super.authenticationManagerBean();
    }

}
