package goodfeeling.db;
import java.util.ArrayList;
import java.util.Calendar;

/**
* Record packaging class
* contains all fields held in database
*/
public class Record {

	//Date values, current date on start
	public Calendar date;
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
	 * Mood rate, can be any string
	 */	
	public String moodRate = "";
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

		this.date = cal;
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
