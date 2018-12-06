/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.profsoft.lomboktest;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import pl.profsoft.lomboktest.entities.Client;

/**
 *
 * @author filip
 */
public class FileAdapterReader {

	private final Validator validator;
	private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public FileAdapterReader() {
		ValidatorFactory validationFactory = Validation.buildDefaultValidatorFactory();
		validator = validationFactory.getValidator();
	}

	public Map<Integer, String> readFile(File file) throws IOException {
		System.out.println("#reading file: " + file.getName());
		Map<Integer, String> lines = new HashMap<>();
		try (Scanner sc = new Scanner(file, "UTF-8")) {
			int line = 1;
			while (sc.hasNextLine()) {
				lines.put(line, sc.nextLine());
				line++;
			}
			System.out.println("#file has " + lines.size() + " lines");
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		}
		return lines;
	}

	public Map<Integer, String> validateFile(Map<Integer, String> fileLines) {
		Map<Integer, String> validatorResult = new HashMap<>();

		fileLines.forEach((k, v) -> {
			String[] clientSplit = v.split("\\s*\\|\\s*");
			try {
				Client client = new Client(clientSplit[0], dateFormat.parse(clientSplit[1]));
				Set<ConstraintViolation<Client>> validationErrors = validator.validate(client);
				if (!validationErrors.isEmpty()) {
					validationErrors.forEach(result -> {
						validatorResult.put(k, result.getPropertyPath().toString() + " " + result.getMessage());
					});

				}
			} catch (ParseException ex) {
				Logger.getLogger(FileAdapterReader.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		);
		return validatorResult;
	}

	public Set<Client> parseFileToClient(Map<Integer, String> fileLines) {
		Set<Client> clientList = new HashSet<>();
		fileLines.forEach((k, v) -> {
			String[] clientSplit = v.split("\\s*\\|\\s*");
			try {
				Client client = new Client(clientSplit[0], dateFormat.parse(clientSplit[1]));
				clientList.add(client);
			} catch (ParseException ex) {
				Logger.getLogger(FileAdapterReader.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		return clientList;
	}
}
