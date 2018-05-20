package shortly;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class LinkControllerTest extends BaseControllerTestHelper {
	private final String user = "shortly";
	private final String password = "gWOqxpz2";

	@Test
	public void registerLinkRequiresAuthentication() throws Exception {
		ObjectNode objectNode = this.mapper.createObjectNode();
		this.mockMvc.perform(
			post("/register")
				.content(json(objectNode))
				.contentType(contentType)
			).andExpect(status().isUnauthorized()
		);
	}

	@Test
	public void shouldReturnShortendUrl() throws Exception {
		ObjectNode objectNode = this.mapper.createObjectNode();
		objectNode.put("url", "http://shortly.net/test/path");
		this.mockMvc
			.perform(
				post("/register")
				.with(httpBasic(this.user, this.password))
				.content(json(objectNode))
				.contentType(contentType)
				.header("Host","shortly.com")
			).andExpect(status().isCreated())
			.andExpect(jsonPath("$.shortUrl").exists());
	}

	@Test
	public void statisticRequiresAuthentication() throws Exception {
		ObjectNode objectNode = this.mapper.createObjectNode();
		this.mockMvc
			.perform(
				get("/statistic/shortly")
				.content(json(objectNode))
				.contentType(contentType)
			).andExpect(status().isUnauthorized());
	}

	@Test
	public void statisticShouldReturnHitCount() throws Exception {
		ObjectNode objectNode = this.mapper.createObjectNode();
		this.mockMvc
			.perform(
				get("/statistic/shortly")
				.with(httpBasic(this.user, this.password))
				.content(json(objectNode))
				.contentType(contentType)
			).andExpect(status().isOk())
			.andExpect(jsonPath("$.['http://shortly.org/test/controller/some/longer/path']", is(1)));
	}

	@Test
	public void getShortUrlShouldRedirect() throws Exception {
		ObjectNode objectNode = this.mapper.createObjectNode();
		this.mockMvc
			.perform(
				get("/testshort")
				.content(json(objectNode))
				.contentType(contentType)
			).andExpect(status().isFound())
			.andExpect(header().string("Location", "http://shortly.org/test/controller"));
	}

	@Test
	public void staticticShouldReturnNotFoundForNotExistingUser() throws Exception {
		ObjectNode objectNode = this.mapper.createObjectNode();
		this.mockMvc
			.perform(
				get("/statistic/invalid_user")
				.with(httpBasic(this.user, this.password))
				.content(json(objectNode))
				.contentType(contentType)
			).andExpect(status().isNotFound());
	}

	@Test
	public void registerShouldReturnConflictIfUrlExistsForAccount() throws Exception {
		ObjectNode objectNode = this.mapper.createObjectNode();
		objectNode.put("url", "http://shortly.org/test/controller");
		this.mockMvc
			.perform(
				post("/register")
				.with(httpBasic(this.user, this.password))
				.content(json(objectNode))
				.contentType(contentType)
				.header("Host","shortly.com")
			).andExpect(status().isConflict())
			.andExpect(jsonPath("$.shortUrl").exists());
	}
}
