package goodfeeling.db;

import goodfeeling.common.Table;
import goodfeeling.db.Record;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
* Class handling database operations
* record files format: yyyy_mm.xml
* creates a file if it doesn't exist
*/
public class DbHandler {
	
	public String xmlActivitiesFileName = "activities_dictionary.xml";
	public String xmlFoodsFileName = "foods_dictionary.xml";
	public String csvFilename = "data.csv";
	
	public int numberOfActivities = 0;
	public int numberOfFood = 0;
	public int numberOfFoodSums = 0;
	
	private final InputOutput io;

	/**
	 * Use only for testing purposes.
	 */
	DbHandler() {
		io = new StandardJavaFileIO();
	}

	/**
	 * For use in production code.
	 *
	 * @param io File I/O suitable for Android devices.
	 */
	public DbHandler(InputOutput io) {
		this.io = io;
	}

	/** Adds or updates one record in database
	* @param record Record class object, update if record of same date exist in database
	*/
	public void addOrUpdateRecord(Record record) throws Exception{
		//int day= record.date.get(Calendar.DATE);
		int month = record.date.get(Calendar.MONTH)+1;
		int year = record.date.get(Calendar.YEAR);
		String xmlFileName = year+"_"+month+".xml";

		try {
			Document doc = parseFile(xmlFileName);
			doc = modifyDocument(doc,record); 
			saveXMLDocument(xmlFileName, doc);	    	
		} catch (FileNotFoundException e) {
			Document doc = newDocument(record); 
			saveXMLDocument(xmlFileName, doc);
		}
	}

	/** Removes a record from database
	 * @param record Record class object to be removed from database, year, month, day should not be empty
	 */
	public void removeRecord(Record record){
		int day= record.date.get(Calendar.DATE);
		int month = record.date.get(Calendar.MONTH)+1;
		int year = record.date.get(Calendar.YEAR);
		
		if(year > 0 && month > 0 && day > 0){
			String xmlFileName = year+"_"+month+".xml";
		    
		    try {
		    	Document doc = parseFile(xmlFileName);
		    	doc = removeFromDocument(doc,record); 
		    	saveXMLDocument(xmlFileName, doc);	    	
		    } catch (FileNotFoundException e) {
		    }
		}
	}
	
	
	/** Loads record with a provided date 
	 * @param cal Calendar object with date 
	 * @return Record class object or empty Record if not in database
	 */
	public Record getRecord(Calendar cal){
		
		Record record = new Record();
		int day= cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		 record.date = cal;
		if(year > 0 && month > 0 && day > 0){
			String xmlFileName = year+"_"+month+".xml";
		    try {
		    	Document doc = parseFile(xmlFileName);
		    	record = readFromDocument(doc,record);     	
		    } catch (FileNotFoundException e) {
		    	
		    }
		}		
		return record;
	}
	/** Adds Food object to Food dictionary database
	 * @param food Food class object, food.name is required
	 */
	public void addToFoodDictionary(Food food) throws Exception {
		try {
			Document doc = parseFile(xmlFoodsFileName);
			doc = modifyFoodDocument(doc,food);  
			saveXMLDocument(xmlFoodsFileName, doc);	    	
		} catch (FileNotFoundException e) {
			Document doc = newFoodDocument(food); 
			saveXMLDocument(xmlFoodsFileName, doc);
		}
	}
	/** Gets Food objects in database 
	 * @return ArrayList<Food>
	 */
	public ArrayList<Food> getFoodDictionaryList(){
		
		ArrayList<Food> foods = new ArrayList<Food>();
		
		try {
			Document doc = parseFile(xmlFoodsFileName);
			foods = readFoodsFromDocument(doc, foods);     	
		} catch (FileNotFoundException e) {
		}
		return foods;	

	}
	/** Gets Activity objects in database
	 * @return ArrayList<Activity>
	 */	
	public ArrayList<Activity> getActivitiesDictionaryList() {
	
		ArrayList<Activity> activities = new ArrayList<Activity>();
		
		try {
			Document doc = parseFile(xmlActivitiesFileName);
			activities = readActivitiesFromDocument(doc, activities);     	
		} catch (FileNotFoundException e) {
		}
		return activities;		
	}
	/** Adds Activity object to Activities dictionary database
	 * @param activity Activity class object, activity.name is required
	 */
	public void addToActivityDictionary(Activity activity) throws Exception {

		try {
			Document doc = parseFile(xmlActivitiesFileName);
			doc = modifyActivityDocument(doc,activity);  
			saveXMLDocument(xmlActivitiesFileName, doc);	    	
		} catch (FileNotFoundException e) {
			Document doc = newActivityDocument(activity); 
			saveXMLDocument(xmlActivitiesFileName, doc);
		}
	}
	/** Generates Table object for all records in database
	 * @param resultsType one of strings: "mentalrate","moodrate" or "physicalrate"
	 */		
	public Table generateDataTable(String resultsType) throws Exception {
		Table table = new Table();
		ArrayList<Record> records = new  ArrayList<Record>();
		ArrayList<Record> wekaReadyRecords = new  ArrayList<Record>();
		
		records = getRecordsListFromDb(2010);
		
		wekaReadyRecords = repositionRecordsData(records,resultsType);
		records = new  ArrayList<Record>(); //clear unused data
		table = convertRecordsToTable(wekaReadyRecords,resultsType);
	
		return table;
	    			
	}	
	/** Generates CSV file based on all records in database for testing only
	 * @param resultsType one of strings: "mentalrate","moodrate" or "physicalrate"
	 */	
	public void generateCSV(String resultsType) throws Exception {
		ArrayList<Record> records = new  ArrayList<Record>();
		ArrayList<Record> wekaReadyRecords = new  ArrayList<Record>();
		records = getRecordsListFromDb(2010);
		
		wekaReadyRecords = repositionRecordsData(records,resultsType);
		
		saveCSVDocument(csvFilename,wekaReadyRecords,resultsType); 			
	}	
	////////////////////////////////////////////////////////////////////
	//Private //////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////
	
	private ArrayList<Record> repositionRecordsData(ArrayList<Record> records,String resultsType){
		ArrayList<Record> wekaReadyRecords = new  ArrayList<Record>();
		
		for(int i = 0; i < records.size(); i++){
			Record tempRecord = new Record();
			tempRecord.date = records.get(i).date;
			tempRecord.mentalRates = records.get(i).mentalRates;
			tempRecord.moodRates = records.get(i).moodRates;
			tempRecord.physicalRates = records.get(i).physicalRates;
			
			int timeEnd = 0;
			if(resultsType.equals("mentalrate")){
				if(records.get(i).mentalRates.size() > 0){
					timeEnd = records.get(i).mentalRates.get(records.get(i).mentalRates.size()-1).hour;
				}
			}	
			if(resultsType.equals("moodrate")){
				if(records.get(i).moodRates.size() > 0){
					timeEnd = records.get(i).moodRates.get(records.get(i).moodRates.size()-1).hour;
				}
			}
			if(resultsType.equals("physicalrate")){
				if(records.get(i).physicalRates.size() > 0){
					timeEnd = records.get(i).physicalRates.get(records.get(i).physicalRates.size()-1).hour;
				}
			}			
			
			for(int j = 0; j < records.get(i).eatenFood.size(); j++){
				if(Integer.parseInt(records.get(i).eatenFood.get(j).timeConsumed) <= timeEnd){
					tempRecord.eatenFood.add(records.get(i).eatenFood.get(j));
				}
			}
			for(int j = 0; j < records.get(i).activitiesDone.size(); j++){
				if(records.get(i).activitiesDone.get(j).startHour <= timeEnd){
					tempRecord.activitiesDone.add(records.get(i).activitiesDone.get(j));
				}
			}
			if(i > 0){
				int timeStart = 0;
				if(resultsType.equals("mentalrate")){
					if(records.get(i-1).mentalRates.size() > 0){
						timeStart = records.get(i-1).mentalRates.get(records.get(i-1).mentalRates.size()-1).hour;
					}
				}
				if(resultsType.equals("moodrate")){
					if(records.get(i-1).moodRates.size() > 0){
						timeStart = records.get(i-1).moodRates.get(records.get(i-1).moodRates.size()-1).hour;
					}
				}
				if(resultsType.equals("physicalrate")){
					if(records.get(i-1).physicalRates.size() > 0){
						timeStart = records.get(i-1).physicalRates.get(records.get(i-1).physicalRates.size()-1).hour;
					}
				}				
				for(int j = 0; j < records.get(i-1).eatenFood.size(); j++){
					if(Integer.parseInt(records.get(i-1).eatenFood.get(j).timeConsumed) >= timeStart){
						tempRecord.eatenFood.add(records.get(i-1).eatenFood.get(j));
					}
				}
				for(int j = 0; j < records.get(i-1).activitiesDone.size(); j++){
					if(records.get(i-1).activitiesDone.get(j).startHour >= timeStart){
						tempRecord.activitiesDone.add(records.get(i-1).activitiesDone.get(j));
					}
				}
			}
			
			for(int ii = 0; ii < tempRecord.eatenFood.size(); ii++){
				boolean found = false;
				int foundIndex = 0;
				for(int jj = 0; jj < tempRecord.eatenFoodSum.size(); jj++){
					if(tempRecord.eatenFood.get(ii).name.equals(tempRecord.eatenFoodSum.get(jj).name)){
						found = true;
						foundIndex = jj;
					}
				}
				if(!found){
					RecordFood f1 = new RecordFood();
					f1.name = tempRecord.eatenFood.get(ii).name;
					f1.amount = tempRecord.eatenFood.get(ii).amount;
					tempRecord.eatenFoodSum.add(f1);
				}else{
					tempRecord.eatenFoodSum.get(foundIndex).amount = tempRecord.eatenFoodSum.get(foundIndex).amount + tempRecord.eatenFood.get(ii).amount;
				}
			}	
			
			wekaReadyRecords.add(tempRecord);
			
			if(numberOfFood < tempRecord.eatenFood.size()){
				numberOfFood = tempRecord.eatenFood.size();
			}
			if(numberOfActivities < tempRecord.activitiesDone.size()){
				numberOfActivities = tempRecord.activitiesDone.size();
			}
			if(numberOfFoodSums < tempRecord.eatenFoodSum.size()){
				numberOfFoodSums = tempRecord.eatenFoodSum.size();
			}			
			
			
			
		}
		
		return wekaReadyRecords;
	}
	
	private Table convertRecordsToTable(ArrayList<Record> records,String resultsType){
		
		int rowSize = (numberOfActivities*4)+(numberOfFood*3)+(numberOfFoodSums*2)+2;
    	String[] columnNames = new String[rowSize];
    	columnNames[0] = "date";
    	int columnI = 1;
    	for(int j = 0; j < numberOfActivities; j++){
    		columnNames[columnI] = "activity"+j+"name"; 
    		columnI++;
    		columnNames[columnI] = "activity"+j+"starthour"; 
    		columnI++;
    		columnNames[columnI] = "activity"+j+"duration"; 
    		columnI++;
    		columnNames[columnI] = "activity"+j+"intensivity"; 
    		columnI++;
    	}
    	for(int j = 0; j < numberOfFood; j++){
    		columnNames[columnI] = "food"+j+"name";
    		columnI++;
    		columnNames[columnI] = "food"+j+"amount";
    		columnI++;
    		columnNames[columnI] = "food"+j+"timeconsumed";
    		columnI++;
    	}   
    	for(int j = 0; j < numberOfFoodSums; j++){
    		columnNames[columnI] = "foodsum"+j+"name";
    		columnI++;
    		columnNames[columnI] = "foodsum"+j+"amount";
    		columnI++;
    	}  
    	
		if(resultsType.equals("mentalrate")){
			columnNames[columnI] ="mentalrate";
		}
		if(resultsType.equals("moodrate")){
			columnNames[columnI] ="moodrate";
		}
		if(resultsType.equals("physicalrate")){
			columnNames[columnI] ="physicalrate";
		}	   	

    	Table table = new Table(columnNames);
    	
    	for(int i = 0; i < records.size(); i++){
    		int day= records.get(i).date.get(Calendar.DATE);
    		int month = records.get(i).date.get(Calendar.MONTH)+1;
    		int year = records.get(i).date.get(Calendar.YEAR);
    		Object[] row = new Object[rowSize];
    		row[0] = day+"-"+month+"-"+year;
            int rcolumnI = 1;
            
        	for(int j = 0; j < numberOfActivities; j++){
        		if(j<records.get(i).activitiesDone.size()){
        			row[rcolumnI] = records.get(i).activitiesDone.get(j).name; rcolumnI++;
        			row[rcolumnI] = records.get(i).activitiesDone.get(j).startHour; rcolumnI++;
        			row[rcolumnI] = records.get(i).activitiesDone.get(j).duration; rcolumnI++;
        			row[rcolumnI] = records.get(i).activitiesDone.get(j).intensivity; rcolumnI++;
        		}else{
        			row[rcolumnI] =""; rcolumnI++;
        			row[rcolumnI] =""; rcolumnI++;
        			row[rcolumnI] =""; rcolumnI++;
        			row[rcolumnI] =""; rcolumnI++;
        		}
        	}
        	for(int j = 0; j < numberOfFood; j++){
        		
        		if(j<records.get(i).eatenFood.size()){
        			row[rcolumnI] = records.get(i).eatenFood.get(j).name; rcolumnI++;
        			row[rcolumnI] = records.get(i).eatenFood.get(j).amount; rcolumnI++;
        			row[rcolumnI] = Integer.parseInt(records.get(i).eatenFood.get(j).timeConsumed); rcolumnI++;
        		}else{
        			row[rcolumnI] =""; rcolumnI++;
        			row[rcolumnI] =""; rcolumnI++;
        			row[rcolumnI] =""; rcolumnI++;
        		}            		
        	} 
        	for(int j = 0; j < numberOfFoodSums; j++){
        		
        		if(j<records.get(i).eatenFoodSum.size()){
        			row[rcolumnI] = records.get(i).eatenFoodSum.get(j).name; rcolumnI++;
        			row[rcolumnI] = records.get(i).eatenFoodSum.get(j).amount; rcolumnI++;
        		}else{
        			row[rcolumnI] =""; rcolumnI++;
        			row[rcolumnI] =""; rcolumnI++;
        		}            		
        	}             	
        	
    		if(resultsType.equals("mentalrate")){
    			row[rcolumnI] = Integer.parseInt(records.get(i).getLastMentalRate());
    		}
    		if(resultsType.equals("moodrate")){
    			row[rcolumnI] = Integer.parseInt(records.get(i).getLastMoodRate()); 
    		}
    		if(resultsType.equals("physicalrate")){
    			row[rcolumnI] = Integer.parseInt(records.get(i).getLastPhysicalRate());
    		}	         	
        	
        	table.addRow(row);
    	}
		return table;
	}
	
	private ArrayList<Record> getRecordsListFromDb(int year){
		ArrayList<Record> records = new  ArrayList<Record>();
		Calendar current = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.set(year,0,1);
		//int i = 0;
		while(cal.before(current) || cal.equals(current)){
			Record tempRecord = new Record();
			Calendar tempCal = Calendar.getInstance();
			tempCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
			tempRecord = getRecord(tempCal);
			if(tempRecord.eatenFood.size() > 0 || tempRecord.activitiesDone.size() > 0 || !tempRecord.getLastMentalRate().equals("")|| !tempRecord.getLastMoodRate().equals("")|| !tempRecord.getLastPhysicalRate().equals("")){
				records.add(tempRecord);
			}
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)+1);
		}
		return records;
	}
	private Record readFromDocument(Document doc, Record record) { 
		
		int day= record.date.get(Calendar.DATE);
		int month = record.date.get(Calendar.MONTH)+1;
		int year = record.date.get(Calendar.YEAR);		
		
		Element rootElement = doc.getDocumentElement();

		boolean read = false;
		Node kid;
		Node kid2;
		Node kid3;
		Node kid4;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("idkey") ){
		                	
		                     if(getElementValue(kid2).equals(year+"_"+month+"_"+day)){
		                    	
		                    	 read = true;
		                     }
		                }
		                if(read){
		                	//physicalRate
		                	if( kid2.getNodeName().equals("physicalRates") ){
		                		if (kid2.hasChildNodes()){
			                		for( kid3 = kid2.getFirstChild(); kid3 != null; kid3 = kid3.getNextSibling() ){
			                			if( kid3.getNodeName().equals("physicalRate") ){
					                		if (kid3.hasChildNodes()){
					                			TestResult tempTest = new TestResult();
						                		for( kid4 = kid3.getFirstChild(); kid4 != null; kid4 = kid4.getNextSibling() ){
						                			
						                			//name
						                			if( kid4.getNodeName().equals("rate") ){
						                				tempTest.rate = getElementValue(kid4);
						                			}
						                			//amount
						                			if( kid4.getNodeName().equals("hour") ){
						                				tempTest.hour = Integer.parseInt(getElementValue(kid4));
						                			}

						                		}
					                			//add
					                			record.physicalRates.add(tempTest);						                		
					                		}
			                			}
			                		}
		                		}
		                	}
		                	//mentalRate
		                	if( kid2.getNodeName().equals("mentalRates") ){
		                		if (kid2.hasChildNodes()){
			                		for( kid3 = kid2.getFirstChild(); kid3 != null; kid3 = kid3.getNextSibling() ){
			                			if( kid3.getNodeName().equals("mentalRate") ){
					                		if (kid3.hasChildNodes()){
					                			TestResult tempTest = new TestResult();
						                		for( kid4 = kid3.getFirstChild(); kid4 != null; kid4 = kid4.getNextSibling() ){
						                			
						                			//name
						                			if( kid4.getNodeName().equals("rate") ){
						                				tempTest.rate = getElementValue(kid4);
						                			}
						                			//amount
						                			if( kid4.getNodeName().equals("hour") ){
						                				tempTest.hour = Integer.parseInt(getElementValue(kid4));
						                			}

						                		}
					                			//add
					                			record.mentalRates.add(tempTest);						                		
					                		}
			                			}
			                		}
		                		}		  
		                	}
		                	//moodRate
		                	if( kid2.getNodeName().equals("moodRates") ){
		                		if (kid2.hasChildNodes()){
			                		for( kid3 = kid2.getFirstChild(); kid3 != null; kid3 = kid3.getNextSibling() ){
			                			if( kid3.getNodeName().equals("moodRate") ){
					                		if (kid3.hasChildNodes()){
					                			TestResult tempTest = new TestResult();
						                		for( kid4 = kid3.getFirstChild(); kid4 != null; kid4 = kid4.getNextSibling() ){
						                			
						                			//name
						                			if( kid4.getNodeName().equals("rate") ){
						                				tempTest.rate = getElementValue(kid4);
						                			}
						                			//amount
						                			if( kid4.getNodeName().equals("hour") ){
						                				tempTest.hour = Integer.parseInt(getElementValue(kid4));
						                			}

						                		}
					                			//add
					                			record.moodRates.add(tempTest);						                		
					                		}
			                			}
			                		}
		                		}
		                	}		                	
		                	//food
		                	if( kid2.getNodeName().equals("foods") ){
		                		if (kid2.hasChildNodes()){
			                		for( kid3 = kid2.getFirstChild(); kid3 != null; kid3 = kid3.getNextSibling() ){
			                			if( kid3.getNodeName().equals("food") ){
					                		if (kid3.hasChildNodes()){
					                			RecordFood tempFood = new RecordFood();
					                			for( kid4 = kid3.getFirstChild(); kid4 != null; kid4 = kid4.getNextSibling() ){
						                			
						                			//name
						                			if( kid4.getNodeName().equals("name") ){
						                				tempFood.name = getElementValue(kid4);
						                			}
						                			//amount
						                			if( kid4.getNodeName().equals("amount") ){
						                				tempFood.amount = Float.parseFloat(getElementValue(kid4));
						                			}
						                			//unit
						                			if( kid4.getNodeName().equals("units") ){
						                				tempFood.unit = getElementValue(kid4);
						                			}
						                			//timeConsumed
						                			if( kid4.getNodeName().equals("timeConsumed") ){
						                				tempFood.timeConsumed = getElementValue(kid4);
						                			}

						                		}
					                			//add
					                			record.addFood(tempFood);						                		
					                		}
			                			}
			                		}
		                		}
		                	}
		                	//activities
		                	if( kid2.getNodeName().equals("activities") ){
		                		if (kid2.hasChildNodes()){
			                		for( kid3 = kid2.getFirstChild(); kid3 != null; kid3 = kid3.getNextSibling() ){
			                			if( kid3.getNodeName().equals("activity") ){
					                		if (kid3.hasChildNodes()){
					                			RecordActivity tempActivity = new RecordActivity();
						                		for( kid4 = kid3.getFirstChild(); kid4 != null; kid4 = kid4.getNextSibling() ){
						                			
						                			//name
						                			if( kid4.getNodeName().equals("name") ){
						                				tempActivity.name = getElementValue(kid4);
						                			}
						                			//startHour
						                			if( kid4.getNodeName().equals("startHour") ){
						                				tempActivity.startHour = Integer.parseInt(getElementValue(kid4));
						                			}
						                			//unit
						                			if( kid4.getNodeName().equals("duration") ){
						                				tempActivity.duration = Integer.parseInt(getElementValue(kid4));
						                			}
						                			//timeConsumed
						                			if( kid4.getNodeName().equals("intensivity") ){
						                				tempActivity.intensivity = getElementValue(kid4);
						                			}

						                		}
					                			//add
					                			record.addActivity(tempActivity);						                		
					                		}
			                			}
			                		}
		                		}
		                	}

		                	
		                } 		                
	            	}
	            	read = false;	
            	}
            }
        }		
		
		return record;
	}	
	
	private Document removeFromDocument(Document doc, Record record) {
		int day= record.date.get(Calendar.DATE);
		int month = record.date.get(Calendar.MONTH)+1;
		int year = record.date.get(Calendar.YEAR);		
		
		
		Element rootElement = doc.getDocumentElement();

		boolean delete = false;
		Node kid;
		Node kid2;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("idkey") ){
		                	
		                     if(getElementValue(kid2).equals(year+"_"+month+"_"+day)){
		                    	
		                    	 delete = true;
		                     }
		                }
		                
	            	}
	                if(delete){
	                	delete = false;
	               		rootElement.removeChild(kid);
	                }   
            	}
            }
        }		
		
		return doc;
	}	
	private Document modifyDocument(Document doc, Record record) {
		int day= record.date.get(Calendar.DATE);
		int month = record.date.get(Calendar.MONTH)+1;
		int year = record.date.get(Calendar.YEAR);		
		
		Element rootElement = doc.getDocumentElement();

		boolean inDB = false;
		boolean insert = false;
		Node kid;
		Node kid2;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("idkey") ){
		                	
		                     if(getElementValue(kid2).equals(year+"_"+month+"_"+day)){
		                    	 inDB = true;
		                    	 insert = true;
		                     }
		                }
		                
	            	}
	                if(insert){
	               	  insert = false;

	               	  Element tempElement = newRecordElement(record,doc);
	               	  rootElement.replaceChild(tempElement, kid);
	                 
	                }   
            	}
            }
        }
        if(!inDB){
        	Element tempElement = newRecordElement(record,doc);
        	rootElement.appendChild(tempElement);
        }
		
		
		return doc;
	}

	private ArrayList<Activity> readActivitiesFromDocument(Document doc, ArrayList<Activity> activities) { 
		
		Element rootElement = doc.getDocumentElement();

		Node kid;
		Node kid2;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("name") ){
		                	String name = getElementValue(kid2);
		                	activities.add(new Activity(name));
		                }
	            	}		
            	}
            }
        }		
		
		return activities;
	}		
	
	private Document newActivityDocument(Activity activity) throws ParserConfigurationException {
		
	    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		
		//Root
		String root ="activities";
		Element rootElement = document.createElement(root);
		document.appendChild(rootElement);
		//newRecordElement(record,rootElement,document);
    	Element tempElement = newActivityElement(activity,document);
    	rootElement.appendChild(tempElement);		
		return document;
		
	}	
	
	private Document modifyActivityDocument(Document doc, Activity activity) {	
		
		Element rootElement = doc.getDocumentElement();

	
		boolean inDB = false;
		Node kid;
		Node kid2;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("name") ){
		                	
		                     if(getElementValue(kid2).equals(activity.name)){
		                    	 inDB = true;
		                     }
		                }
		                
	            	}
            	}
            }
        }
        if(!inDB){
        	Element tempElement = newActivityElement(activity,doc);
        	rootElement.appendChild(tempElement);
        }        
		return doc;
	}
	
	private Element newActivityElement(Activity activity,  Document document){ 

		
		//Record
		Element activityElement = document.createElement("activity");
	//	rootElement.appendChild(recordElement);
		
		//name
		Element nameElement = document.createElement("name");
		nameElement.appendChild(document.createTextNode(activity.name));
		activityElement.appendChild(nameElement);
		
 
		return activityElement;
	}	
	
	//FOOD OPERATIONS
	
	
	
	private ArrayList<Food> readFoodsFromDocument(Document doc, ArrayList<Food> foods) { 
		
		Element rootElement = doc.getDocumentElement();

		Node kid;
		Node kid2;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("name") ){
		                	String name = getElementValue(kid2);
		                	foods.add(new Food(name));
		                }
	            	}		
            	}
            }
        }		
		
		return foods;
	}		
	 
	private Document newFoodDocument(Food food) throws ParserConfigurationException {
		
	    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		
		//Root
		String root ="foods";
		Element rootElement = document.createElement(root);
		document.appendChild(rootElement);
		//newRecordElement(record,rootElement,document);
    	Element tempElement = newFoodElement(food,document);
    	rootElement.appendChild(tempElement);		
		return document;
		
	}	
	
	private Document modifyFoodDocument(Document doc, Food food) {	
		
		Element rootElement = doc.getDocumentElement();

	
		boolean inDB = false;
		Node kid;
		Node kid2;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("name") ){
		                	
		                     if(getElementValue(kid2).equals(food.name)){
		                    	 inDB = true;
		                     }
		                }
		                
	            	}
            	}
            }
        }
        if(!inDB){
        	Element tempElement = newFoodElement(food,doc);
        	rootElement.appendChild(tempElement);
        }        
		return doc;
	}
	
	private Element newFoodElement(Food food,  Document document){ 

		
		//Record
		Element foodElement = document.createElement("food");
	//	rootElement.appendChild(recordElement);
		
		//name
		Element nameElement = document.createElement("name");
		nameElement.appendChild(document.createTextNode(food.name));
		foodElement.appendChild(nameElement);
		
 
		return foodElement;
	}		

	//---------------------------
	
	private Element newRecordElement(Record record,  Document document){ 
		
		int day= record.date.get(Calendar.DATE);
		int month = record.date.get(Calendar.MONTH)+1;
		int year = record.date.get(Calendar.YEAR);		
				
		
		//Record
		Element recordElement = document.createElement("record");
	//	rootElement.appendChild(recordElement);
		
		//idkey
		Element idkey = document.createElement("idkey");
		idkey.appendChild(document.createTextNode(year+"_"+month+"_"+day));
		recordElement.appendChild(idkey);
		
		//day
		Element dayElement = document.createElement("day");
		dayElement.appendChild(document.createTextNode(Integer.toString(day)));
		recordElement.appendChild(dayElement);		
		//month
		Element monthElement = document.createElement("month");
		monthElement.appendChild(document.createTextNode(Integer.toString(month)));
		recordElement.appendChild(monthElement);
		//year
		Element yearElement = document.createElement("year");
		yearElement.appendChild(document.createTextNode(Integer.toString(year)));
		recordElement.appendChild(yearElement);		
		
		//REST OF DATA
		//physicalRates
		
		if(record.physicalRates.size() > 0){

			Element testResultsElement = document.createElement("physicalRates");

			for(int ii = 0; ii < record.physicalRates.size(); ii++ ){
				Element testResultElement = document.createElement("physicalRate");
				TestResult tempTestResult = record.physicalRates.get(ii);
				
				//rate
				Element rateElement = document.createElement("rate");
				rateElement.appendChild(document.createTextNode(tempTestResult.rate));
				testResultElement.appendChild(rateElement);
				//hour
				Element hourElement = document.createElement("hour");
				hourElement.appendChild(document.createTextNode(Integer.toString(tempTestResult.hour)));
				testResultElement.appendChild(hourElement);	

				testResultsElement.appendChild(testResultElement);	
			}
			
			recordElement.appendChild(testResultsElement);				

		}	
		//mentalRate
		if(record.mentalRates.size() > 0){
			Element testResultsElement = document.createElement("mentalRates");

			for(int ii = 0; ii < record.mentalRates.size(); ii++ ){
				Element testResultElement = document.createElement("mentalRate");
				TestResult tempTestResult = record.mentalRates.get(ii);
				
				//rate
				Element rateElement = document.createElement("rate");
				rateElement.appendChild(document.createTextNode(tempTestResult.rate));
				testResultElement.appendChild(rateElement);
				//hour
				Element hourElement = document.createElement("hour");
				hourElement.appendChild(document.createTextNode(Integer.toString(tempTestResult.hour)));
				testResultElement.appendChild(hourElement);	

				testResultsElement.appendChild(testResultElement);	
			}
			
			recordElement.appendChild(testResultsElement);	
		}
		//moodRate
		if(record.moodRates.size() > 0){
			Element testResultsElement = document.createElement("moodRates");

			for(int ii = 0; ii < record.moodRates.size(); ii++ ){
				Element testResultElement = document.createElement("moodRate");
				TestResult tempTestResult = record.moodRates.get(ii);
				
				//rate
				Element rateElement = document.createElement("rate");
				rateElement.appendChild(document.createTextNode(tempTestResult.rate));
				testResultElement.appendChild(rateElement);
				//hour
				Element hourElement = document.createElement("hour");
				hourElement.appendChild(document.createTextNode(Integer.toString(tempTestResult.hour)));
				testResultElement.appendChild(hourElement);	

				testResultsElement.appendChild(testResultElement);	
			}
			
			recordElement.appendChild(testResultsElement);	
		}
		//FOODS
		if(record.eatenFood.size() > 0){
			Element foodsElement = document.createElement("foods");

			for(int ii = 0; ii < record.eatenFood.size(); ii++ ){
				Element foodElement = document.createElement("food");
				RecordFood tempFood = record.eatenFood.get(ii);
				
				//Food - name
				Element foodNameElement = document.createElement("name");
				foodNameElement.appendChild(document.createTextNode(tempFood.name));
				foodElement.appendChild(foodNameElement);
				//Food - amount
				Element foodAmountElement = document.createElement("amount");
				foodAmountElement.appendChild(document.createTextNode(Float.toString(tempFood.amount)));
				foodElement.appendChild(foodAmountElement);	
				//Food - unit
				Element foodUnitElement = document.createElement("unit");
				foodUnitElement.appendChild(document.createTextNode(tempFood.unit));
				foodElement.appendChild(foodUnitElement);	
				//Food - timeConsumed
				Element foodTimeElement = document.createElement("timeConsumed");
				foodTimeElement.appendChild(document.createTextNode(tempFood.timeConsumed));
				foodElement.appendChild(foodTimeElement);					
				//
				foodsElement.appendChild(foodElement);	
			}
			
			recordElement.appendChild(foodsElement);				
		}
		//Activities
		if(record.activitiesDone.size() > 0){
			Element activitiesElement = document.createElement("activities");

			for(int ii = 0; ii < record.activitiesDone.size(); ii++ ){
				Element activityElement = document.createElement("activity");
				RecordActivity tempActivity = record.activitiesDone.get(ii);
				
				//Activity - name
				Element activityNameElement = document.createElement("name");
				activityNameElement.appendChild(document.createTextNode(tempActivity.name));
				activityElement.appendChild(activityNameElement);
				//Activity - startHour
				Element activityStartHourElement = document.createElement("startHour");
				activityStartHourElement.appendChild(document.createTextNode(Integer.toString(tempActivity.startHour)));
				activityElement.appendChild(activityStartHourElement);	
				//Activity - duration
				Element activityDurationElement = document.createElement("duration");
				activityDurationElement.appendChild(document.createTextNode(Integer.toString(tempActivity.duration)));
				activityElement.appendChild(activityDurationElement);	
				//Activity - intensivity
				Element activityIntensivityElement = document.createElement("intensivity");
				activityIntensivityElement.appendChild(document.createTextNode(tempActivity.intensivity));
				activityElement.appendChild(activityIntensivityElement);					
				//
				activitiesElement.appendChild(activityElement);	
			}
			
			recordElement.appendChild(activitiesElement);				
		}		
 
		return recordElement;
	}
	
	private Document newDocument(Record record) throws ParserConfigurationException {
		
	    DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.newDocument();
		
		
		//Root
		String root ="records";
		Element rootElement = document.createElement(root);
		document.appendChild(rootElement);
		//newRecordElement(record,rootElement,document);
    	Element tempElement = newRecordElement(record,document);
    	rootElement.appendChild(tempElement);		
		return document;
		
	}	
    
	private final static String getElementValue( Node elem ) {
        Node kid;
        if( elem != null){
            if (elem.hasChildNodes()){
                for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
                    if( kid.getNodeType() == Node.TEXT_NODE  ){
                        return kid.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
	
	/*
    private String getIndentSpaces(int indent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }
    
   
    private void writeDocumentToOutput(Node node,int indent) {
        // get element name
        String nodeName = node.getNodeName();
        // get element value
        String nodeValue = getElementValue(node);
        // get attributes of element
        NamedNodeMap attributes = node.getAttributes();
        System.out.println(getIndentSpaces(indent) + "NodeName: " + nodeName + ", NodeValue: " + nodeValue);
        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            System.out.println(getIndentSpaces(indent + 2) + "AttributeName: " + attribute.getNodeName() + ", attributeValue: " + attribute.getNodeValue());
        }
        // write all child nodes recursively
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                writeDocumentToOutput(child,indent + 2);
            }
        }
    }
    */
  
    private boolean saveXMLDocument(String fileName, Document doc) {
        System.out.println("Saving XML file... " + fileName);
        // open output stream where XML Document will be saved
        OutputStream os;
        Transformer transformer;
        try {
            os = io.getOutputStream(fileName);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error occured: " + e.getMessage());
            return false;
        }
        // Use a Transformer for output
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        }
        catch (TransformerConfigurationException e) {
            System.out.println("Transformer configuration error: " + e.getMessage());
            return false;
        }
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(os);
        // transform source into result will do save
        try {
            transformer.transform(source, result);
        }
        catch (TransformerException e) {
            System.out.println("Error transform: " + e.getMessage());
        }
        System.out.println("XML file saved.");
        return true;
    }
    
    private Document parseFile(String fileName) throws FileNotFoundException {
       // System.out.println("Parsing XML file... " + fileName);
        DocumentBuilder docBuilder;
        Document doc = null;
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        docBuilderFactory.setIgnoringElementContentWhitespace(true);
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            System.out.println("Wrong parser configuration: " + e.getMessage());
            return null;
        }
        try {
            doc = docBuilder.parse(io.getInputStream(fileName));
        }
        catch (SAXException e) {
            System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
        }
        catch (FileNotFoundException e) {
        	throw e;
        }
        catch (IOException e) {
            System.out.println("Could not read source file: " + e.getMessage());
        }
        //System.out.println("XML file parsed");
        return doc;
    }
    private boolean saveCSVDocument(String fileName, ArrayList<Record> records,String resultsType) {
        System.out.println("Saving CSV file... " + fileName);
        // open output stream where CSV Document will be saved
        OutputStream os;
        
        try {
        	String source = "date";
        	for(int j = 0; j < numberOfActivities; j++){
        		source = source + ",activity"+j+"name,activity"+j+"starthour,activity"+j+"duration,activity"+j+"intensivity";
        	}
        	for(int j = 0; j < numberOfFood; j++){
        		source = source + ",food"+j+"name,food"+j+"amount,food"+j+"timeconsumed";
        	}   
        	for(int j = 0; j < numberOfFoodSums; j++){
        		source = source + ",foodsum"+j+"name,foodsum"+j+"amount";
        	}          	
        	source = source + ","+resultsType;
        	source = source + "\r\n";
        	
        	for(int i = 0; i < records.size(); i++){
        		int day= records.get(i).date.get(Calendar.DATE);
        		int month = records.get(i).date.get(Calendar.MONTH)+1;
        		int year = records.get(i).date.get(Calendar.YEAR);
        		os = io.getOutputStream(fileName);
             
                source = source+ day+"-"+month+"-"+year;
            	for(int j = 0; j < numberOfActivities; j++){
            		//System.out.println("numberOfActivities="+numberOfActivities);
            		//System.out.println("j="+j);
            		//System.out.println("act size="+records.get(i).activitiesDone.size());
            		if(j<records.get(i).activitiesDone.size()){
            			source = source + ","+records.get(i).activitiesDone.get(j).name+","+records.get(i).activitiesDone.get(j).startHour+","+records.get(i).activitiesDone.get(j).duration+","+records.get(i).activitiesDone.get(j).intensivity;
            		}else{
            			source = source + ",,,,";
            		}
            		
            	}
            	
            	for(int j = 0; j < numberOfFood; j++){
            		
            		if(j<records.get(i).eatenFood.size()){
            			source = source + ","+records.get(i).eatenFood.get(j).name+","+records.get(i).eatenFood.get(j).amount+","+records.get(i).eatenFood.get(j).timeConsumed;
            		}else{
            			source = source + ",,,";
            		}            		
            	} 
            	
            	for(int j = 0; j < numberOfFoodSums; j++){
            		
            		if(j<records.get(i).eatenFoodSum.size()){
            			source = source + ","+records.get(i).eatenFoodSum.get(j).name+","+records.get(i).eatenFoodSum.get(j).amount;
            		}else{
            			source = source + ",,";
            		}            		
            	}            
        		if(resultsType.equals("mentalrate")){
        			source = source+","+records.get(i).getLastMentalRate()+"\r\n";
        		}
        		if(resultsType.equals("moodrate")){
        			source = source+","+records.get(i).getLastMoodRate()+"\r\n";
        		}
        		if(resultsType.equals("physicalrate")){
        			source = source+","+records.get(i).getLastPhysicalRate()+"\r\n";
        		}           	
                

            	byte buf[] = source.getBytes();
  	
            	try {
                	os.write(buf);
                	os.close(); 
				} catch (IOException e) {
					System.out.println("Error : " + e.getMessage());
				}       		
        	}

            
            
        }
        catch (FileNotFoundException e) {
            System.out.println("Error occured: " + e.getMessage());
            return false;
        }

        System.out.println("CSV file saved.");
        return true;
    }   
    
}
