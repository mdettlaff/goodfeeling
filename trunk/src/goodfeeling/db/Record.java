package goodfeeling.db;

import java.util.ArrayList;
import java.util.Calendar;

/**
* Record packaging class
* contains all fields held in database
*/
public class Record {

	//Date values, current date on start
	public int day = 0; //np 23 lub 5 etc
	public int month = 0; //np 11 lub 6 etc
	public int year = 0; // np 2010
	
	//other data
	/**
	 * Physical exercise rate, can be any string
	 */
	public String physicalRate = "";
	/**
	 * Mental exercise rate, can be any string
	 */	
	public String mentalRate = "";

	/**
	 * Eaten food array list
	 */		
	public ArrayList<RecordFood> eatenFood = new ArrayList<RecordFood>();
	/**
	 * Activites done array list
	 */		
	public ArrayList<RecordActivity> activitiesDone = new ArrayList<RecordActivity>();
	
	public Record(){
		//Set current date
		Calendar cal = Calendar.getInstance();
		this.day = cal.get(Calendar.DATE);
		this.month = cal.get(Calendar.MONTH) + 1;
		this.year = cal.get(Calendar.YEAR);
	}
	/**
	 * Adds food to food array list
	 * @param food RecordFood to add
	 */		
	public void addFood(RecordFood food){
		this.eatenFood.add(food);
		
	}
	/**
	 * Adds activity to activities array list
	 * @param activity RecordActivity to add
	 */			
	public void addActivity(RecordActivity activity){
		this.activitiesDone.add(activity);
		
	}	
}
