

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server implements Runnable {

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		String input = "";
		String[] args;
		File folder = new File("C:\\Users\\Nikhil Vijayan\\CNLabDemo\\CNLabAssignment2\\DefaultDirectory");
		Date date = new Date(); 
		
		try {
			serverSocket = new ServerSocket(9002);
			System.out.println("Listening to port 9002...\r\n\r\n");
			Socket clientSocket = serverSocket.accept();

			BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String recievedRequest = "";
			while ((input = inputReader.readLine()) != null) {
				recievedRequest += input + "\r\n";
			}
			//System.out.println("The recieved request is :: \r\n" + recievedRequest);
			if (recievedRequest != null) {
				if (recievedRequest.contains("GET")) {
					String[] lines = recievedRequest.split("\r\n");
					args = lines[0].split(" ");
					if (args[1].equals("/")) {
						File[] listOfFiles = folder.listFiles();
						String fileNames = "";
						for (int i = 0; i < listOfFiles.length; i++) {
								fileNames += listOfFiles[i].getName();
						}
						System.out.println("HTTP/1.1 200 OK\r\n" + 
								"Date: " + date.toString() +"\r\n" + 
								"Server: localhost:9002\r\n" + 
								"Content-Length: " + fileNames.length() + "\r\n" + 
								"Connection: Closed" + "\r\n\r\n");
						for (int i = 0; i < listOfFiles.length; i++) {
							if (listOfFiles[i].isFile()) {
								System.out.println("File " + listOfFiles[i].getName());
							} else if (listOfFiles[i].isDirectory()) {
								System.out.println("Directory " + listOfFiles[i].getName());
							}
						}
					} 
					
					else if (args[1].matches("/.+")) {
						String fileToFind = args[1].substring(1);
						String line = "", body = "";
						if (checkFileExists(fileToFind, folder)) {
							
							try (BufferedReader br = new BufferedReader(new FileReader(folder + "\\" + fileToFind))) {
								while ((line = br.readLine()) != null) {
									body += line + "\r\n";
								}
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							System.out.println("HTTP/1.1 200 OK\r\n" + 
									"Date: " + date.toString() +"\r\n" + 
									"Server: localhost:9002\r\n" + 
									"Content-Length: " + body.length() + "\r\n" + 
									"Connection: Closed" + "\r\n\r\n");
							System.out.println(body);
						} else {
							System.out.println("HTTP/1.1 404 File not found\r\n" + 
									"Date: " + date.toString() +"\r\n" + 
									"Server: localhost:9002\r\n" + 
									"Connection: Closed" + "\r\n\r\n");
						}
					}
				}

				else if (recievedRequest.contains("POST")) {
					String[] lines = recievedRequest.split("\r\n");
					args = lines[0].split(" ");
					if (args[1].startsWith("/")) {
						if (args[1].matches("/.+")) {
							String fileToFind = args[1].substring(1);
							if (checkFileExists(fileToFind, folder)) {
								File existingFile = new File(folder + "\\" + fileToFind);
								String body = extractBody(recievedRequest);
								try {
									FileWriter fw = new FileWriter(existingFile, false);
									fw.write(body);
									fw.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								System.out.println("HTTP/1.1 200 OK\r\n" + 
										"Connection: keep-alive\r\n" + 
										"Date: " + date.toString() +"\r\n" + 
										"Server: localhost:9002\r\n" + 
										"Content-Length: " + body.length() + "\r\n\r\n" + 
										body);
							} else {
								String body = extractBody(recievedRequest);
								try {
									File newFile = new File(folder + "\\" + fileToFind);
									newFile.createNewFile();
									FileWriter fw = new FileWriter(newFile, false);
									fw.write(body);
									fw.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								System.out.println("HTTP/1.1 200 OK\r\n" + 
										"Connection: keep-alive\r\n" + 
										"Date: " + date.toString() +"\r\n" + 
										"Server: localhost:9002\r\n" + 
										"Content-Length: " + body.length() + "\r\n" + 
										body +  "\r\n\r\n");
							}
						}
					}
				} else {
					System.out.println("Printing first line :::  " + input);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean checkFileExists(String fileToFind, File folder) {
		boolean fileExists = false;
		File[] listOfFiles = folder.listFiles();
		if (listOfFiles != null)
			for (File file1 : listOfFiles) {
				if (file1.isDirectory()) {
					continue;
				} else if (fileToFind.equalsIgnoreCase(file1.getName())) {
					fileExists = true;
					break;
				}
				// else System.out.println("File not found");
			}
		if (fileExists) {
			return true;
		} else {
			return false;
		}
	}

	private String extractBody(String requestString) {
		String[] requestLines = requestString.split("\r\n\r\n");
		return requestLines[requestLines.length - 1];

	}

}