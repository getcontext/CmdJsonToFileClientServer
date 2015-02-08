package app;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.RestRequest.Method;
import app.file.CsvFileWriter;
import app.request.RestGet;
import app.response.JsonResponse;

/**
 * @author andrzej.salamon@gmail.com
 *
 */
public final class Server {

	public static final String SERVICE_URL = "http://api.goeuro.com/api/v2/position/suggest/en/%s";
	public static final String CSV_FILE_FORMAT = "%s.csv";
	public static final String[] CSV_FILE_HEADER = { "_id", "name", "type",
			"geo_position.latitude", "geo_position.longitude" };
	public static final String ERROR_PARSING_JSON_MESSAGE = "data error";
	public static Method method = Method.GET;

	protected CsvFileWriter writer;

	private SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
	private RestRequest request;
	private List<String[]> csvData = new ArrayList<String[]>();
	private String[] csvRow;

	public Server() {
		final String fileName = String.format(CSV_FILE_FORMAT,
				sf.format(new Date()));

		writer = new CsvFileWriter(fileName);
	}

	public static void main(String[] args) {
		println("Cli Rest server-client\n");

		Server runner = new Server();

		try {
			runner.process(args);
		} catch (IllegalArgumentException e) {
			println("Invalid arguments specified");
		} catch (JSONException e) {
			println("Could not process json " + e.getMessage());
		} catch (IOException e) {
			println("IO error " + e.getMessage());
		}

		// println("Processing : " + runner.getRequest().getUrl() + "\n");
		System.exit(1);
	}

	public void process(String[] args) throws JSONException, IOException {
		writer.open();

		switch (method) {
		case GET:
			getAction(args);
			break;
		default:
			println("Only GET request method allowed");
			break;
		}

		println("Saved file : " + writer.getFileName());

		writer.close();
	}

	protected void getAction(String[] args) throws JSONException, IOException {
		request = createGetRequest(args);
		request.handle();

		Response response = request.getResponse();
		JSONArray json = ((JsonResponse) response).getJson();
		JSONObject row;
		int i = 0;
		csvRow = new String[CSV_FILE_HEADER.length];
		writer.writeRow(CSV_FILE_HEADER, 1);

		for (; i < json.length(); i++) {
			row = json.getJSONObject(i);
			csvRow = getCsvRow(row);
			writer.writeRow(csvRow, 1);
		}
	}

	private String[] getCsvRow(JSONObject row) {
		String[] output = new String[CSV_FILE_HEADER.length];
		JSONObject nestedRow;
		Object tmp = "";
		int i = 0;

		for (String columnName : CSV_FILE_HEADER) {
			try {
				if (columnName.contains(".")) {
					nestedRow = (JSONObject) row
							.get(columnName.split("\\.")[0]);
					tmp = nestedRow.get(columnName.split("\\.")[1]);

				} else {
					tmp = row.get(columnName);
				}
			} catch (Exception e) {
			}

			try {
				output[i] = (String) tmp;
			} catch (Exception e) {
				try {
					output[i] = Integer.toString((int) tmp);
				} catch (Exception e2) {
					try {
						output[i] = Double.toString((double) tmp);
					} catch (Exception e3) {
						output[i] = ERROR_PARSING_JSON_MESSAGE;
					}
				}
			}
			i++;
		}

		return output;
	}

	private static RestGet createGetRequest(String[] args) {
		int argc = args.length, i = 0;
		String query = null, url = null;

		if (argc < 1) {
			throw new IllegalArgumentException();
		}

		for (String arg : args) { //should be 1 size (;
			query = arg;
		}

		url = String.format(SERVICE_URL, query);

		return RestGet.create(url);
	}

	private static void println(String line) {
		System.out.println(line);
	}

	private static void println(Object line) {
		System.out.println(line);
	}

	private static void print(String line) {
		System.out.print(line);
	}
}
