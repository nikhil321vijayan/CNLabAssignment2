package httpClient;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Rakshitha
 */
public class HttpRequest {

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
	public void sendRequest(String requestMessage, String requestHost, boolean isVerbose)
			throws UnknownHostException, IOException {

		Socket socket = new Socket(requestHost, 80);
		OutputStream output = socket.getOutputStream();
		PrintWriter pw = new PrintWriter(output, false);
		pw.print(requestMessage);
		pw.flush();
		InputStream inputStream = socket.getInputStream();
		InputStreamReader streamreader = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(streamreader);
		String input;
		HttpResponse response = new HttpResponse();
		while ((input = br.readLine()) != null) {
			response.respond(input);
		}
		// if -v is specified print the verbose version of the response
		if (isVerbose) {
			response.printVerboseResponse();
		} else {
			response.printResponse();
		}
		socket.close();
	}

}
