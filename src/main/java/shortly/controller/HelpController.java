package shortly;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelpController {

	/**
	* Help page
	*/
	@GetMapping("/help")
	public String help() {
		return "help";
	}

}