import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

//Klasa obslugi bazy, korzysta z klasy Record do opakowaywania informacji o wpisie
//pliki zapisywane sa w nazwach: rrrr_mm.xml

public class DbHandler {
	
	//Dodaje badz zmienia caly wpis do bazy
	//przyjmuje parametry:
	//record - obiekt klasy Record, 
	//jesli pole date wskazuje na istniejacy wpis ulegnie on modyfikacji
	
	public void addOrUpdateRecord(Record record) throws Exception{
		
		String xmlFileName = record.year+"_"+record.month+".xml";
	    File f = new File(xmlFileName);
	    
	    if(!f.exists()){
	    	Document doc = newDocument(record); 
	    	saveXMLDocument(xmlFileName, doc);
	    	
	    }else{
	    	Document doc = parseFile(xmlFileName);
	    	doc = modifyDocument(doc,record); 
	    	saveXMLDocument(xmlFileName, doc);	    	
	    }

	    
		
	}

	//Usuwa z bazy caly wpis
	//przyjmuje parametry:
	//record - wpis do usuniecia wymaga pol year, month i day
	public void removeRecord(Record record){
		if(record.year > 0 && record.month > 0 && record.day > 0){
			String xmlFileName = record.year+"_"+record.month+".xml";
		    File f = new File(xmlFileName);
		    
		    if(f.exists()){
	
		    	Document doc = parseFile(xmlFileName);
		    	doc = removeFromDocument(doc,record); 
		    	saveXMLDocument(xmlFileName, doc);	    	
		    }	
		}
	}
	
	//Wczytuje z bazy wpis z danego dnia , miesiaca , roku
	//zwraca w postaci obiektu klasy Record
	//jesli nie istnieje wpis to zwroci pusty obiekt klasy
	public Record getRecord(int year, int month, int day){
		Record record = new Record();
		record.day= day;
		record.month = month;
		record.year = year;
		
		if(year > 0 && month > 0 && day > 0){
			String xmlFileName = year+"_"+month+".xml";
		    File f = new File(xmlFileName);
		    
		    if(f.exists()){
	
		    	Document doc = parseFile(xmlFileName);
		    	record = readFromDocument(doc,record);     	
		    	
		    }	
		}		
		return record;
	}





	////////////////////////////////////////////////////////////////////
	//METODY PRYWATNE///////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////

	private Record readFromDocument(Document doc, Record record) { //wymaga deklaracji pol
		Element rootElement = doc.getDocumentElement();

		boolean read = false;
		Node kid;
		Node kid2;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("idkey") ){
		                	
		                     if(getElementValue(kid2).equals(record.year+"_"+record.month+"_"+record.day)){
		                    	
		                    	 read = true;
		                     }
		                }
		                if(read){
		                	//timeAwaken
		                	if( kid2.getNodeName().equals("timeAwaken") ){
		                		record.timeAwaken = getElementValue(kid2);
		                	}
		               		//pozostale TODO
		                	//...
		                	
		                } 		                
	            	}
	            	read = false;		
            	}
            }
        }		
		
		return record;
	}	
	
	private Document removeFromDocument(Document doc, Record record) {

		Element rootElement = doc.getDocumentElement();

		boolean delete = false;
		Node kid;
		Node kid2;
        if (rootElement.hasChildNodes()){
            for( kid = rootElement.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
            	if (kid.hasChildNodes()){
	            	for( kid2 = kid.getFirstChild(); kid2 != null; kid2 = kid2.getNextSibling() ){
		                if( kid2.getNodeName().equals("idkey") ){
		                	
		                     if(getElementValue(kid2).equals(record.year+"_"+record.month+"_"+record.day)){
		                    	
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
		                	
		                     if(getElementValue(kid2).equals(record.year+"_"+record.month+"_"+record.day)){
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

	
	private Element newRecordElement(Record record,  Document document){ //wymaga deklaracji pol
		//Record
		Element recordElement = document.createElement("record");
	//	rootElement.appendChild(recordElement);
		
		//idkey
		Element idkey = document.createElement("idkey");
		idkey.appendChild(document.createTextNode(record.year+"_"+record.month+"_"+record.day));
		recordElement.appendChild(idkey);
		
		//day
		Element dayElement = document.createElement("day");
		dayElement.appendChild(document.createTextNode(Integer.toString(record.day)));
		recordElement.appendChild(dayElement);		
		//month
		Element monthElement = document.createElement("month");
		monthElement.appendChild(document.createTextNode(Integer.toString(record.month)));
		recordElement.appendChild(monthElement);
		//year
		Element yearElement = document.createElement("year");
		yearElement.appendChild(document.createTextNode(Integer.toString(record.year)));
		recordElement.appendChild(yearElement);		
		
		//NIEWYMAGANE
		//timeAwaken
		if(record.timeAwaken != null){
			Element timeAwakenElement = document.createElement("timeAwaken");
			timeAwakenElement.appendChild(document.createTextNode(record.timeAwaken));
			recordElement.appendChild(timeAwakenElement);	
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
  
    
    /** Creates a new instance of ParseXMLFile */
   // public ParseXMLFile() {
        // parse XML file -> XML document will be build
          //Document doc = parseFile(xmlFileName);
        // get root node of xml tree structure
         // Node root = doc.getDocumentElement();
        // write node and its child nodes into System.out
        //System.out.println("Statemend of XML document...");
        //writeDocumentToOutput(root,0);
        //System.out.println("... end of statement");
        // write Document into XML file
          //saveXMLDocument(targetFileName, doc);
 //   }
    
    /** Returns element value
     * @param elem element (it is XML tag)
     * @return Element value otherwise empty String
     */
    public final static String getElementValue( Node elem ) {
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
    
    private String getIndentSpaces(int indent) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indent; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }
    
    /** Writes node and all child nodes into System.out
     * @param node XML node from from XML tree wrom which will output statement start
     * @param indent number of spaces used to indent output
     */
    public void writeDocumentToOutput(Node node,int indent) {
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
    
    /** Saves XML Document into XML file.
     * @param fileName XML file name
     * @param doc XML document to save
     * @return <B>true</B> if method success <B>false</B> otherwise
     */    
    public boolean saveXMLDocument(String fileName, Document doc) {
        System.out.println("Saving XML file... " + fileName);
        // open output stream where XML Document will be saved
        File xmlOutputFile = new File(fileName);
        FileOutputStream fos;
        Transformer transformer;
        try {
            fos = new FileOutputStream(xmlOutputFile);
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
        StreamResult result = new StreamResult(fos);
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
    
    /** Parses XML file and returns XML document.
     * @param fileName XML file to parse
     * @return XML document or <B>null</B> if error occured
     */
    public Document parseFile(String fileName) {
        System.out.println("Parsing XML file... " + fileName);
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
        File sourceFile = new File(fileName);
        try {
            doc = docBuilder.parse(sourceFile);
        }
        catch (SAXException e) {
            System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
        }
        catch (IOException e) {
            System.out.println("Could not read source file: " + e.getMessage());
        }
        System.out.println("XML file parsed");
        return doc;
    }
    

  //  public static void main(String[] args) {
   //     new ParseXMLFile();
  //  }
	
	
	
	
	
	
}
