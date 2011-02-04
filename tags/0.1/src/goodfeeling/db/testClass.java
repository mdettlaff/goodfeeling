package goodfeeling.db;

import goodfeeling.db.Activity;
import goodfeeling.db.DbHandler;
import goodfeeling.db.Food;
import goodfeeling.db.Record;
import goodfeeling.db.RecordActivity;
import goodfeeling.db.RecordFood;
import goodfeeling.db.TestResult;

import java.util.ArrayList;
import java.util.Calendar;

public class testClass {
/*
	public static void main(String[] args) {
		
		
		//RECORDS - ADDING / UPDATING / DELETING / READING
		///////////////////////////////////////////////////
		//New record, if fields: day, month and year are not provided, current date is taken
		Record r1 = new Record();
		//add some tests results
		TestResult physicalTest1 = new TestResult(12,"Poor");
		r1.physicalRates.add(physicalTest1);
		TestResult moodTest1 = new TestResult("Poor"); //current hour taken
		r1.moodRates.add(moodTest1);
		TestResult mentalTest1 = new TestResult(11,"Poor");
		r1.mentalRates.add(mentalTest1);
		
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
		Calendar cal = Calendar.getInstance();	
		cal.set(2010, 0, 10); // 0 because months are counted from 0 by the calendar
		Record r2 = new Record();
		r2.date = cal;
		TestResult physicalTest2 = new TestResult(12,"Poor");
		r2.physicalRates.add(physicalTest2);		
		r2.addFood(food1);
		r2.addActivity(act1);
		//Init dbHandler
		DbHandler dbHandler= new DbHandler();

		try {
			//update or add both records
			dbHandler.addOrUpdateRecord(r1);
			dbHandler.addOrUpdateRecord(r2);
			
			//load record from database from: 2010 1 10 //rrrr mm dd
			Calendar cal2 = Calendar.getInstance();	
			cal2.set(2010, 0, 10); // 0 because months are counted from 0 by the calendar
			Record r3 = dbHandler.getRecord(cal2);

			//Print some info
			System.out.println();
			System.out.println();
			System.out.println("R3 contains:");
			System.out.println("Date yyyy mm dd: "+r3.date.get(Calendar.YEAR)+"-"+(r3.date.get(Calendar.MONTH)+1)+"-"+r3.date.get(Calendar.DATE));
			System.out.println("Last physical exercise rate: "+r3.getLastPhysicalRate());
			
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
		
		try {
			//FOOD
			//add to food dictionary
			dbHandler.addToFoodDictionary(new Food("Pizza"));
			dbHandler.addToFoodDictionary(new Food("Hamburger"));
			dbHandler.addToFoodDictionary(new Food("Hot dog"));
			
			//get food list from dictionary
			ArrayList<Food> foodsArray = dbHandler.getFoodDictionaryList();
			System.out.println("Food in DB:");
			System.out.println();
			for(int i = 0; i < foodsArray.size(); i++){
				System.out.println(i+". name: "+foodsArray.get(i).name);
			}		
			
			//ACTIVITIES
			//add to activities dictionary
			dbHandler.addToActivityDictionary(new Activity("Running"));
			dbHandler.addToActivityDictionary(new Activity("Walking"));
			dbHandler.addToActivityDictionary(new Activity("Smoking"));
			
			//get activities list from dictionary
			ArrayList<Activity> activitiesArray = dbHandler.getActivitiesDictionaryList();
			System.out.println("Activities in DB:");
			System.out.println();
			for(int i = 0; i < activitiesArray.size(); i++){
				System.out.println(i+". name: "+activitiesArray.get(i).name);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		
	}
*/
}
