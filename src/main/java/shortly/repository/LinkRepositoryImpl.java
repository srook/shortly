package shortly.repository;

import shortly.model.Link;
import shortly.model.Account;

import org.springframework.stereotype.Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class LinkRepositoryImpl implements LinkRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void create(Link link) {
		entityManager.persist(link);
	}

	@Override
	public void update(Link link) {
		entityManager.merge(link);
	}

	/**
	* @param Short URL key to search
	* @return True if Link found
	*/
	@Override
	public boolean checkIfExists(String key) {
		return findByKey(key) != null;
	}

	/**
	* @param Link short key
	* @return Link matching specified key
	*/
	@Override
	public Link findByKey(String key) {
		List<Link> links = entityManager
			.createQuery("select l from Link l where l.key = :key", Link.class)
			.setParameter("key", key).getResultList();
		return links.isEmpty() ? null : links.get(0);
	}

	/**
	* @param URL to find
	* @param Account Link belongs to
	* @return Link matching params
	*/
	@Override
	public Link findByUrl(String url, Account account) {
		List<Link> links = entityManager
			.createQuery("select l from Link l where l.url = :url and l.account = :account", Link.class)
			.setParameter("url", url).setParameter("account", account).getResultList();
		return links.isEmpty() ? null : links.get(0);
	}
}
