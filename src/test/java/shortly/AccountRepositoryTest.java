package shortly;

import shortly.model.Account;
import shortly.repository.AccountRepository;
import shortly.repository.AccountRepositoryImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

	@TestConfiguration
	static class AccountRepositoryTestContextConfiguration {

		@Bean
		public AccountRepository accountRepository() {
			return new AccountRepositoryImpl();
		}
	}

	@Autowired private TestEntityManager entityManager;
	@Autowired private AccountRepository accountRepository;

	@Test
	public void returnAccountIfUsernameFound() {
		Account acc = new Account("shortlyTest");
		acc.setPassword("testpwd1");
		entityManager.persistAndFlush(acc);

		Account found = accountRepository.findByUsername("shortlyTest");
		assertThat(found.getUsername()).isEqualTo("shortlyTest");
	}

}