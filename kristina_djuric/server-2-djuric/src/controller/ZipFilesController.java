package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import connector.DBConnector;
import model.ActionType;
import model.InteractionLog;

public class ZipFilesController {

	public static void zipFiles(String zippingLocation) {
		String[] filesForZip = new File("src/data").list();
		try {
			String zipFile = zippingLocation + "\\zipped_files.zip";
			byte[] buffer = new byte[1024];

			FileOutputStream fout = new FileOutputStream(zipFile);
			ZipOutputStream zout = new ZipOutputStream(fout);

			for (String f : filesForZip) {

				FileInputStream fin = new FileInputStream("src/data/" + f);
				zout.putNextEntry(new ZipEntry(f));

				int length;

				while ((length = fin.read(buffer)) > 0) {
					zout.write(buffer, 0, length);
				}

				zout.closeEntry();
				fin.close();

			}

			zout.finish();
			zout.close();
			DBConnector.logZipInteraction(new InteractionLog(LocalDateTime.now(), ActionType.zipping));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
