package shortly.repository;

import shortly.model.Account;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
* Repository for accessing accounts from DB
*/
@Repository
public class AccountRepositoryImpl implements AccountRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(Account account) {
		entityManager.persist(account);
	}

	/**
	* @param Username to search
	* @return True if account found
	*/
	@Override
	public boolean checkIfExists(String username) {
		return findByUsername(username) != null;
	}

	/**
	* Find Account by username
	* @param Username to search
	* @return Account if found
	*/
	@Override
	public Account findByUsername(String username) {
		List<Account> accounts = entityManager
			.createQuery("select a from Account a where a.username = :username", Account.class)
			.setParameter("username", username).getResultList();
		return accounts.isEmpty() ? null : accounts.get(0);
	}

}
