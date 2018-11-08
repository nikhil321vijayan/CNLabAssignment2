


public class FSMainServer {

	public static void main(String[] args) {
		new Thread(new FileServer()).start();
		new Thread(new HttpServer()).start();
	}

}