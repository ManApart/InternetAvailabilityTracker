package rak.netAvailability;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileLogWriter implements LogWriter {
	private static final String AVAILABILITY_LOG = "AvailabilityLog.txt";
	private File logFile;
	
	public TextFileLogWriter(){
		try {
			createFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void createFile() throws IOException{
		logFile = new File(AVAILABILITY_LOG);
		logFile.createNewFile();
	}
	
	@Override
	public void writeLog(String message) {
		try {
			//TODO - this is replaceing the file contents instead of appending to them
			BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));
			bw.write("\n" + message);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
