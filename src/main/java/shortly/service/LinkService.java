package shortly.service;

import shortly.model.Link;
import shortly.model.Account;

/**
* Link Service interface
*/
public interface LinkService {
	boolean create(Link link);
	Link findByKey(String key);
	Link findByUrl(String url, Account account);
	void incrementAndUpdate(Link link);
}
