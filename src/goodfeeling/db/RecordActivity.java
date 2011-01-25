package goodfeeling.db;

/**
* Activity packaging class, represents a single activity done
* part of data contained in Record class
*/
public class RecordActivity {

	/**
	 * Name of done activity taken form database
	 */	
	public String name = "";
	
	/**
	 * Hour of the start of the activity
	 */	
	public int startHour = 0;
	
	/**
	 * Duration of done activity, in minutes
	 */		
	public int duration = 0;
	
	/**
	 * Static values declared in gui, such as: High / Low / Average
	 */
	public String intensivity;	
	
}
