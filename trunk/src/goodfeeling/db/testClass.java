import java.util.ArrayList;


public class testClass {
/*
	public static void main(String[] args) {
		
		
		//RECORDS - ADDING / UPDATING / DELETING / READING
		///////////////////////////////////////////////////
		//New record, if fields: day, month and year are not provided, current date is taken
		Record r1 = new Record();
		r1.physicalRate = "Poor";
			//Create food for the record
			RecordFood food1 = new RecordFood();
			food1.name = "Potato";
			food1.amount = 3;
			food1.unit = "number";
			food1.timeConsumed = "before 11";
			
			RecordFood food2 = new RecordFood();
			food2.name = "Tomato";
			food2.amount = 2;
			food2.unit = "kg";
			food2.timeConsumed = "after 17";	
			
		//add food1, food2 to the record
		r1.addFood(food1);	
		r1.addFood(food2);
		
			//Create activity for the record
			RecordActivity act1 = new RecordActivity();
			act1.name = "Swimming";
			act1.startHour = 12;
			act1.duration = 63;
			act1.intensivity = "High";	
		
			r1.addActivity(act1);	
			
		//Other record, date provided
		Record r2 = new Record();
		r2.day = 10;
		r2.month = 1;
		r2.year = 2011;
		r2.physicalRate ="12/100";
		r2.addFood(food1);
		r2.addActivity(act1);
		//Init dbHandler
		DbHandler dbHandler= new DbHandler();

		try {
			//update or add both records
			dbHandler.addOrUpdateRecord(r1);
			dbHandler.addOrUpdateRecord(r2);
			
			//load record from database from: 2010 1 10 //rrrr mm dd
			Record r3 = dbHandler.getRecord(2011, 1, 10);

			//Print some info
			System.out.println();
			System.out.println();
			System.out.println("R3 contains:");
			System.out.println("Date yyyy mm dd: "+r3.year+"-"+r3.month+"-"+r3.day);
			System.out.println("Physical exercise rate: "+r3.physicalRate);
			System.out.println("Food 1 name: "+r3.eatenFood.get(0).name);
			System.out.println("Activity 1 name: "+r3.activitiesDone.get(0).name);
			System.out.println();
			//Delete record r2
			dbHandler.removeRecord(r2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//DICTIONARY OPERATIONS
		///////////////////////////////////////////////////		
		
		//ADD NEW ITEM TO DICTIONARY
		try {
			//add to food dictionary
			Food fd1 = new Food("Pizza");
			dbHandler.addToFoodDictionary(fd1);
			
			//get food list from dictionary
			ArrayList<Food> foodsArray = dbHandler.getFoodDictionaryList();
			
			//add to activities dictionary
			dbHandler.addToActivityDictionary(new Activity("Pizza"));
			
			//get food activities from dictionary
			ArrayList<Activity> activitiesArray = dbHandler.getActivitiesDictionaryList();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		
	}
*/
}
