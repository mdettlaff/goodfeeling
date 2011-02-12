package goodfeeling.db;

import goodfeeling.common.Table;
import goodfeeling.db.Activity;
import goodfeeling.db.DbHandler;
import goodfeeling.db.Food;
import goodfeeling.db.Record;
import goodfeeling.db.RecordActivity;
import goodfeeling.db.RecordFood;
import goodfeeling.db.TestResult;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class testClass {
/*
	public static void main(String[] args) {
		
		
		//RECORDS - ADDING / UPDATING / DELETING / READING
		///////////////////////////////////////////////////
		//New record, if fields: day, month and year are not provided, current date is taken
		Record r1 = new Record();
		//add some tests results
		TestResult physicalTest1 = new TestResult(12,"33");
		r1.physicalRates.add(physicalTest1);
		TestResult moodTest1 = new TestResult("76"); //current hour taken
		r1.moodRates.add(moodTest1);
		TestResult mentalTest1 = new TestResult(11,"21");
		r1.mentalRates.add(mentalTest1);
		
			//Create food for the record
			RecordFood food1 = new RecordFood();
			food1.name = "Potato";
			food1.amount = 3;
			food1.unit = "number";
			food1.timeConsumed = "11";
			
			RecordFood food1a = new RecordFood();
			food1a.name = "Potato";
			food1a.amount = 7;
			food1a.unit = "number";
			food1a.timeConsumed = "17";
			
			RecordFood food2 = new RecordFood();
			food2.name = "Tomato";
			food2.amount = 2;
			food2.unit = "kg";
			food2.timeConsumed = "17";	
			
		//add food1, food2 to the record
		r1.addFood(food1);	
		r1.addFood(food1a);
		r1.addFood(food2);
		
			//Create activity for the record
			RecordActivity act1 = new RecordActivity();
			act1.name = "Swimming";
			act1.startHour = 12;
			act1.duration = 63;
			act1.intensivity = "High";	
			r1.addActivity(act1);	
			
			RecordActivity act2 = new RecordActivity();
			act2.name = "Running";
			act2.startHour = 22;
			act2.duration = 13;
			act2.intensivity = "Low";			
			r1.addActivity(act2);	
			
		//Other record, date provided
		Calendar cal = Calendar.getInstance();	
		cal.set(2010, 0, 10); // 0 because months are counted from 0 by the calendar
		Record r2 = new Record();
		r2.date = cal;
		TestResult physicalTest2 = new TestResult(12,"11");
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
			dbHandler.addToFoodDictionary(new Food("Banana"));
			dbHandler.addToFoodDictionary(new Food("Potato"));
			dbHandler.addToFoodDictionary(new Food("Tomato"));
			dbHandler.addToFoodDictionary(new Food("Chicken meat"));
			dbHandler.addToFoodDictionary(new Food("Fish meat"));
			dbHandler.addToFoodDictionary(new Food("Cow meat"));
			dbHandler.addToFoodDictionary(new Food("Apple"));
			dbHandler.addToFoodDictionary(new Food("Orange"));
			dbHandler.addToFoodDictionary(new Food("Watermelon"));
			dbHandler.addToFoodDictionary(new Food("Carrot"));
			dbHandler.addToFoodDictionary(new Food("Broccoli"));
			
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
			dbHandler.addToActivityDictionary(new Activity("Diving"));
			dbHandler.addToActivityDictionary(new Activity("Swimming"));
			dbHandler.addToActivityDictionary(new Activity("Playing video games"));
			dbHandler.addToActivityDictionary(new Activity("Playing computer games"));
			dbHandler.addToActivityDictionary(new Activity("Jogging"));
			dbHandler.addToActivityDictionary(new Activity("Watching TV"));
			dbHandler.addToActivityDictionary(new Activity("Sleeping"));
			
			//get activities list from dictionary
			ArrayList<Activity> activitiesArray = dbHandler.getActivitiesDictionaryList();
			System.out.println("Activities in DB:");
			System.out.println();
			for(int i = 0; i < activitiesArray.size(); i++){
				System.out.println(i+". name: "+activitiesArray.get(i).name);
			}
			generateRecords();
			//GENERATE CSV DOCUMENT
			dbHandler.generateCSV("mentalrate"); //Testing only
			Table table = dbHandler.generateDataTable("mentalrate");
			System.out.println(table.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}		
		//
		
	}
	public static void generateRecords(){
		DbHandler dbHandler= new DbHandler();
		ArrayList<Activity> activitiesArray = dbHandler.getActivitiesDictionaryList();
		ArrayList<Food> foodsArray = dbHandler.getFoodDictionaryList();
		ArrayList<String> testsRates = new ArrayList<String>();
		testsRates.add("awful");
		testsRates.add("poor");
		testsRates.add("medicore");
		testsRates.add("great");
		testsRates.add("suberb");
		ArrayList<String> timesConsumed = new ArrayList<String>();
		timesConsumed.add("before 11");
		timesConsumed.add("between 11 and 17");
		timesConsumed.add("after 17");
		ArrayList<String> intensivities = new ArrayList<String>();
		intensivities.add("Low");
		intensivities.add("Medium");
		intensivities.add("High");		
		Random rand = new Random();
		
		Calendar startCal = Calendar.getInstance();	
		startCal.set(2010, 0, 1); 
		Calendar endCal = Calendar.getInstance();	
		endCal.set(2010, 4, 28); 	
		while(startCal.before(endCal) || startCal.equals(endCal)){
			
			Record record = new Record();
			record.date = startCal;
			int numOfFood = 1+rand.nextInt(12);
			int numOfActivities = 1+rand.nextInt(7);
			
			for(int i = 0; i < numOfFood; i++ ){
				RecordFood food1 = new RecordFood();
				food1.name = foodsArray.get(rand.nextInt(foodsArray.size())).name;	
				food1.amount = 1+rand.nextInt(12);
				food1.unit = "number";
				food1.timeConsumed = Integer.toString(1+rand.nextInt(24));
				record.addFood(food1);
			}
			for(int i = 0; i < numOfActivities; i++ ){
				RecordActivity act = new RecordActivity();
				act.name = activitiesArray.get(rand.nextInt(activitiesArray.size())).name;	
				act.startHour = 1+rand.nextInt(23);
				act.duration = 1+rand.nextInt(59);
				act.intensivity = intensivities.get(rand.nextInt(intensivities.size()));			
				record.addActivity(act);					
			}			
			TestResult physicalTest = new TestResult(1+rand.nextInt(24),Integer.toString(rand.nextInt(101)));
			record.physicalRates.add(physicalTest);
			TestResult moodTest = new TestResult(1+rand.nextInt(24),Integer.toString(rand.nextInt(101)));
			record.moodRates.add(moodTest);			
			TestResult mentalTest = new TestResult(1+rand.nextInt(24),Integer.toString(rand.nextInt(101)));
			record.mentalRates.add(mentalTest);
			
			try {
				dbHandler.addOrUpdateRecord(record);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			startCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH), startCal.get(Calendar.DATE)+1);
		}
		
	}
*/
}
