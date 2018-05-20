package shortly;

import shortly.model.Account;
import shortly.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
* Configuration for REST endpoints
*/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	/**
	* Basic authentication configuration
	*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(Account.PASSWORD_ENCODER);
	}

	/**
	* Require basic authentication for /register and /statistic
	* Require no authentication for other endpoints.
	*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.httpBasic()
			.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/register").authenticated()
				.antMatchers(HttpMethod.GET, "/statistic/**").authenticated()
				.antMatchers(HttpMethod.POST, "/account").permitAll()
				.antMatchers(HttpMethod.GET, "/**").permitAll()
			.and().csrf().disable();
	}
}
