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
	 * Hour in hh format, such as: 12,21,7
	 */
	public String timeConsumed;
}
