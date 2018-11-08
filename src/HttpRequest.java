import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Rakshitha
 */
public class HttpRequest implements Runnable {

	private String requestMessage;
	private String requestHost;
	private boolean isVerbose;
	Socket clientSocket;
	PrintWriter outWriter;
	BufferedReader br;

	public HttpRequest(String requestMessage, String requestHost, boolean isVerbose) {
		this.requestMessage = requestMessage;
		this.requestHost = requestHost;
		this.isVerbose = isVerbose;
	}

	/**
	 * @param requestMessage
	 * @param requestHost
	 * @param isVerbose
	 * @throws UnknownHostException
	 * @throws IOException
	 * 
	 *             In this method, a socket is created and the request is sent over
	 *             the outputStream and the response is received over the
	 *             inputStream
	 */

	String in;

	@Override
	public void run() {

		try {
			System.out.println("request message @HttpRequest :: " + requestMessage);
			clientSocket = new Socket("localhost", 9000);
			PrintWriter outWriter = new PrintWriter(clientSocket.getOutputStream(), true);
			outWriter.print(requestMessage);
			outWriter.flush();


			// if -v is specified print the verbose version of the response
			/*
			 * if (isVerbose) { response.printVerboseResponse(); } else {
			 * response.printResponse(); }
			 */

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
