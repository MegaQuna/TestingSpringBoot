package com.test.woloszkiewicz.config;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		//.antMatchers("/upload").permitAll()
		.antMatchers("/app/index").permitAll()
		.antMatchers("/app/login").permitAll()
		.antMatchers("/app/info").hasAnyRole("USER", "ADMIN")
		.antMatchers("/app/test").hasAnyRole("USER", "ADMIN")
		.antMatchers("/js/adpanelcss/**").hasAnyRole("ADMIN")
		.antMatchers("/js/adpaneljs/**").hasAnyRole("ADMIN")
		.antMatchers("/admin/**").hasAnyRole("ADMIN")
		.antMatchers("/account/**").hasAnyRole("ADMIN")
		.antMatchers("/course/**").hasAnyRole("ADMIN")
		.antMatchers("/group/**").hasAnyRole("ADMIN")
		.antMatchers("/question/**").hasAnyRole("ADMIN")
		.antMatchers("/testsettings/**").hasAnyRole("ADMIN")
		.antMatchers("/test/**").hasAnyRole("USER", "ADMIN")
		.and().formLogin()  //login configuration
        .loginPage("/app/login")
        .loginProcessingUrl("/app-login")
        .usernameParameter("app_username")
        .passwordParameter("app_password")
        .defaultSuccessUrl("/app/info")	
		.and().logout()    //logout configuration
		.logoutUrl("/app-logout") 
		.logoutSuccessUrl("/app/index")
		.and().exceptionHandling() //exception handling configuration
		.accessDeniedPage("/app/error")
		.and()
		.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false);
	} 
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
	    auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());
	}
    
    @Override
	public void configure(WebSecurity web) {
    	web.ignoring().antMatchers("/css/all/**", "/img/**");
	    //web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
	}
}