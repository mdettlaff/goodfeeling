package goodfeeling.userstate.balloon;

import android.app.Activity;
import android.os.Bundle;

public class Balloon extends Activity {
	
	private BalloonView view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
        setContentView(R.layout.balloon);
        this.view = (BalloonView) findViewById(R.layout.balloon);
        System.out.println(">>>>>>> onCreate");
    }
    
	@Override
	protected void onStart() {
		super.onStart();
		System.out.println(">>>>>>> onStart");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println(">>>>>>> onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		System.out.println(">>>>>>> onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		System.out.println(">>>>>>> onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println(">>>>>>> onStop");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println(">>>>>>> onDestroy");
		this.finish();
	}
}