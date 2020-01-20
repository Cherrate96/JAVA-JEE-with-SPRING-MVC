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

/**
 * @author ADAM CHERRATE
 *
 */
@EnableWebSecurity
@Configuration
public class websec  extends WebSecurityConfigurerAdapter{
   @Autowired
	private javax.sql.DataSource dataSource;
   @Autowired
	private BCryptPasswordEncoder bcpe;
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login");
		http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
		http.exceptionHandling().accessDeniedPage("/403");
		
	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder bcpe=getBCPE();
		auth.jdbcAuthentication()
		.dataSource(dataSource).usersByUsernameQuery("SELECT USERNAME AS PRINCIPAL,PASSWROD AS CREDENTIALS,ACTIVE FROM USERS WHERE USERNAME =?")
		.authoritiesByUsernameQuery("SELECT USERNAME as PRINCIPAL,ROLES as ROLE FROM USERS_ROLES WHERE USERNAME=?")
		.rolePrefix("ROLES_").passwordEncoder(getBCPE());
		
	}

	@Bean
BCryptPasswordEncoder getBCPE()

{
	return new BCryptPasswordEncoder();
}

}
