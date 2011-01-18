package picture.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PictureTest extends Activity {
    int p1 = 0, p2 = 1; //numery mImagesIds ktore bedziemy porownywac...
    
    /*  public int getP1() {		return p1;	}
    	public void setP1(int p1) {		this.p1 = p1;	}
    	public int getP2() {		return p2;	}
    	public void setP2(int p2) {		this.p2 = p2;	}
    */
        final int[] mImageIds = {
                R.drawable.decyzje1,
                R.drawable.decyzje2,
                R.drawable.decyzje3,
                R.drawable.decyzje4,
                R.drawable.decyzje5,
                R.drawable.decyzje6,
                R.drawable.decyzje7,
                R.drawable.egzamin1,
                R.drawable.egzamin2,
                R.drawable.egzamin3,
                R.drawable.egzamin4,
                R.drawable.egzamin5,
                R.drawable.egzamin6,
                R.drawable.egzamin7,
                R.drawable.egzamin8,
                R.drawable.egzamin9,
                R.drawable.egzamin10,
                R.drawable.egzamin11
        };
        
        
       /*TUTAJ ZACZYNA SIĘ INTERFEJS... 
        */
        
        /** Called when the activity is first created. */
        @Override
    	public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);


            final Button option1 = (Button) findViewById(R.id.button_option_1);
            final Button option2 = (Button) findViewById(R.id.button_option_2);
            final Button option3 = (Button) findViewById(R.id.button_option_3);

            //obrazek 1szy
    		option1.setBackgroundDrawable(getResources().getDrawable(mImageIds[p1]));
    		option1.setText("");
            option1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	first_picture(); //wywolujemy metode kliknieciem
            		option1.setBackgroundDrawable(getResources().getDrawable(mImageIds[p1]));
                	option2.setBackgroundDrawable(getResources().getDrawable(mImageIds[p2]));
                }
            });
            
            //2gi 
    		option2.setBackgroundDrawable(getResources().getDrawable(mImageIds[p2]));
    		option2.setText("");
            option2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                	second_picture();
            		option1.setBackgroundDrawable(getResources().getDrawable(mImageIds[p1]));
                	option2.setBackgroundDrawable(getResources().getDrawable(mImageIds[p2]));
                }
            });
            
            //lub opcja "nie wybieram"
    		option3.setText("Nie wybieram");
            option3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    dont_choose();
            		option1.setBackgroundDrawable(getResources().getDrawable(mImageIds[p1]));
                	option2.setBackgroundDrawable(getResources().getDrawable(mImageIds[p2]));
                }
            });
        }
        
        
        void first_picture(){
        	Toast.makeText(PictureTest.this, "First...", Toast.LENGTH_SHORT).show();
        	p1++; //jakas zmiana obrazka...
        	p2++;
        }
        
        void second_picture(){
        	Toast.makeText(PictureTest.this, "Second...", Toast.LENGTH_SHORT).show();
        	p1++;
        	p2++;
        }
        
        void dont_choose(){
        	Toast.makeText(PictureTest.this, "Nothing chosen...", Toast.LENGTH_SHORT).show();
        	p1++;
        	p2++;
        }
    }