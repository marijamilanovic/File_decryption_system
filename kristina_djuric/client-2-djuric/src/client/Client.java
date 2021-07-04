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

public class Client {
	
	public static final int TCP_PORT = 9000;

	public static void main(String[] args) {
		try {
			InetAddress address = InetAddress.getByName("192.168.1.177"); 
			Socket socket = new Socket(address, TCP_PORT);
			
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader fin = null;
			File folder = new File("src/data");
			File[] listOfFiles = folder.listFiles();

			String s;
			String allData = "";
			for(File f:listOfFiles)
				if(f.getName().contains(".txt")) {
					fin = new BufferedReader(new InputStreamReader(new FileInputStream("src/data/"+ f.getName())));
					while ((s = fin.readLine()) != null) {
						out.println(true);
						//out.println(s);
						allData+=s;
						
					}
					out.println(f.getName()+"/CONTENT/"+allData+"/END/");
					System.out.println(f.getName()+"/CONTENT/"+allData+"/END/");
				}
				else
					System.out.println("Excel file ");
			
			out.println(false);
			out.println();
			out.flush(); // ako ne ukljuèimo auto flush kod PrintWriter-a, moramo ovako
			
			fin.close();


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
