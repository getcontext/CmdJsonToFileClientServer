package app.request;

import java.io.IOException;

import org.json.JSONException;

import app.Response;
import app.RestRequest;
import app.response.JsonResponse;

/**
 * @author andy
 *
 */
public class RestGet implements RestRequest {

	public static RestGet create(String url) {
		RestGet request = new RestGet();
		request.setUrl(url);
		return request;
	}

	Method method;
	String url;
	Response response;

	private RestGet() {
		
	}
	
	@Override
	public Method getMethod() {
		return Method.GET;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Type getType() {
		return Type.REST;
	}

	@Override
	public void handle() throws IOException, JSONException {		
		response = new JsonResponse();
		response.send(getUrl());
	}

	@Override
	public Response getResponse() {
		return response;
	}

	@Override
	public String getUrl() {
		return url;
	}
}
