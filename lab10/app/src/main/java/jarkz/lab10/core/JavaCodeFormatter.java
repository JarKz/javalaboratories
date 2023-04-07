package jarkz.lab10.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaCodeFormatter implements Runnable {

	private final String inputFile;
	private final String outputFile;
	private String logFile = "log.txt";

	public JavaCodeFormatter(String inputFile, String outputFile) {
		if (inputFile == null)
			throw new NullPointerException("inputFile cannot be null");
		if (inputFile.isBlank())
			throw new IllegalArgumentException("inputFile cannot be blank");
		if (outputFile == null)
			throw new NullPointerException("outputFile cannot be null");
		if (outputFile.isBlank())
			throw new IllegalArgumentException("outputFile cannot be blank");

		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	private String formatLine(String line) {
		Matcher matcher = Pattern.compile("\\b\\w{2,}\\b").matcher(line);
		StringBuilder builder = new StringBuilder(line);
		while (matcher.find()) {
			String word = matcher.group();
			builder.replace(matcher.start(), matcher.end(), word.toUpperCase());
		}
		return builder.toString();
	}

	public void setLogFile(String filename){
		if (filename == null){
			throw new NullPointerException("filename cannot be null");
		}
		logFile = filename;
	}

	private void createFileIfDontExist(File file){
		try {
			if (!file.exists())
				file.createNewFile();
		} catch (IOException e) {
			writeLogToFile(e);
			Thread.currentThread().interrupt();
		}
	}

	private void writeLogToFile(Exception exception){
		File logFile = new File(this.logFile);
		if (!logFile.exists()){
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				throw new FormatRuntimeException(e.getMessage());
			}
		}
		try (PrintWriter writer = new PrintWriter(logFile)){
			exception.printStackTrace(writer);
		} catch (FileNotFoundException e){
			throw new FormatRuntimeException(e.getMessage());
		}
	}

	@Override
	public void run() {
		File iFile = new File(inputFile);
		File oFile = new File(outputFile);
		oFile.getParentFile().mkdirs();

		createFileIfDontExist(oFile);

		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(oFile)));
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(iFile)))) {
			while (reader.ready()) {
				String line = reader.readLine();
				writer.write(formatLine(line));
				writer.newLine();
			}
		} catch (FileNotFoundException e) {
			writeLogToFile(e);
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			writeLogToFile(e);
			Thread.currentThread().interrupt();
		}
	}
}
