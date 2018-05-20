package shortly.controller;

import shortly.model.Account;
import shortly.model.Link;
import shortly.service.AccountService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
* REST controller for working with Account model
*/
@RestController
public class AccountController {
	@Autowired
	private AccountService accountService;
	private ObjectMapper mapper = new ObjectMapper();

	/**
	* Create new Account and generate random password
	* @param JSON body with AccountId
	* @return HTTP 201 if created, 409 if Username already exists
	*/
	@RequestMapping(value = "/account", method = RequestMethod.POST,
		consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity register(@RequestBody Account account) {
		ObjectNode objectNode = this.mapper.createObjectNode();
		if (accountService.create(account)) {
			objectNode.put("success", true);
			objectNode.put("description", "Successfully added account");
			objectNode.put("password", account.getPassword());
			return ResponseEntity.status(HttpStatus.CREATED).body(objectNode);
		}
		else {
			objectNode.put("success", false);
			objectNode.put("description", "Error creating account. AccountId already exists.");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(objectNode);
		}
	}
}
