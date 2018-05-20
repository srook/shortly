package shortly.service;

import shortly.model.Account;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.AuthorityUtils;

/**
* Extend org.springframework.security.core.userdetails.User to allow setting
* Account on object
*/
public class MyUser extends User {

	private Account account;

	/**
	* Set account and call super with account parameters
	*/
	public MyUser (Account account) {
		super(account.getUsername(), account.getEncryptedPassword(), AuthorityUtils.createAuthorityList(new String [] {}));
		this.account = account;
	}

	/**
	* @return Account getter
	*/
	public Account getAccount() {
		return this.account;
	}

}