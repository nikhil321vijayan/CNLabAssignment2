
/**
 * @author Nikhil Vijayan
 *
 */
public class HttpResponse {

	String responseBody;
	String responseHeader = "";

	/**
	 * @param input
	 * 
	 *            Separately stores the header and the body of the response
	 */
	public void respond(String input) {
		if (responseBody == null) {
			if (input.isEmpty()) {
				responseBody = new String();
			} else {
				responseHeader += input + "\r\n";
			}
		} else {
			responseBody += input + "\r\n";
		}
	}

	/**
	 * Prints only body of the response
	 */
	public void printResponse() {
		System.out.println(responseBody);
	}

	/**
	 * Prints both header and body of the response(For verbose option)
	 */
	public void printVerboseResponse() {
		System.out.println(responseHeader + "\n" + responseBody);
	}

}
