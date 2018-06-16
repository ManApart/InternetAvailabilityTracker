package rak.netAvailability

import spock.lang.Specification


class AvailabilityCheckerSpec extends Specification {

	def "logCheckResult writes to the file only when the status changes"(){
		given:
			AvailabilityChecker checker = new AvailabilityChecker()
			
			LogWriter logWriter = Mock()
			checker.logWriter = logWriter
			
		when:
			checker.previousStatus = previousStatus
			checker.logCheckResult(currentStatus)
		then:
			expectedCallCount * logWriter.writeLog(_)
		where:
			previousStatus	|	currentStatus	|	expectedCallCount
			false			|	false			|	0
			true			|	true			|	0
			true			|	false			|	1
			false			|	true			|	1
	}

}
