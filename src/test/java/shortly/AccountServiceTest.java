package shortly;

import shortly.model.Account;
import shortly.repository.AccountRepository;
import shortly.service.AccountService;
import shortly.service.AccountServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AccountServiceTest {

	@TestConfiguration
	static class AccountServiceTestContextConfiguration {

		@Bean
		public AccountService accountService() {
			return new AccountServiceImpl();
		}
	}

	@Autowired private AccountService accountService;
	@MockBean private AccountRepository accountRepository;

	@Before
	public void setup() {
		Account shortly = new Account("shortly");
		shortly.setPassword("testpwd1");

		Mockito.when(accountRepository.findByUsername("shortly")).thenReturn(shortly);
		Mockito.when(accountRepository.findByUsername("not_found")).thenReturn(null);
		Mockito.when(accountRepository.checkIfExists("shortly")).thenReturn(true);
		Mockito.when(accountRepository.checkIfExists("new_account")).thenReturn(false);
	}

	@Test
	public void existingUsernameShouldReturnAccount() {
		Account acc = accountService.findByUsername("shortly");
		assertThat(acc.getUsername()).isEqualTo("shortly");
	}

	@Test
	public void notExistingUsernameShouldReturnNull() {
		Account acc = accountService.findByUsername("not_found");
		assertThat(acc).isEqualTo(null);
	}

	@Test
	public void accountCreateShouldReturnFalseIfExists() {
		Account duplicate = new Account("shortly");
		assertThat(accountService.create(duplicate)).isEqualTo(false);
	}

	@Test
	public void accountCreateShouldReturnTrueIfCreated() {
		Account new_account = new Account("new_account");
		assertThat(accountService.create(new_account)).isEqualTo(true);
	}
}
