package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import connector.DBConnector;
import encryption.Encryptor;
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
			String r = in.readLine();
			System.out.println(r);
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
			System.out.println(request);
		
			String[] filesWithContent =  request.split("/END/");

			for(String f:filesWithContent) {
				String[] fileNameContent = f.split("/CONTENT/");
				String decryptionKey = DBConnector.decryptionKeyForFile(fileNameContent[0]);
				String decryptedContent = Encryptor.decrypt(decryptionKey, fileNameContent[1]);
				createDecryptedFile(fileNameContent[0], decryptedContent);
				
			}
			ZipFiles.zipFiles();
			
			out.println("uspesnoooo");
			out.flush();
			// zatvori konekciju
			in.close();
			out.close();
			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}


	private void createDecryptedFile(String fileName, String decryptedContent) {
		System.out.println(decryptedContent);
		try {
		      File decryptedFile = new File(fileStorage+"\\"+fileName);
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
