package goodfeeling.db;
import java.util.ArrayList;
import java.util.Calendar;

/**
* Record packaging class
* contains all fields held in database
*/
public class Record {

	//Date values, current date on start
	/**
	 * Date of record being added to database (year, month and day must be provided)
	 * Current date is taken as a default
	 */	
	public Calendar date;
	/**
	 * Physical exercise rates
	 */
	public ArrayList<TestResult> physicalRates = new ArrayList<TestResult>();
	/**
	 * Mental exercise rates
	 */	
	public ArrayList<TestResult> mentalRates = new ArrayList<TestResult>();
	/**
	 * Mood exercise rates
	 */	
	public ArrayList<TestResult> moodRates = new ArrayList<TestResult>();	
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
	/**
	 * Returns last physical test rate
	 * @return last physical test rate or empty string if none test were made
	 */		
	public String getLastPhysicalRate() {
		
		if(this.physicalRates.size() > 0){
			return this.physicalRates.get(this.physicalRates.size()-1).rate;
		}else{
			return "";
		}
		
	}
	/**
	 * Returns last mood test rate
	 * @return last mood test rate or empty string if none test were made
	 */		
	public String getLastMoodRate() {
		if(this.moodRates.size() > 0){
			return this.moodRates.get(this.moodRates.size()-1).rate;
		}else{
			return "";
		}
		
	}
	/**
	 * Returns last mental test rate
	 * @return last mental test rate or empty string if none test were made
	 */		
	public String getLastMentalRate() {
		if(this.mentalRates.size() > 0){
			return this.mentalRates.get(this.mentalRates.size()-1).rate;
		}else{
			return "";
		}
		
	}	
}
