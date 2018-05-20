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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class AccountControllerTest extends BaseControllerTestHelper {
	@Test
	public void createAccount() throws Exception {
		ObjectNode objectNode = this.mapper.createObjectNode();
		objectNode.put("AccountId", "shortlyTestAccount");

		this.mockMvc
			.perform(
				post("/account")
				.content(json(objectNode))
				.contentType(contentType)
			).andExpect(status().isCreated())
			.andExpect(jsonPath("$.password").exists())
			.andExpect(jsonPath("$.success", is(true)));
	}

	@Test
	public void accountIdIsRequired() throws Exception {
		ObjectNode objectNode = mapper.createObjectNode();
		this.mockMvc
			.perform(
				post("/account")
				.content(json(objectNode))
				.contentType(contentType)
			).andExpect(status().isBadRequest());
	}

}
