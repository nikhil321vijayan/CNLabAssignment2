package httpClient;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Rakshitha
 * 
 */
public class HttpHelper {

	int requestType;
	String url;
	String body = "";
	boolean isVerbose;
	ArrayList<String> headerList;
	boolean hasInline = false;
	boolean hasFile = false;

	/**
	 * @param args
	 *            command line arguments
	 * @return true if options are valid, false if invalid This method extracts the
	 *         options, their parameters and the url from the command line
	 */
	public boolean extractDetails(String[] args) {

		String arg;
		for (int i = 1; i < args.length; i++) {

			arg = args[i];
			// extract url
			if (i == args.length - 1) {
				url = args[i];
			}
			// check if verbose option is given
			if (arg.contentEquals("-v")) {
				isVerbose = true;
			}
			// check if headers are provided
			if (arg.contentEquals("-h")) {
				i++;
				if (headerList == null) {
					headerList = new ArrayList<String>();
				}
				headerList.add(args[i]);
			}
			// check if inline data is provided
			if (arg.contentEquals("-d")) {
				if (hasFile) {
					System.out.println("Inline data and file cannot be chosen at the same time.");
					return false;
				}

				hasInline = true;
				i++;
				body = args[i];
			}
			// check if file option is provided
			if (arg.contentEquals("-f")) {
				if (hasInline) {
					System.out.println("Inline data and file cannot be chosen at the same time.");
					return false;
				}
				hasFile = true;
				i++;
				String file = args[i];
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String str;
					while ((str = br.readLine()) != null) {
						body += str;
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
}
