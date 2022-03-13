package com.jewelry;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/webjars/**", "/css/**", "/js/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// jsとcss、新規登録画面はだけでも遷移可能
		http.authorizeRequests()
			.antMatchers("/css/**", "/js/**", "/login", "/error")
			.permitAll()
			.anyRequest()
			.authenticated();

		// ログイン
		http.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")
			.failureUrl("/login")
			.defaultSuccessUrl("/salesList")
			.permitAll()
			.and()
			.logout()
			.permitAll();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withDefaultPasswordEncoder()
			.username("admin")
			.password("admin")
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(user);
	}

}
