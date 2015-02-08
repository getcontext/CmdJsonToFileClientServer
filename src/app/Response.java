package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

public interface Response {
	public static enum Type {
		JSON, XML, HTML
	}

	public String getData();

	public void setData(String data);

	public Type getType();

	public void send(String url) throws IOException, JSONException;

}
