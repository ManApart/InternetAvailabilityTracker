package rak.netAvailability;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AvailabilityChecker {
	private static final int CHECK_INTERVAL_DEFAULT = 60000;
	
	private int checkInterval = CHECK_INTERVAL_DEFAULT;
	private boolean previousStatus = false;
	private LogWriter logWriter;
	
	public AvailabilityChecker(){
		logWriter = new TextFileLogWriter();
	}
	
	public void startChecking() {
		writeStartupMessage();
		try {
			loopCheck();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void writeStartupMessage() {
		String startupMessage = getCurrentDateTime() + " Starting internet checker.";
		System.out.println(startupMessage);
		logWriter.writeLog(startupMessage);
	}
	
	public void writeShutdownMessage(){
		String shutDownMessage = getCurrentDateTime() + " Stopping internet checker.";
		System.out.println(shutDownMessage);
		logWriter.writeLog(shutDownMessage);
	}

	private void loopCheck() throws InterruptedException{
		checkInternet();
		Thread.sleep(checkInterval);
		loopCheck();
	}

	private void checkInternet() {
		boolean isAvailable = pingTest();		
		logCheckResult(isAvailable);
	}

	private boolean pingTest() {
		String url = "https://www.google.com";
		int timeout = 2000;
		return pingURL(url, timeout);
	}

	
	private boolean pingURL(String url, int timeout) {
	    url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.

	    try {
	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	        connection.setConnectTimeout(timeout);
	        connection.setReadTimeout(timeout);
	        connection.setRequestMethod("HEAD");
	        int responseCode = connection.getResponseCode();
	        return (200 <= responseCode && responseCode <= 399);
	    } catch (IOException exception) {
	        return false;
	    }
	}
	

	private void logCheckResult(boolean isAvailable) {
		String changeMessage = createStatusMessage(isAvailable);
		System.out.println(changeMessage);
	
		if (isAvailable != previousStatus){
			previousStatus = isAvailable;
			logWriter.writeLog(changeMessage);
		}
	}

	private String createStatusMessage(boolean isAvailable) {
		String dateString = getCurrentDateTime();
		String status = isAvailable ? "available" : "not available";
		String changeMessage = dateString + " The internet is " + status + ".";
		return changeMessage;
	}

	private String getCurrentDateTime() {
		String dateString = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(new Date());
		return dateString;
	}
	
	
	public int getCheckInterval() {
		return checkInterval;
	}

	public void setCheckInterval(int checkInterval) {
		this.checkInterval = checkInterval;
	}

}
