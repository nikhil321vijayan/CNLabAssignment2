package httpclientserver;

import java.io.IOException;
import java.net.UnknownHostException;

public class MainClient {

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.println("arg " + i + ": " + args[i]);
		}
		HttpClientLibrary library = new HttpClientLibrary();
		
			if(args[0].toLowerCase().contentEquals("get"))
			{
				if(args[1].equals("/"))
				{
					try {
						library.getAllFiles();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else
					if(args[1].matches("/.+"))
					{	
						String filename = "";
						filename = args[1].substring(1);
						System.out.println("filename: " + filename);
						library.getFileContent(filename);
					}
			}
			else
				if(args[0].toLowerCase().contentEquals("post"))
				{
					if(args[1].matches("/.+"))
					{
						String toFilename = args[1].substring(1);
						String fromFilename = args[2];
						System.out.println("post filename :: " + toFilename);
						library.postFile(toFilename, fromFilename);
						
					}
				}
			
		}

}