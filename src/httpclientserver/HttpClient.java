package httpclientserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class HttpClient implements Runnable {

	String requestMessage = "";

	public HttpClient(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	@Override
	public void run() {

		Socket socket = null;
		try {
			Thread.sleep(3000);
			socket = new Socket("localhost",9002);
			PrintWriter outWriter = new PrintWriter(socket.getOutputStream(),true);
			outWriter.print(requestMessage);
			//System.out.println("writing request: " + requestMessage);
			outWriter.flush();
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}

