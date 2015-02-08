package app.file;

import java.io.IOException;

import app.AbstractFileWriter;

public class CsvFileWriter extends AbstractFileWriter {

	public static final String DELIMETER = ";";

	public CsvFileWriter(String fileName) {
		super(fileName);		
	}

	@Override
	public void write() throws IOException {

	}

	public void writeRow(String[] row, int lenght) throws IOException {
		String line = "";

		for (String key : row) {
			line += key;
			line += DELIMETER;
		}

		line = line.substring(0, line.length() - lenght);
		
		getWriter().println(line);
	}

}
