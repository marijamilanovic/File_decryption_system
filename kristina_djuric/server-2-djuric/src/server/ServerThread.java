package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import connector.DBConnector;
import encryption.Encryptor;
import filesHandler.TxtFileHandler;
import filesHandler.XlsxFileHandler;
import zippedFile.ZipFiles;

public class ServerThread extends Thread {
	
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	String zippedFileLocation= "";
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
			String[] filesWithContent =  request.split("/END/");

			for(String f:filesWithContent) {
				String[] fileNameContent = f.split("/CONTENT/");
				String decryptionKey = DBConnector.decryptionKeyForFile(fileNameContent[0]);
				if(f.contains(".txt")) {
					String decryptedContent = Encryptor.decrypt(decryptionKey, fileNameContent[1]);
					TxtFileHandler.createDecryptedTxtFile(fileStorage+"\\"+fileNameContent[0], decryptedContent);
				}
				else {
					String decryptedContent = "";
					String[] rowsContent = fileNameContent[1].split("/ROW/");
					
					for(String r:rowsContent) {
						String[] rowCellsContent = r.split("/CELL/");
						for(String c:rowCellsContent) {
							decryptedContent += Encryptor.decrypt(decryptionKey, c)+"/CELL/";
						}
						decryptedContent += "/ROW/";
					}
					XlsxFileHandler.createDecryptedXlsxFile(fileNameContent[0], decryptedContent);
				}
			}
			ZipFiles.zipFiles();
			
			out.println("success");
			out.flush();
		
			in.close();
			out.close();
			socket.close();
		} catch (Exception ex) {
			out.println("failure");
			ex.printStackTrace();
		}
	}
}
