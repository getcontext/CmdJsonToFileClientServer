package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author andrzej.salamon@gmail.com
 *
 */
abstract public class AbstractFileWriter {
	public static final String DIRECTORY_SEPARATOR = getFileseparator();

	private FileWriter outputFile;
	private PrintWriter output;

	private String fileName;

	public AbstractFileWriter(String fileName) {
		setFileName(fileName);
	}

	public static enum Type {
		Csv, Xls
	}

	public static String getFileseparator() {
		return System.getProperty("file.separator");
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	final public void open() throws IOException {
		final String applicationDir = getClass().getProtectionDomain()
				.getCodeSource().getLocation().getPath().replace("%20", " ");

		final String rootDir = new File(applicationDir).getParent();

		final String filePath = rootDir + DIRECTORY_SEPARATOR + getFileName();

		outputFile = new FileWriter(filePath, true);
		output = new PrintWriter(outputFile);
	}

	final public void close() throws IOException {
		outputFile.close();
		output.close();
	}

	final public PrintWriter getWriter() {
		return this.output;
	}

	abstract public void write() throws IOException;
}
