package httpclientserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		String input = "";
		String[] args;
		File folder = new File("C:\\Users\\Nikhil Vijayan\\CNLabDemo\\CNLabAssignment2\\DefaultDirectory");
		try {
			serverSocket = new ServerSocket(9002);
			System.out.println("Listening to port 9002...");
			Socket clientSocket = serverSocket.accept();

			BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			String recievedRequest = "";
			while((input = inputReader.readLine()) != null)
			{
				recievedRequest += input + "\r\n";
			}
			System.out.println("The recieved request is :: \r\n" + recievedRequest);
			if (recievedRequest != null) {
				if (recievedRequest.contains("GET")) {
					String[] lines = recievedRequest.split("\r\n");
					args = lines[0].split(" ");
					// System.out.println("args[1]:::" + args[1]);
					if (args[1].equals("/")) {
						File[] listOfFiles = folder.listFiles();

						for (int i = 0; i < listOfFiles.length; i++) {
							if (listOfFiles[i].isFile()) {
								System.out.println("File " + listOfFiles[i].getName());
							} else if (listOfFiles[i].isDirectory()) {
								System.out.println("Directory " + listOfFiles[i].getName());
							}
						}
					} else if (args[1].matches("/.+")) {
						String fileToFind = args[1].substring(1);
						System.out.println("\nFileToFind: \n" + fileToFind);
						findFile(fileToFind, folder);

					}
				}
				
				
				else if (recievedRequest.contains("POST")) {
					
					String[] lines = recievedRequest.split("\r\n");
					
					args = lines[0].split(" ");
					if (args[1].startsWith("/")) {
						System.out.println("args[1]" + args[1]);
						if (args[1].matches("/.+")) {
							System.out.println("Matched");
							String fileToFind = args[1].substring(1);
							if (checkFileExists(fileToFind, folder)) {
								// delete file and replace with new
								File existingFile = new File(folder + "\\" + fileToFind);
								// existingFile.delete();
								// File newFile = new File(folder+"/"+fileToFind);

								/*String requestString = "";
								while (inputReader.readLine() != null) {
									requestString += inputReader.readLine();
								}*/
								String body = extractBody(recievedRequest);
								System.out.println("Body :::: \r\n" + body);
								try {
									FileWriter fw = new FileWriter(existingFile, false);
									fw.write(body);
									fw.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								System.out.println("The file has been overwritten & content has been written to file...");
							} else {
								/*String requestString = "";
								while (inputReader.readLine() != null) {
									requestString += inputReader.readLine();
								}*/
								String body = extractBody(recievedRequest);
								System.out.println("Body :::: \r\n" + body);
								try {
									File newFile = new File(folder + "\\" + fileToFind);
									newFile.createNewFile();
									FileWriter fw = new FileWriter(newFile, false);
									fw.write(body);
									fw.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								System.out.println("The content has been written to file...");
							}
						}
					}
				}
				else
				{
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

	private void findFile(String fileToFind, File folder) {

		File[] listOfFiles = folder.listFiles();

		if (listOfFiles != null)
			for (File file1 : listOfFiles) {
				if (file1.isDirectory()) {
					// findFile(fileToFind, file1);
					continue;
				} else if (fileToFind.equalsIgnoreCase(file1.getName())) {
					// System.out.println("File found!!");
					try (BufferedReader br = new BufferedReader(new FileReader(
							"C:\\Users\\Rakshitha\\CN\\LabAssignment2\\src\\httpclientserver\\" + fileToFind))) {
						String line = null;
						while ((line = br.readLine()) != null) {
							System.out.println(line);
						}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// else System.out.println("File not found");
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
					System.out.println("File found!!");
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