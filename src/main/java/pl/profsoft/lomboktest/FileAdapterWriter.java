package pl.profsoft.lomboktest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

/**
 *
 * @author filip
 */
public class FileAdapterWriter {

	public void saveValidationResult(String fileName, Map<Integer, String> validationResult) throws IOException {
		File fileExist = new File(fileName);
		File fileToSave;
		if (fileExist.exists()) {
			fileExist.delete();
			fileToSave = new File(fileName);
		} else {
			fileToSave = new File(fileName);
		}
		List<String> firstLine = Arrays.asList("OPERATION AT: " + Calendar.getInstance().getTime().toString());
		FileUtils.writeLines(fileToSave, firstLine);
		validationResult.forEach((line, err) -> {
			try {
				//FileUtils.writeStringToFile(fileToSave, new StringBuilder("line: " + line + "; " + err).toString(), "UTF-8", true);
				FileUtils.writeLines(fileExist, Arrays.asList(new StringBuilder("line: " + line + "; " + err).toString()), true);

			} catch (IOException ex) {
				Logger.getLogger(FileAdapterWriter.class.getName()).log(Level.SEVERE, null, ex);
			}

		});

	}

}
