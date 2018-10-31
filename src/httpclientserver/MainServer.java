package httpclientserver;


public class MainServer {

	public static void main(String[] args) {
		new Thread(new Server()).start();
	}

}