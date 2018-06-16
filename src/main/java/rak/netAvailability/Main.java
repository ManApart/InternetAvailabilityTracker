package rak.netAvailability;

public class Main {
	private static AvailabilityChecker availabilityChecker;
	
	public static void main(String[] args){
		availabilityChecker = new AvailabilityChecker(); 
		
		//Test checking once a second
//		availabilityChecker.setCheckInterval(1000);
		availabilityChecker.startChecking();
		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            availabilityChecker.writeShutdownMessage();
	        }
	    }, "Shutdown-thread"));
	}
	
	
	
}
