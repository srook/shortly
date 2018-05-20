package shortly.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

/**
* Basic user representation
*/
@Entity
@Table(name = "accounts")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account extends BaseModel {
	@Column(name = "username", unique = true)
	private String username;
	private String encrypted_password;

	@JoinColumn(name = "account_id")
	@OneToMany(targetEntity = Link.class, fetch = FetchType.LAZY)
    private List<Link> links = new ArrayList<>();

	@Transient
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder(11);

	/**
	* Plain text password, set before storing to DB
	*/
	@Transient
	private String password;

	public Account(){}

	@JsonCreator
	public Account(@JsonProperty(value = "AccountId", required = true) String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getEncryptedPassword() {
		return this.encrypted_password;
	}

	public void setPassword(String password) {
		this.password = password;
		this.encrypted_password = PASSWORD_ENCODER.encode(password);
	}

	public List<Link> getLinks() {
		return this.links;
	}

	public Long getId() {
		return this.id;
	}
}
