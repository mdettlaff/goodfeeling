package goodfeeling.userstate.picture_test;

import goodfeeling.gui.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

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
    
    private int step = -1; //says how many questions were asked
    public int getStep() {		return step;	}
	public void setStep(int step) {		this.step = step;	}
	public void incStep(){ 		setStep(getStep()+1); 		}
	
	private int stepRound = -1;
	public int getStepRound() {		return stepRound;	}
	public void setStepRound(int stepRound) {	this.stepRound = stepRound;	}
	public void incStepRound(){ 		setStepRound(getStepRound()+1); }

	private boolean testState = false;
	
    
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
	final private int[][][] mImageIds = {
	/* Category sequence:
	 * morning (waking up)
	 * exams
	 * work on computer (work)
	 * talks (meeting with others)
	 * housework
	 * study (learning)
	 * decisions
	 * career
	 * relationships
	 * comfort (frame of mind) */
    		{	
    		  {
      			R.drawable.picture_test_morning_negative1, 
      			R.drawable.picture_test_morning_negative1, 
        		R.drawable.picture_test_morning_negative1, 
        		R.drawable.picture_test_morning_negative2, 
        		R.drawable.picture_test_morning_negative3, 
        		R.drawable.picture_test_morning_negative4, 
        		R.drawable.picture_test_morning_negative5, 
        		R.drawable.picture_test_morning_negative6, 
        		R.drawable.picture_test_morning_negative7, 
        		R.drawable.picture_test_morning_negative8,
        		R.drawable.picture_test_morning_negative9, 
        		R.drawable.picture_test_morning_negative10
        	  },
        	  {
    			R.drawable.picture_test_morning_positive1, 
    			R.drawable.picture_test_morning_positive2,
        		R.drawable.picture_test_morning_positive3, 
        		R.drawable.picture_test_morning_positive4,
        		R.drawable.picture_test_morning_positive5, 
        		R.drawable.picture_test_morning_positive6,
        		R.drawable.picture_test_morning_positive7,
        		R.drawable.picture_test_morning_positive8,
        		R.drawable.picture_test_morning_positive9,
        		R.drawable.picture_test_morning_positive10 
        	  }
    		},
    		{
    		  {
				R.drawable.picture_test_exam_negative1, 
				R.drawable.picture_test_exam_negative2, 
				R.drawable.picture_test_exam_negative3, 
				R.drawable.picture_test_exam_negative4, 
				R.drawable.picture_test_exam_negative5, 
				R.drawable.picture_test_exam_negative6, 
				R.drawable.picture_test_exam_negative7, 
				R.drawable.picture_test_exam_negative8,
				R.drawable.picture_test_exam_negative9, 
				R.drawable.picture_test_exam_negative10
    		  },
    		  {
    			R.drawable.picture_test_exam_positive1, 
    			R.drawable.picture_test_exam_positive2, 
    			R.drawable.picture_test_exam_positive3,
    			R.drawable.picture_test_exam_positive5, 
    			R.drawable.picture_test_exam_positive6, 
    			R.drawable.picture_test_exam_positive7, 
    			R.drawable.picture_test_exam_positive8,
    			R.drawable.picture_test_exam_positive9, 
    			R.drawable.picture_test_exam_positive10,
    			R.drawable.picture_test_exam_positive11, 
    			R.drawable.picture_test_exam_positive13, 
    			R.drawable.picture_test_exam_positive14,
    			R.drawable.picture_test_exam_positive15, 
    			R.drawable.picture_test_exam_positive16
    		  }
    		},
    		{
    		  {
    			R.drawable.picture_test_workonthecomputer_negative1,
    			R.drawable.picture_test_workonthecomputer_negative2,	
    			R.drawable.picture_test_workonthecomputer_negative3,	
    			R.drawable.picture_test_workonthecomputer_negative4,	
    			R.drawable.picture_test_workonthecomputer_negative5,	
    			R.drawable.picture_test_workonthecomputer_negative6,
    			R.drawable.picture_test_workonthecomputer_negative7,
    			R.drawable.picture_test_workonthecomputer_negative8,
    			R.drawable.picture_test_workonthecomputer_negative9	
    		  },
    		  {
    			R.drawable.picture_test_workonthecomputer_positive1,
    			R.drawable.picture_test_workonthecomputer_positive2,
    			R.drawable.picture_test_workonthecomputer_positive4,
    			R.drawable.picture_test_workonthecomputer_positive5,
    			R.drawable.picture_test_workonthecomputer_positive6
    		  }
    		},
    		{
      		  {
      			R.drawable.picture_test_talks_negative1,
      			R.drawable.picture_test_talks_negative2,
      			R.drawable.picture_test_talks_negative3,
      			R.drawable.picture_test_talks_negative4,
      			R.drawable.picture_test_talks_negative5,
      			R.drawable.picture_test_talks_negative6
      		  },
      		  {
      			R.drawable.picture_test_talks_positive1,
      			R.drawable.picture_test_talks_positive2,
      			R.drawable.picture_test_talks_positive3,
      			R.drawable.picture_test_talks_positive4,
      			R.drawable.picture_test_talks_positive5,
      			R.drawable.picture_test_talks_positive6
      		  }
      		},
    		{
      		  {
      			R.drawable.picture_test_housework_negative1,
      			R.drawable.picture_test_housework_negative2,
      			R.drawable.picture_test_housework_negative3,
      			R.drawable.picture_test_housework_negative4
      		  },
      		  {
      			R.drawable.picture_test_housework_positive1,
      			R.drawable.picture_test_housework_positive2,
      			R.drawable.picture_test_housework_positive3,
      			R.drawable.picture_test_housework_positive4
      		  }
      		},
    		{
      		  {
      			R.drawable.picture_test_study_negative1,
      			R.drawable.picture_test_study_negative2,
      			R.drawable.picture_test_study_negative3,
      			R.drawable.picture_test_study_negative4
      		  },
      		  {
      			R.drawable.picture_test_study_positive1,
      			R.drawable.picture_test_study_positive2,
      			R.drawable.picture_test_study_positive3,
      			R.drawable.picture_test_study_positive4
      		  }
      		},
    		{
      		  {
      			R.drawable.picture_test_decisions_negative1,
      			R.drawable.picture_test_decisions_negative2,
      			R.drawable.picture_test_decisions_negative4
      		  },
      		  {
      			R.drawable.picture_test_decisions_positive1,
      			R.drawable.picture_test_decisions_positive2,
      			R.drawable.picture_test_decisions_positive3
      		  }
      		},
    		{
        	  {
        		R.drawable.picture_test_career_negative1, 
        		R.drawable.picture_test_career_negative2, 
        		R.drawable.picture_test_career_negative3, 
        		R.drawable.picture_test_career_negative5
        	  },
        	  {
        		R.drawable.picture_test_career_positive1,
        		R.drawable.picture_test_career_positive2,
        		R.drawable.picture_test_career_positive3,
        		R.drawable.picture_test_career_positive5
        	  }
        	},
    		{
      		  {
      			R.drawable.picture_test_relationships_negative1,
      			R.drawable.picture_test_relationships_negative2,
      			R.drawable.picture_test_relationships_negative3,
      			R.drawable.picture_test_relationships_negative4
      		  },
      		  {
      			R.drawable.picture_test_relationships_positive1,
      			R.drawable.picture_test_relationships_positive2,
      			R.drawable.picture_test_relationships_positive3,
      			R.drawable.picture_test_relationships_positive4,
      			R.drawable.picture_test_relationships_positive5,
      			R.drawable.picture_test_relationships_positive6,
      			R.drawable.picture_test_relationships_positive7
      		  }
      		},
    		{
      		  {
      			R.drawable.picture_test_comfort_negative1,
      			R.drawable.picture_test_comfort_negative2,
      			R.drawable.picture_test_comfort_negative3,
      			R.drawable.picture_test_comfort_negative4,
      			R.drawable.picture_test_comfort_negative5,
      			R.drawable.picture_test_comfort_negative6
      		  },
      		  {
      			R.drawable.picture_test_comfort_positive1,
      			R.drawable.picture_test_comfort_positive3,
      			R.drawable.picture_test_comfort_positive4,
      			R.drawable.picture_test_comfort_positive5,
      			R.drawable.picture_test_comfort_positive6
      		  }
      		}
    };
	
	/** @return table with references to pictures
	 */
	public int[][][] getmImageIds() {
		return mImageIds;
	}
	
	/**
	 * Store results of test (numPositiveA, numA)
	 */
	//private int my_answers[][];
	private int answersPositive[];
	private int answers[];
	
	/**Initialize any phase of test: <br>
	 * choose pictures for test using <b>Math.random()</b> as random 
	 * function,<br>
	 * display info using <b>Toast</b>
	 * @see Math
	 * @see Toast
	 */
    public void test_init(){
    	System.out.println(">>>>>>>" + getStep() + ">>>>>>" + getStepRound());
    	if(getStepRound() == 1){
    		testState = true;
    		Intent data = new Intent();
    		data.putExtra("PictureTestResultPositive", this.answersPositive);
    		data.putExtra("PictureTestResult", this.answers);
    		setResult(RESULT_OK, data);
    		finish();
    		
    	}
    	
    	//if step reach the end of categories to choose...
    	if( (getStep()) == (getmImageIds().length - 1) ) {
        	incStepRound();
    		setStep(0); //start again from 0...
    	}
    	else {
    	incStep(); //increase progress of test
    	}
    	setCat(getStep()); //set actual category to step
    	    	
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
    	setP1(((int)(Math.random() *
    			getmImageIds()[getCat()][getP1_type()].length)));
    	setP2(((int)(Math.random() *
    			getmImageIds()[getCat()][getP2_type()].length)));
    	//display info box
    	Toast.makeText(PictureTest.this, 
    			"(" 
    			+ this.answersPositive[getStep()]
    			+ ", " 
    			+ this.answers[getStep()]
    			+ ")", 
    			Toast.LENGTH_SHORT).show();
    }

    
    //Interface for android starts here... 
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
        setContentView(R.layout.picture_test);
        
        this.answersPositive = new int[getmImageIds().length];
        this.answers = new int[getmImageIds().length];
    	for(int i = 0; i < getmImageIds().length; i++) {
    		this.answersPositive[i] = 0;
    		this.answers[i] = 0;
        }
        //init 1st step of test
        test_init();
        
        //define buttons - all 3 options in test
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
    	if(testState)
    		return;
    	
    	if(getP1_type() == 1){ //1 is positive
    		this.answersPositive[getStep()]++;
    		//my_answers.incPoints();  //increase points
    	}
    	
    	this.answers[getStep()]++; //picture chosen so increase
    	test_init();
    }
    
    /**Apply choice of first picture
     */
	void second_picture(){
    	if(testState)
    		return;
    	if(getP2_type() == 1){ //1 is positive
    		this.answersPositive[getStep()]++;
    		//my_answers.incPoints();  //increase points
    	}
    	
    	this.answers[getStep()]++; //picture chosen so increase
    	test_init();
    }
    
	
    /**Apply choosing none of pictures
     */
    void dont_choose(){
    	if(testState)
    		return;
    	Toast.makeText(PictureTest.this, "Nothing chosen...", 
    			Toast.LENGTH_SHORT).show();
    	
    	//nothing chosen so we don't increase count of given answers
    	test_init();
    }
}