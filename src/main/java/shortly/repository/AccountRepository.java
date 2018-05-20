package shortly.repository;

import shortly.model.Account;

/**
* Interface for AccountRepository
*/
public interface AccountRepository {
	void create(Account account);
	boolean checkIfExists(String username);
	Account findByUsername(String username);
}
