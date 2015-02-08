package app;

import java.io.IOException;

import org.json.JSONException;

public interface Request {
	public static enum Type {
		REST, SOAP, RPC
	}

	public Type getType();
	public void handle() throws IOException, JSONException;
	public Response getResponse();

}
