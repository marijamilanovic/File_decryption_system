package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.TxtFileController;
import controller.XlsxFileController;

public class Client {

	public static final int TCP_PORT = 9000;

	public static void main(String[] args) {
		String[] fileNames = new File("src/data").list();
		try {
			InetAddress address = InetAddress.getByName("192.168.1.177");
			Socket socket = new Socket(address, TCP_PORT);

			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String allData = "";
			for (String f : fileNames)
				if (f.contains(".txt"))
					allData += f + "/CONTENT/" + TxtFileController.readTxtFile("src/data/" + f) + "/END/";
				else
					allData += f + "/CONTENT/" + XlsxFileController.readXlsxFile("src/data/" + f) + "/END/";

			out.println(allData);
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
