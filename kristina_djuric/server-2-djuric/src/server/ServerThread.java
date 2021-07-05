package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import connector.DBConnector;
import controller.TxtFileController;
import controller.XlsxFileController;
import controller.ZipFilesController;
import encryption.Encryptor;
import model.ActionType;
import model.InteractionLog;

public class ServerThread extends Thread {

	Socket socket;
	BufferedReader in;
	PrintWriter out;
	String zippedFileLocation = "";
	String fileStorage = "src/data";

	public ServerThread(Socket s, String zippedFileLocation) {
		this.socket = s;
		try {
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
			this.zippedFileLocation = zippedFileLocation;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Running thread...");
		try {
			String request = in.readLine();
			String[] filesWithContent = request.split("/END/");

			for (String f : filesWithContent) {
				String[] fileNameContent = f.split("/CONTENT/");
				String decryptionKey = DBConnector.decryptionKeyForFile(fileNameContent[0]);
				if (f.contains(".txt")) {
					String decryptedContent = Encryptor.decrypt(decryptionKey, fileNameContent[1]);
					TxtFileController.createAndWriteTxt(fileStorage + "\\" + fileNameContent[0], decryptedContent);
					DBConnector.logInteraction(new InteractionLog(LocalDateTime.now(), fileNameContent[0], ActionType.decryption));
				} else {
					XlsxFileController.createAndWriteTxt(fileNameContent[0], decrtyptingTable(fileNameContent, decryptionKey));
					DBConnector.logInteraction(new InteractionLog(LocalDateTime.now(), fileNameContent[0], ActionType.decryption));
				}
			}
			ZipFilesController.zipFiles(zippedFileLocation);

			out.println("success");
			out.flush();

			in.close();
			out.close();
			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private String decrtyptingTable(String[] fileNameContent, String decryptionKey) {
		String decryptedContent = "";
		String[] rowsContent = fileNameContent[1].split("/ROW/");

		for (String r : rowsContent) {
			String[] rowCellsContent = r.split("/CELL/");
			for (String c : rowCellsContent) {
				decryptedContent += Encryptor.decrypt(decryptionKey, c) + "/CELL/";
			}
			decryptedContent += "/ROW/";
		}
		return decryptedContent;
	}
}
