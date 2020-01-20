/**
 * 
 */
package com.home.security;

import javax.activation.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Md4PasswordEncoder;

/**
 * @author ADAM CHERRATE
 *
 */

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
@Autowired
	private BCryptPasswordEncoder bcpe;
	@Autowired
	private javax.sql.DataSource dataSource;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
http.formLogin().loginPage("/login");
http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
http.exceptionHandling().accessDeniedPage("/403");
		
	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder BCPE=getBCPE();
		System.out.println(bcpe.encode("1234"));
		auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("SELECT USERNAME AS PRINCIPAL,PASSWORD AS CREDENTIALS,ACTIVE FROM USERS WHERE USERNAME=?")
		.authoritiesByUsernameQuery("SELECT USERNAME AS PRINCIPAL,ROLES AS ROLE FROM USERS_ROLES WHERE USERNAME=?")
		.rolePrefix("ROLE_").passwordEncoder(getBCPE());
		
		

		


	}
	@Bean
	BCryptPasswordEncoder getBCPE()
	{
		return new BCryptPasswordEncoder();
	}
}
