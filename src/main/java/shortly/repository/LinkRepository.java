package shortly.repository;

import shortly.model.Link;
import shortly.model.Account;

/**
* Interface for LinkRepository
*/
public interface LinkRepository {
	void create(Link link);
	void update(Link link);

	boolean checkIfExists(String key);
	Link findByKey(String key);
	Link findByUrl(String url, Account account);
}
