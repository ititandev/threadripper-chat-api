package org.ititandev.config;

import javax.sql.DataSource;

import org.ititandev.security.JWTAuthenticationEntryPoint;
import org.ititandev.security.JWTAuthenticationFilter;
import org.ititandev.security.JWTLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private DataSource dataSource;
	
	@Autowired
    private JWTAuthenticationEntryPoint unauthorizedHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()
				.antMatchers(HttpMethod.POST, "/api/signup").permitAll()
				.antMatchers(HttpMethod.POST, "/api/signup2").permitAll()
				.antMatchers(HttpMethod.GET, "/api/verify/resend/{username}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/verify/{username}/{hash}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/forgot/{username}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/user").permitAll()
				.antMatchers(HttpMethod.GET, "/email").permitAll()
				.antMatchers(HttpMethod.GET, "/api/avatar/{filename}.{ext}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/image/{filename}.{ext}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/file/**").permitAll()
				.antMatchers(HttpMethod.GET, "/index.html").permitAll()
				.antMatchers(HttpMethod.GET, "/css/main.css").permitAll()
				.antMatchers(HttpMethod.GET, "/js/main.js").permitAll()
				.anyRequest().permitAll()
				.and()
			.addFilterBefore(new JWTLoginFilter("/api/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.headers().cacheControl();
	}	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT username, password, NOT `lock` FROM user WHERE username=?")
		.authoritiesByUsernameQuery("SELECT username, \"USER_ROLE\" as role FROM user WHERE username=?")
		.passwordEncoder(new BCryptPasswordEncoder());
	}
}
