package shortly.service;

import shortly.StringUtils;
import shortly.model.Link;
import shortly.model.Account;
import shortly.service.LinkService;
import shortly.repository.LinkRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LinkServiceImpl implements LinkService {

	@Autowired
	private LinkRepository linkRepository;

	/**
	* Generate unique key and insert Link to DB.
	* @param Link to create
	* @return true if created
	*/
	@Override
	public boolean create(Link link) {
		String key = null;
		Link existing = findByUrl(link.getUrl(), link.getAccount());
		if (existing == null) {
			while (key == null) {
				key = StringUtils.generateRandomString(6);
				if (linkRepository.checkIfExists(key)) {
					key = null;
				}
			}
			link.setKey(key);
			linkRepository.create(link);
			return true;
		}
		else {
			return false;
		}
	}

	/**
	* @param key to find
	* @return Link with specified key
	*/
	@Override
	public Link findByKey(String key) {
		return linkRepository.findByKey(key);
	}

	/**
	* @param url to find
	* @param account Link belongs to
	* @return found Link
	*/
	@Override
	public Link findByUrl(String url, Account account) {
		return linkRepository.findByUrl(url, account);
	}

	/**
	* Increment hit count by 1
	* @param link to update
	*/
	@Override
	public void incrementAndUpdate(Link link) {
		link.incrementCount();
		linkRepository.update(link);
	}
}
