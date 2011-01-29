package goodfeeling.db;
/**
* Test result class for all results of test
* such as mental, mood and physical tests
*/
public class TestResult {
	/**
	 * Hour of result being made
	 */	
	public int hour = -1;
	/**
	 * Rate achived in the test
	 */		
	public String rate = "";
	
	public TestResult(){
		
	}
	public TestResult(int hour, String rate){
		this.hour = hour;
		this.rate = rate;
	}
}
