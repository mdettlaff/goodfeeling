package goodfeeling.userstate.balloon;

import goodfeeling.userstate.balloon.BalloonThread.GameState;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BalloonView extends SurfaceView implements SurfaceHolder.Callback {
	
	private BalloonThread thread;
	
	public BalloonView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		this.thread = new BalloonThread(holder, context);
		setFocusableInTouchMode(true);
	}
	
	public BalloonThread getThread() {
		return this.thread;
	}
	
	// event
	
	public boolean onTouchEvent (MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			this.thread.doPressed(event.getX(), event.getY());
			return true;
		}
		return false;
	}
	
	// focus
	
	protected void onFocusChanged (boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		if(gainFocus)
			this.thread.setGameState(GameState.RESUME);
		else
			this.thread.setGameState(GameState.PAUSE);	
	}
	
	public void onWindowFocusChanged (boolean hasWindowFocus) {
		if(hasWindowFocus)
			this.thread.setGameState(GameState.RESUME);
		else
			this.thread.setGameState(GameState.PAUSE);
	}
	
	// SurfaceHolder.Callback implements
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		this.thread.setDimension(width, height);
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		this.thread.setThreadRun(true);
		this.thread.start();
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.thread.setThreadRun(false);
		while(true) {
			try {
				this.thread.join();
				return;
			} catch (InterruptedException e) {}
		}
	}
}
