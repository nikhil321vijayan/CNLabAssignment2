package httpClient;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @author Rakshitha
 * 
 */
public class Client {

	public static void main(String[] args) {

		if (args[0].contentEquals("httpc")) {

			for (int i = 0; i < args.length; i++) {
				System.out.println("arg " + i + ": " + args[i]);
			}
			System.out.println("\n");
			HttpHelper helper = new HttpHelper();

			if (helper.extractDetails(args)) {
				HttpLibrary library = new HttpLibrary();
				// check if its a get request
				if (args[1].contentEquals("get")) {
					try {
						library.get(helper.url, helper.headerList, helper.isVerbose);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// check if its a post request
				else if (args[1].contentEquals("post")) {
					try {
						library.post(helper.url, helper.headerList, helper.body, helper.isVerbose);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// check if its help on get or post request
				else if (args[1].contentEquals("help") && args.length > 1) {
					if (args[2].contentEquals("get")) {
						System.out.println("usage: httpc get [-v] [-h key:value] URL\r\n"
								+ "Get executes a HTTP GET request for a given URL.\r\n"
								+ " -v Prints the detail of the response such as protocol, status, and headers.\r\n"
								+ " -h key:value Associates headers to HTTP Request with the format 'key:value'.");
					} else

					if (args[2].contentEquals("post")) {
						System.out.println("usage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\r\n"
								+ "Post executes a HTTP POST request for a given URL with inline data or from\r\n"
								+ "file.\r\n"
								+ " -v Prints the detail of the response such as protocol, status, and headers.\r\n"
								+ " -h key:value Associates headers to HTTP Request with the format 'key:value'.\r\n"
								+ " -d string Associates an inline data to the body HTTP POST request.\r\n"
								+ " -f file Associates the content of a file to the body HTTP POST request.\r\n"
								+ "Either [-d] or [-f] can be used but not both.");
					}
				}
				if (args[1].contentEquals("help") && args.length == 1) {
					System.out.println("httpc is a curl-like application but supports HTTP protocol only.\r\n"
							+ "Usage:\r\n" + " httpc command [arguments]\r\n" + "The commands are:\r\n"
							+ " get executes a HTTP GET request and prints the response.\r\n"
							+ " post executes a HTTP POST request and prints the response.\r\n"
							+ " help prints this screen.\r\n"
							+ "Use \"Httpc help [command]\" for more information about a command.");
				}
			} else {
				System.out.println("Invalid input. Use Httpc help for information.");
			}
		}
		if(args[0].contentEquals("httpfs"))
		{
			if(args[1].contentEquals("help"))
			{
				System.out.println("httpfs is a simple file server.\r\n" + 
						"usage: httpfs [-v] [-p PORT] [-d PATH-TO-DIR]\r\n" + 
						"-v Prints debugging messages.\r\n" + 
						"-p Specifies the port number that the server will listen and serve at.\r\n" + 
						"Default is 8080.\r\n" + 
						"-d Specifies the directory that the server will use to read/write requested files. "
						+ "Default is the current directory when launching the application.");
			}
			if(args[2].contentEquals("GET"))
			{
				if(args[3].contentEquals("/"))
				{
					System.out.println("Display repository");
				}
			}
		}
	}
}
