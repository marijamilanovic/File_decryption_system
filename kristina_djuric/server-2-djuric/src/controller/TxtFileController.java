package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TxtFileController {

	public static void createAndWriteTxt(String fileLocation, String decryptedContent) {
		try {
			File decryptedFile = new File(fileLocation);
			if (decryptedFile.createNewFile()) {
				FileWriter myWriter = new FileWriter(decryptedFile);
				myWriter.write(decryptedContent);
				myWriter.close();
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}
}
