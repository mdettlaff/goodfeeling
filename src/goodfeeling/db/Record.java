//Klasa ktorej obiekt zawiera strukture pol wpisu do bazy
//aktualnie jej pola sa tymczasowe i ulegna zmianie wedlug pozniejszych ustalen
public class Record {
	
	//podstawowe dane //WYMAGANE!
	public int day = 0; //np 23 lub 5 etc
	public int month = 0;; //np 11 lub 6 etc
	public int year = 0;; // np 2010
	
	//dane uzupelniajace
	public String physicalRate = null;
	public String mentalRate = null;
	
	public String[] foodEaten = null;
	public String timeAwaken = null;
	public int hoursSleept = -1;

}
