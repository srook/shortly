package shortly.service;

import shortly.StringUtils;
import shortly.model.Account;
import shortly.service.AccountService;
import shortly.repository.AccountRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	/**
	* Generate random password and create account if username doesn't exist
	* @param Account to create
	* @return true if created
	*/
	@Override
	public boolean create(Account account) {
		if (accountRepository.checkIfExists(account.getUsername())) {
			return false;
		}
		else {
			String password = StringUtils.generateRandomString(8);
			account.setPassword(password);
			accountRepository.create(account);
		}
		return true;
	}

	/**
	* @param Username to search
	* @return Account if found
	*/
	@Override
	public Account findByUsername(String username) {
		return accountRepository.findByUsername(username);
	}
}
