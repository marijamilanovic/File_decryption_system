package server;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	public static final int TCP_PORT = 9000;
	
	public static void main(String[] args) {
		new Server();
	}
	
	public Server() {
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket(TCP_PORT);
			String zippedFileLocation = "src/zippedFile";
			System.out.println("Server running...");
			
			while (true) {
				Socket sock = ss.accept();
				ServerThread serverThread = new ServerThread(sock,zippedFileLocation);
				serverThread.start();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
