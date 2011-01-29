package goodfeeling.userstate.balloon;

import goodfeeling.gui.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Balloon extends Activity implements Handler.Callback {
	
	public static final int MSG_EXIT = 0;
	
	private BalloonView view;
	
	private BalloonThread thread;
	
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
        setContentView(R.layout.balloon);
        this.view = (BalloonView)findViewById(R.id.balloon);
        this.thread = this.view.getThread();
        this.handler = new Handler(this);
        this.thread.setHandler(this.handler);
    }
	
	public boolean handleMessage(Message msg) {
		if(msg.what == MSG_EXIT) {
			Intent data = new Intent(); 
			this.thread.getGameResult().put(data);
			setResult(RESULT_OK, data);
			this.finish();
			return true;
		}
		return false;
	}
}