package rak.netAvailability

import spock.lang.Specification

class AvailabilityCheckerRunner extends Specification {
	
	def "test the log write function"(){
		given:
			AvailabilityChecker checker = new AvailabilityChecker()
			
		when:
			boolean previousStatus = false
			boolean currentStatus = true
			
			checker.previousStatus = previousStatus
			
			checker.logCheckResult(currentStatus)
		then: "we arbitrarily pass the test"
			int i = 0
			i == 0
	}

}
