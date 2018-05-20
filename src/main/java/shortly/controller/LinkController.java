package shortly.controller;

import shortly.model.Link;
import shortly.model.Account;
import shortly.service.MyUser;
import shortly.service.LinkService;
import shortly.service.AccountService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

@RestController
public class LinkController {
	@Autowired private LinkService linkService;
	@Autowired private AccountService accountService;

	private ObjectMapper mapper = new ObjectMapper();

	/**
	* Register new link and generate short URL
	* @param JSON body with url and optional redirectType
	* @return HTTP 201 if created, 409 if URL already exists for this User
	*/
	@RequestMapping(value = "/register", method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity register(@RequestBody Link link, @RequestHeader("Host") String host) {
		MyUser user = (MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ObjectNode objectNode = this.mapper.createObjectNode();
		link.setAccount(user.getAccount());
		if (linkService.create(link)) {
			objectNode.put("success", true);
			objectNode.put("shortUrl", buildShortUrl(link.getKey(), host));
			return ResponseEntity.status(HttpStatus.CREATED).body(objectNode);
		}
		else {
			Link existingLink = linkService.findByUrl(link.getUrl(), user.getAccount());
			objectNode.put("success", false);
			objectNode.put("description", "Error creating link. Specified URL already exists for your account.");
			objectNode.put("shortUrl", buildShortUrl(existingLink.getKey(), host));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(objectNode);
		}
	}

	/**
	* Redirect all short URLs to specified Location
	*/
	@RequestMapping(value = "/{key}", method = RequestMethod.GET)
	public ResponseEntity redirect(@PathVariable String key) {
		Link link = linkService.findByKey(key);
		if (link == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		linkService.incrementAndUpdate(link);
		return ResponseEntity.status(link.getRedirectCode()).header(HttpHeaders.LOCATION, link.getUrl()).build();
	}

	/**
	* Get statistics for registered URLs
	*/
	@RequestMapping(value = "/statistic/{username}", method = RequestMethod.GET)
	public ResponseEntity statistic(@PathVariable String username) {
		Account account = accountService.findByUsername(username);
		if (account == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		else {
			List<Link> links = account.getLinks();
			ObjectNode objectNode = this.mapper.createObjectNode();
			for (Link link : links) {
				objectNode.put(link.getUrl(), link.getCount());
			}
			return ResponseEntity.status(HttpStatus.OK).body(objectNode);
		}
	}

	private String buildShortUrl(String key, String host) {
		return "http://" + host + "/" + key;
	}
}
