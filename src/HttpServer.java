import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpServer implements Runnable {
	ServerSocket serverSocket;
	Socket clientSocket;

	String string = "";
	String[] args;
	// BufferedReader inputReader;
	PrintWriter outWriter;
	String httpRequest = "";

	@Override
	public void run() {
		Date date = new Date();
		try {

			serverSocket = new ServerSocket(9000);
			System.out.println("Listening to port 9000...\r\n\r\n");
			String response = "";
			clientSocket = serverSocket.accept();
			
			BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			while ((string = inputReader.readLine()) != null) {
				httpRequest += string + "\r\n";
			}

			//System.out.println("Request fom http client: \n" + httpRequest);
			if (httpRequest != null) {
				if (httpRequest.contains("GET")) {
					String[] lines = httpRequest.split("\r\n");
					args = lines[0].split(" ");
					String params = args[1].substring((args[1].indexOf('?') + 1), (args[1].length()));
					List<String> keyList = new ArrayList<String>();
					List<String> valueList = new ArrayList<String>();
					String[] parameterList = params.split("&");
					for (String par : parameterList) {
						keyList.add(par.split("=")[0]);
						valueList.add(par.split("=")[1]);
					}
					response += "HTTP/1.1 200 OK\r\n" + "Connection: close\r\n" + "Server: myhttpserver\r\n" + "Date: "
							+ date.toString() + "\r\n\r\n" + "{\r\n" + " \"args\": {\r\n";
					for (int i = 0; i < parameterList.length; i++) {
						response += "\"" + keyList.get(i) + "\": \"" + valueList.get(i) + "\"\r\n";
					}
					response += " },\r\n" + " \"headers\": {\r\n" + "   \"Connection\": \"close\",\r\n"
							+ "   \"Host\": \"localhost:8080\"\r\n" + " },\r\n" + "}\r\n";
					System.out.println("response \n" + response);
										
				}
				else
					if(httpRequest.contains("POST")) {
						String[] lines = httpRequest.split("\r\n\r\n");
						String body = lines[lines.length-1];
						//System.out.println("post body :: " + body);
						response += "HTTP/1.1 200 OK\r\n" + 
								"Connection: close\r\n" + 
								"Server: myhttpserver\r\n" + 
								date.toString() + "\r\n\r\n" + 
								"{\r\n" + 
								" \"args\": {},\r\n" + 
								" \"data\": \"" + body + "\",\r\n" + 
										" \"files\": {},\r\n" + 
										" \"form\": {},\r\n" + 
										" \"headers\": {\r\n" + 
										"   \"Connection\": \"close\",\r\n" + 
										"   \"Content-Length\": \"" + body.length() +"\",\r\n" + 
										"   \"Content-Type\": \"application/json\",\r\n" + 
										"   \"Host\": \"httpbin.org\"\r\n" + 
										" },\r\n" + 
										" \"json\": null,\r\n" + 
										" \"origin\": \"66.131.239.56\",\r\n" + 
										" \"url\": \"http://httpbin.org/post\"\r\n" + 
										"}";
						System.out.println(response); 
					}

			}

		} catch (

		IOException e) {
			e.printStackTrace();
		} finally {
			try {
				clientSocket.close();
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
