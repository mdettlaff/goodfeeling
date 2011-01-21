package picture.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Object Answers - basic structure to store points, 
 * and number of questions <br>
 */
class Answers {
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

	public float getResult() {
		if(getQuestion_amount() == 0){
			return 0;
		}
		result = ((float)getPoints()/((float)getQuestion_amount()))*10;
		return result;
	}
	
}

/**
 * <b>PictureTest</b> - an {@link Activity} 
 * which execute "test of pictures":<br>
 * - user is shown pair of pictures <br>
 * - can choose one of them or none <br>
 * - test counts questions and answers <br>
 * @author darek
 */
public class PictureTest extends Activity {
	
	private int cat = 0; //categories of pics-egz: morning,exam,decisions.. 
    public int getCat() {		return cat;		}
	public void setCat(int cat) {	this.cat = cat;		}
	
	private int p1_type, p2_type; // is pic positive type or not
	public int getP1_type() {		return p1_type;	}
	public void setP1_type(int p1Type) {		p1_type = p1Type;	}
	public int getP2_type() {		return p2_type;	}
	public void setP2_type(int p2Type) {		p2_type = p2Type;	}
	
	private int p1, p2; // pic number
    public int getP1() {	return p1;	}
    public void setP1(int p1) {		this.p1 = p1;	}
    public int getP2() {		return p2;	}
    public void setP2(int p2) {		this.p2 = p2;	}

    Answers my_answers = new Answers(0,0);
    
	/**Stores Ids of pictures (look at source of generated {@link R} class)<br>
	 * <b>mImageId</b>s map<br>
	 * 		| -- morning<br>
	 * 				| -- negative<br>
	 * 						| -- pic number X<br>
	 * 				| -- positive<br>
	 * 						| -- pic number X<br>
	 * 		| -- exams<br>
	 * 				| -- negative<br>
	 * 						| -- pic number X<br>
	 * 				| -- positive<br>
	 * 						| -- pic number X<br>
	 * 		| ...<br>
	 * ...
	 */
	final int[][][] mImageIds = {
    		{	
    		  {
      			R.drawable.morning_negative1, R.drawable.morning_negative1, 
        		R.drawable.morning_negative1, R.drawable.morning_negative2, 
        		R.drawable.morning_negative3, R.drawable.morning_negative4, 
        		R.drawable.morning_negative5, R.drawable.morning_negative6, 
        		R.drawable.morning_negative7
        	  },
        	  {
    			R.drawable.morning_positive1, R.drawable.morning_positive2,
        		R.drawable.morning_positive3, R.drawable.morning_positive4,
        		R.drawable.morning_positive5, R.drawable.morning_positive6,
        		R.drawable.morning_positive7 
        	  }
    		},
    		{
    		  {
				R.drawable.exam_negative1, R.drawable.exam_negative2, 
				R.drawable.exam_negative3, R.drawable.exam_negative4, 
				R.drawable.exam_negative5, R.drawable.exam_negative6, 
				R.drawable.exam_negative7, R.drawable.exam_negative8
    		  },
    		  {
    			R.drawable.exam_positive1, R.drawable.exam_positive2, 
    			R.drawable.exam_positive3, R.drawable.exam_positive4, 
    			R.drawable.exam_positive5, R.drawable.exam_positive6, 
    			R.drawable.exam_positive7    				
    		  }
    		}
    };
	
	public int[][][] getmImageIds() {
		return mImageIds;
	}
	    
	/**Initialize any phase of test: <br>
	 * choose pictures for test using <b>Math.random()</b> as random 
	 * function,<br>
	 * display info using <b>Toast</b>
	 * @see Math
	 * @see Toast
	 */
    public void test_init(){
    	//if category is 0 (start) then Random
    	//if(getCat() == 0){
    		setCat((int)(Math.random()*getmImageIds().length));
    	//}
    	    	
    	//select which picture (first or second) will be positive/negative
    	if(((int)(Math.random()*2)) == 0){
    		setP1_type(0);
    		setP2_type(1);
    	} else
    	{
    		setP1_type(1);
    		setP2_type(0);
    	}
    	
    	
    	//select picture for each button
    	setP1(((int)(Math.random()*getmImageIds()
    										[getCat()][getP1_type()].length)));
    	setP2(((int)(Math.random()*getmImageIds()
    										[getCat()][getP2_type()].length)));
/*    	Toast.makeText(PictureTest.this, 	my_answers.getPoints() + ", " + 
    										my_answers.getQuestion_amount(), 
    										Toast.LENGTH_SHORT).show();*/
    	//display info box
    	Toast.makeText(PictureTest.this, "("+ my_answers.getPoints() + ", " 
							    			+ my_answers.getQuestion_amount() 
							    			+ ")\n" 
							    			+ my_answers.getResult() 
							    			+ "%", 
							    			Toast.LENGTH_SHORT).show();
    }
	
	//Interface for android starts somewhere here... 
	//look at onCreate func
	/** Called when the activity is first created.
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     * @see Button
     * @see {@link OnClickListener}
     * 
     */
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        test_init();

        final Button option1 = 
        			(Button) findViewById(R.id.picture_test_button_option_1);
        final Button option2 = 
        			(Button) findViewById(R.id.picture_test_button_option_2);
        final Button option3 = 
        			(Button) findViewById(R.id.picture_test_button_option_3);

        //1st picture is option1 (button1)
        option1.setBackgroundDrawable(getResources().
    			getDrawable(mImageIds[getCat()][getP1_type()][getP1()]));
		option1.setText("");
        option1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
				// Perform action on click
            	first_picture();
        		option1.setBackgroundDrawable(getResources().
        			getDrawable(mImageIds[getCat()][getP1_type()][getP1()]));
        		option2.setBackgroundDrawable(getResources().
        			getDrawable(mImageIds[getCat()][getP2_type()][getP2()]));
            }
        });
        
        //2nd 
        option2.setBackgroundDrawable(getResources().
        			getDrawable(mImageIds[getCat()][getP2_type()][getP2()]));
		option2.setText("");
        option2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	second_picture();
        		option1.setBackgroundDrawable(getResources().
        			getDrawable(mImageIds[getCat()][getP1_type()][getP1()]));
        		option2.setBackgroundDrawable(getResources().
        			getDrawable(mImageIds[getCat()][getP2_type()][getP2()]));
            }
        });
        
        //3rd option mean not choosing any of above
		option3.setText("None of above");
        option3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                dont_choose();
        		option1.setBackgroundDrawable(getResources().
        			getDrawable(mImageIds[getCat()][getP1_type()][getP1()]));
            	option2.setBackgroundDrawable(getResources().
        			getDrawable(mImageIds[getCat()][getP2_type()][getP2()]));
            }
        });
    }
    
    
    /**Apply choice of first picture
     */
    void first_picture(){
//    	Toast.makeText(PictureTest.this, "First...", 
//    									Toast.LENGTH_SHORT).show();
    	if(getP1_type() == 1){ //1 is positive
    		my_answers.incPoints();  //increase points
    	}
    	
    	my_answers.incQuestion_amount(); //picture chosen so increase
    	test_init();
    }
    
    /**Apply choice of first picture
     */
	void second_picture(){
//    	Toast.makeText(PictureTest.this, "Second...", 
//    									Toast.LENGTH_SHORT).show();
    	if(getP2_type() == 1){ //1 is positive
    		my_answers.incPoints();  //increase points
    	}
    	
    	my_answers.incQuestion_amount(); //picture chosen so increase
    	test_init();
    }
    
	
    /**Apply choosing none of pictures
     * 
     */
    void dont_choose(){
    	Toast.makeText(PictureTest.this, "Nothing chosen...", 
    									Toast.LENGTH_SHORT).show();
    	
    	//nothing chosen so we don't increase count of given answers
    	test_init();
    }
}