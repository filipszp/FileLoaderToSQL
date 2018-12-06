package pl.profsoft.lomboktest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import pl.profsoft.lomboktest.entities.Client;
import pl.profsoft.lomboktest.service.ClientService;

/**
 *
 * @author filip
 */
public class LombokTest {

	/**
	 * @param args the command line arguments
	 */
	private final Validator validator;
	private final static String ERROR_LOG = "client_errors.log";

	public LombokTest() {
		ValidatorFactory validationFactory = Validation.buildDefaultValidatorFactory();
		validator = validationFactory.getValidator();
	}

	public static void main(String[] args) throws IOException, ParseException {
		LombokTest test = new LombokTest();

		if (args.length > 0) {
			File file = new File(args[0]);
			FileAdapterReader fileAdapter = new FileAdapterReader();
			Map<Integer, String> fileLines = fileAdapter.readFile(file);
			Map<Integer, String> validateResult = fileAdapter.validateFile(fileLines);
			if (validateResult.isEmpty()) {
				Set<Client> clientList = fileAdapter.parseFileToClient(fileLines);
				//clientList.forEach(client -> System.out.println(client.toString()));

				ClientService service = new ClientService();
				service.createClients(clientList);

			} else {
				//write errors
				System.err.println("Validation errors count: " + validateResult.size());
				System.err.println("Writing errors to " + ERROR_LOG);

				FileAdapterWriter fileAdapterWriter = new FileAdapterWriter();
				fileAdapterWriter.saveValidationResult(ERROR_LOG, validateResult);
			}
		} else {
			System.err.println("No file in arguments");
		}
	}

	private void showSimpleValidation(Client client) {
		Set<ConstraintViolation<Client>> validationErrors = validator.validate(client);
		for (ConstraintViolation<Client> validationError : validationErrors) {
			System.out.println(validationError.getPropertyPath().toString() + " " + validationError.getMessage());
		}
	}
}
