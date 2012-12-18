package org.vntu.practice;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleHttpServer {
	public static void main(String args[]) throws IOException {
		ServerSocket socket = new ServerSocket(8080);
		ExecutorService executors = Executors.newCachedThreadPool();
		
		System.out.println("Server started on port: 8080, go to: http://localhost:8080/");
		while (true) {
			try {
				// wait for request
				final Socket connection = socket.accept();

				executors.execute(new Runnable() {
					@Override
					public void run() {
						try {
							processRequest(connection);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						try {
							connection.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void processRequest(Socket conn) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));
		PrintStream pout = new PrintStream(new BufferedOutputStream(
				conn.getOutputStream()));

		List<String> headers = new ArrayList<>();
		// skip all other data
		while (true) {
			String line = in.readLine();
			if (line == null || line.length() == 0) break;

			headers.add(line);
			System.out.println(">" + line);
		}

		if(headers.isEmpty()) {
			System.out.println("Empty request");
			return ;
		}
		
		String[] reqParts = headers.get(0).split(" ");
		if (reqParts[0].startsWith("GET")) {
			
			String resp = "<html>" +
					"<head><title>Test page</title></head>" +
					"<body>" +
					"<h2>Great! Server is working.</h2>" +
					"</body>" +
					"</html>";
			
			retData(pout, 200, "OK", resp);
		} else {
			retData(pout, 501, "Not Implemented",
					"<html><body><h2>501 - Method not implemented</h2></body></html>");
		}
		
		conn.close();
	}
	
	private static void retData(PrintStream out, int code, String status, String content) {
		out.print("HTTP/1.0 " + code + " " + status + "\r\n");
		out.print("Date: " + new Date() + "\r\n");
		out.print("Server: Java server/0.0.1\r\n");
		out.print("Content-Type: text/html\r\n"); // MIME
		out.print("Content-Length: " + content.getBytes().length + "\r\n");
		out.print("\r\n");
		out.print(content);
		out.close();
	}
}
