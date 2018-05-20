package shortly;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import java.lang.Throwable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
* Response exception handler to generate generic response for defined exceptions
*/
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	* @return Returns HTTP 400 for InvalidDefinitionExcepiton
	*/
	@ExceptionHandler({ InvalidDefinitionException.class })
	public ResponseEntity handleBadRequest(final InvalidDefinitionException ex, final WebRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.put("description", ex.getCause().getLocalizedMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(objectNode);
	}

}
