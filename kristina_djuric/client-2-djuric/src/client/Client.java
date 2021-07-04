package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import filesHandler.TxtFileHandler;
import filesHandler.XlsxFileHandler;

public class Client {
	
	public static final int TCP_PORT = 9000;

	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getByName("192.168.1.177"); 
			Socket socket = new Socket(address, TCP_PORT);
			
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			File folder = new File("./src/data");
			String[] fileNames = folder.list();

			String allData = "";
			for(String f:fileNames)
				if(f.contains(".txt"))
					allData += f+"/CONTENT/"+TxtFileHandler.readTxtFile("src/data/"+ f)+"/END/";
				else
					allData += f+"/CONTENT/"+XlsxFileHandler.readXlsxFile("src/data/"+ f)+"/END/";
				
			out.println(allData);
			System.out.println(allData);
			out.println(false);
			out.println();
			out.flush();

			String response = in.readLine();
			System.out.println("[Server]: " + response);

			in.close();
			out.close();
			socket.close();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}



}
