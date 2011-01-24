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
	
	public BalloonResult getGameResult() {
		return this.thread.getGameResult();
	}
	
	public GameState getGameState() {
		return this.thread.getGameState();
	}
	
	// event
	
	public boolean onTouchEvent (MotionEvent event) {
		System.out.println(">>>>>>> onTouchEvent");
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			this.thread.doPressed(event.getX(), event.getY());
			return true;
		}
		return false;
	}
	
	// focus
	
	protected void onFocusChanged (boolean gainFocus, int direction, Rect previouslyFocusedRect) {
		System.out.println(">>>>>>> onFocusChanged");
		if(gainFocus)
			this.thread.setGameState(GameState.RESUME);
		else
			this.thread.setGameState(GameState.PAUSE);	
	}
	
	public void onWindowFocusChanged (boolean hasWindowFocus) {
		System.out.println(">>>>>>> onWindowFocusChanged");
		if(hasWindowFocus)
			this.thread.setGameState(GameState.RESUME);
		else
			this.thread.setGameState(GameState.PAUSE);
	}
	
	// SurfaceHolder.Callback implements
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		System.out.println(">>>>>>> surfaceChanged");
		this.thread.setDimension(width, height);
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		System.out.println(">>>>>>> surfaceCreated");
		this.thread.setThreadRun(true);
		this.thread.start();
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println(">>>>>>> surfaceDestroyed");
		this.thread.setThreadRun(false);
		while(true) {
			try {
				this.thread.join();
				return;
			} catch (InterruptedException e) {}
		}
	}
}
