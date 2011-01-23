package goodfeeling.userstate.picture_test;

/**Object Answers - basic structure to store points, 
 * and number of questions <br>
 */
public class Answers {
	/**Store points <br>
	 * +10 for positive choice;<br>
	 * +0 for negative;
	 */
	int points;
	/**Count how many questions were answered,<br>
	 * +1 for any choice;<br>
	 * +0 for not choosing any;
	 */
	int question_amount;
	
	/**
	 * value to convert and return result of tests
	 */
	float result;
	
	/**Constructor for Answers without parameters, <br>
	 * set <b>points</b> to 0 as default, <br>
	 * set <b>question_amount</b> to 0 as default
	 */
	Answers(){
		setPoints(0);
		setQuestion_amount(0);
	}
	
	/**Constructor for Answers with parameters
	 * @param p store points, set as requested during create
	 * @param qa count how many questions were answered, set as above
	 */
	Answers(int p, int qa){
		setPoints(p);
		setQuestion_amount(qa);
	}
	
	/**@return amount of points
	 */
	public int getPoints() {
		return points;
	}
	/**Sets point to indicate value
	 * @param points
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	/**Increase by <b>10</b> if positive answer
	 */
	public void incPoints(){
		int a = getPoints()+10;
		setPoints(a);
	}
	/**@return count of question answered
	 */
	public int getQuestion_amount() {
		return question_amount;
	}
	/**Set number of question to indicate value
	 * @param questionAmount
	 */
	public void setQuestion_amount(int questionAmount) {
		question_amount = questionAmount;
	}
	/**Increase question_amount by <b>1</b> if any answer were given
	 */
	public void incQuestion_amount(){
		int a = getQuestion_amount()+1;
		setQuestion_amount(a);
	}

	/**@return calculated (result)% 
	 */
	public float getResult() {
		if(getQuestion_amount() == 0){
			return 0;
		}
		result = ((float)getPoints()/((float)getQuestion_amount()))*10;
		return result;
	}
	
}
