package shortly;

import shortly.model.Link;
import shortly.repository.LinkRepository;
import shortly.service.LinkService;
import shortly.service.LinkServiceImpl;

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
public class LinkServiceTest {

	@TestConfiguration
	static class LinkServiceTestContextConfiguration {

		@Bean
		public LinkService linkService() {
			return new LinkServiceImpl();
		}
	}

	@Autowired private LinkService linkService;
	@MockBean private LinkRepository linkRepository;

	@Before
	public void setup() {
		Link testLink = new Link("http://shortly.net/test/link/long", null);
		testLink.setKey("longkey");

		Mockito.when(linkRepository.findByKey("longkey")).thenReturn(testLink);
		Mockito.when(linkRepository.findByKey("not_found")).thenReturn(null);
		Mockito.when(linkRepository.checkIfExists("longkey")).thenReturn(true);
		Mockito.when(linkRepository.checkIfExists("not_found")).thenReturn(false);
	}

	@Test
	public void existingKeyShouldReturnLink() {
		Link link = linkService.findByKey("longkey");
		assertThat(link.getKey()).isEqualTo("longkey");
	}

	@Test
	public void notExistingKeyShouldReturnNull() {
		Link link = linkService.findByKey("not_found");
		assertThat(link).isEqualTo(null);
	}

	@Test
	public void linkCreateShouldGenerateKey() {
		Link newLink = new Link("http://shortly.net/get/this/long/url", null);
		linkService.create(newLink);
		assertThat(newLink.getKey().length()).isEqualTo(6);
	}

	@Test
	public void incrementAndUpdateShouldIncrementVisitCount() {
		Link link = linkService.findByKey("longkey");
		Integer old_count = link.getCount();
		linkService.incrementAndUpdate(link);
		assertThat(link.getCount()).isEqualTo(old_count + 1);
	}
}
