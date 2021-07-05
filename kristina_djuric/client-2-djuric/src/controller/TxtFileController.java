package controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class TxtFileController {

	public static String readTxtFile(String fileLocation) {
		String fileContent = "";
		try {
			BufferedReader fin = null;
			String s;
			fin = new BufferedReader(new InputStreamReader(new FileInputStream(fileLocation)));
			while ((s = fin.readLine()) != null) {
				fileContent += s;
			}
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

}
