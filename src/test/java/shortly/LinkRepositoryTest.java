package shortly;

import shortly.model.Link;
import shortly.repository.LinkRepository;
import shortly.repository.LinkRepositoryImpl;

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
public class LinkRepositoryTest {

	@TestConfiguration
	static class LinkRepositoryTestContextConfiguration {

		@Bean
		public LinkRepository linkRepository() {
			return new LinkRepositoryImpl();
		}
	}

	@Autowired private TestEntityManager entityManager;
	@Autowired private LinkRepository linkRepository;

	@Test
	public void returnLinkIfKeyFound() {
		Link found = linkRepository.findByKey("testshort");
		assertThat(found.getKey()).isEqualTo("testshort");
	}

}