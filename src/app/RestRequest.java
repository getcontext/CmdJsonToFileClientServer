package app;

import java.io.IOException;

import org.json.JSONException;

public interface RestRequest extends Request {
	public static enum Method {
		GET, POST, PUT, DELETE
	}

	public Method getMethod();
	public void setUrl(String url);
	public String getUrl();
	public void handle() throws IOException, JSONException; 
	
}
