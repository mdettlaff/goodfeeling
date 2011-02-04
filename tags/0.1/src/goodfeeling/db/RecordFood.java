package goodfeeling.db;

/**
* Food eaten packaging class
* part of data contained in Record class
*/
public class RecordFood {
	/**
	 * Food name taken form database
	 */	
	public String name = "";
	
	/**
	 * Amount of food eaten
	 */	
	public float amount = 0;
	
	/**
	 * Unit type of eaten food, such as: kg, g, l
	 */		
	public String unit = "";
	
	/**
	 * Static values declared in gui, such as: before 11 / between 11-17 / after 17
	 */
	public String timeConsumed;
}
