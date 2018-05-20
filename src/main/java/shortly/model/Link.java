package shortly.model;

import org.springframework.beans.factory.annotation.*;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;

import org.springframework.http.HttpStatus;

/**
* Link model used for storing shortend URLs
*/
@Entity
@Table(name = "links")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Link extends BaseModel {
	private String url;
	@Column(name = "key", unique = true)
	private String key;
	private Integer visit_count = 0;
	private Integer redirect_code = 302;

	@JoinColumn(name = "account_id")
	@ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
	private Account account;

	public Link(){}

	@JsonCreator
	public Link(@JsonProperty(value = "url", required = true) String url,
		@JsonProperty("redirectType") Integer redirect_code) {
		this.url = url;
		if (redirect_code != null) {
			if (redirect_code != 301 && redirect_code != 302) {
				throw new IllegalArgumentException("Allowed redirect codes are 301 and 302");
			}
			else {
				this.redirect_code = redirect_code;
			}
		}
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public String getUrl() {
		return this.url;
	}

	public Integer getRedirectCode() {
		return this.redirect_code;
	}

	public void incrementCount() {
		this.visit_count++;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Integer getCount() {
		return this.visit_count;
	}

	public Account getAccount() {
		return this.account;
	}

}
