package app.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.Response;
import app.RestRequest;

/**
 * @author andy
 *
 */
public final class JsonResponse implements Response {
	String data;
	JSONArray json;

	@Override
	public String getData() {
		return data;
	}

	@Override
	public void setData(String data) {
		this.data = data;
		// json = re
	}

	@Override
	public Type getType() {
		return Type.JSON;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static JSONArray read(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		JSONArray json = null;
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					Charset.forName("UTF-8")));

			String out = readAll(rd);
			json = new JSONArray(out);
		} finally {
			is.close();
		}
		return json;
	}

	@Override
	public void send(String url) throws IOException, JSONException {
		json = read(url);
	}

	public JSONArray getJson() {
		return json;
	}
}
