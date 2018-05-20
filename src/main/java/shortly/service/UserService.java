package shortly.service;

import shortly.repository.AccountRepository;
import shortly.model.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import java.util.Collections;

@Component
public class UserService implements UserDetailsService {

	@Autowired
	AccountRepository accounts;

	/**
	* Used by WebSecurityConfigurerAdapter for basic authorization
	* @param username Userto load
	* @return MyUser if found
	*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accounts.findByUsername(username);
		if (account == null){
			throw new UsernameNotFoundException(username + " was not found");
		}
		return new MyUser(account);
	}
}
