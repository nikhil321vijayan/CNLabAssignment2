package httpClient;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nikhil Vijayan
 *
 */
public class HttpLibrary {

	String uri;
	String requestPath;
	String requestHost;
	String requestHeader = "";
	String allHeaders = "";
	String requestMessage = "";

	/**
	 * @param url
	 * @param headerList
	 * @param isVerbose
	 * @throws UnknownHostException
	 * @throws IOException
	 * @author Nikhil Vijayan
	 * 
	 *         This method is used to compose a get request by adding path, host and
	 *         headers
	 */
	public void get(String url, ArrayList<String> headerList, boolean isVerbose)
			throws UnknownHostException, IOException {
		HttpRequest request = new HttpRequest();
		if (url.startsWith("http://")) {
			uri = url.substring(7);
		}
		requestPath = getPath(uri);
		requestHost = getHost(uri, requestPath);
		requestHeader = "GET" + " " + requestPath +  " HTTP/1.0\r\n" + "Host: " + requestHost + "\r\n";
		// Concatenate all headers to a string
		if (headerList != null) {
			for (int i = 0; i < headerList.size(); i++) {
				allHeaders += headerList.get(i);
			}
			allHeaders += "\r\n";
		}
		requestMessage += requestHeader + allHeaders + "\r\n";
		request.sendRequest(requestMessage, requestHost, isVerbose);
	}

	/**
	 * @param url
	 * @param headerList
	 * @param body
	 * @param isVerbose
	 * @throws UnknownHostException
	 * @throws IOException
	 * @author Rakshitha
	 * 
	 *         This method is used to compose a post request by adding path, host,
	 *         headers and data if any
	 */
	public void post(String url, ArrayList<String> headerList, String body, boolean isVerbose)
			throws UnknownHostException, IOException {
		HttpRequest request = new HttpRequest();
		if (url.startsWith("http://")) {
			uri = url.substring(7);
		}
		requestPath = getPath(uri);
		requestHost = getHost(uri, requestPath);
		requestHeader = "POST" + " " + requestPath + " HTTP/1.0\r\n" + "Host: " + requestHost + "\r\n";
		// Concatenate all headers to a string
		if (headerList != null) {
			for (int i = 0; i < headerList.size(); i++) {
				allHeaders += headerList.get(i);
			}
		}
		requestMessage += requestHeader + allHeaders + "\r\n" + "Content-Length: " + body.length() + "\r\n\r\n" + body
				+ "\r\n";
		request.sendRequest(requestMessage, requestHost, isVerbose);
	}

	/**
	 * @param uri
	 * @return path
	 * 
	 *         This method extracts the path from the URI using regex pattern
	 *         matching
	 */
	public static String getPath(String uri) {
		
		/*String par = "";
		String[] params = uri.split("&");
		for(int i=1; i<params.length;i++) {
			par += "&" + params[i] ;
			
		}*/
		String path;
		Pattern regexPattern = Pattern.compile("[.][^/]+(.*)");
		Matcher matcher = regexPattern.matcher(uri);
		matcher.find();
		path = matcher.group(1); //+ par;
		System.out.println("path: " + path);
		return path;
	}

	/**
	 * @param uri
	 * @param path
	 * @return host name
	 * 
	 *         This method extracts the host name from the URI
	 */
	public static String getHost(String uri, String path) {
		return (uri.substring(0, uri.length() - path.length()));
	}
}
