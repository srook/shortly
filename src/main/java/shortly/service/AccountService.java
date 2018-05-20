package shortly.service;

import shortly.model.Account;

/**
* Account Service interface
*/
public interface AccountService {
	boolean create(Account account);
	Account findByUsername(String username);
}
