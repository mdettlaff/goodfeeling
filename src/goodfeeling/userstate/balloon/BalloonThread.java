package goodfeeling.userstate.balloon;

import java.util.LinkedList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.SurfaceHolder;

public class BalloonThread extends Thread {
	
	private final long THREAD_INTERVAL = 10l; // in milliseconds
	private final long THREAD_INTERVAL_IDLE = 200l; // in milliseconds
	
	private final long GAME_DURATION = 120000l; // in milliseconds
	
	public enum GameState {
		READY,
		WORK,
		PAUSE,
		RESUME,
		GAMEOVER
	}
	
	public enum Operation {
		ADDITION,
		SUBTRACTION,
		MULTIPLICATION,
		DIVISION
	}
	
	private SurfaceHolder holder;
	
	private boolean threadRun;
	
	private long threadInterval;
	
	private GameState gameState;
	
	private long gameStartedAt;
	
	private BalloonResult gameResult;
	
	private LinkedList<BalloonBalloon> balloons;
	
	private LinkedList<BalloonBalloon> balloonsToDestroy;
	
	private BalloonDrawableBackground dBackground;
	private BalloonDrawableBalloons dBalloons;
	private BalloonDrawableScore dScore;
	private BalloonDrawableClock dClock;
	private BalloonDrawableCorrect dCorrect;
	private BalloonDrawableIncorrect dIncorrect;
	private BalloonDrawableDisplay dDisplay;
	private BalloonDrawableButtons dButtons;
	private BalloonDrawableReady dReady;
	private BalloonDrawableGameOver dGameOver;

	public BalloonThread(SurfaceHolder holder, Context context) {
		this.holder = holder;
		
		this.threadRun = true;
		
		this.threadInterval = THREAD_INTERVAL_IDLE;
		
		this.gameState = GameState.READY;
		
		this.gameStartedAt = 0l;
		
		this.gameResult = new BalloonResult();
		
		this.balloons = new LinkedList<BalloonBalloon>();
		
		this.balloons = new LinkedList<BalloonBalloon>();
		
		this.dBackground = new BalloonDrawableBackground(context);
		this.dBalloons = new BalloonDrawableBalloons(context, this.balloons);
		this.dScore = new BalloonDrawableScore(context);
		this.dClock = new BalloonDrawableClock(context);
		this.dCorrect = new BalloonDrawableCorrect(context);
		this.dIncorrect = new BalloonDrawableIncorrect(context);
		this.dDisplay = new BalloonDrawableDisplay(context);
		this.dButtons = new BalloonDrawableButtons(context);
		this.dReady = new BalloonDrawableReady(context);
		this.dGameOver = new BalloonDrawableGameOver(context);
	}

	public void run() {
		while(this.threadRun) {
			Canvas canvas = null;
			try {
				synchronized(this.holder) {
					canvas = this.holder.lockCanvas();
					doUpdate();
					doDraw(canvas);
				}
			} finally {
				if(canvas != null)
					this.holder.unlockCanvasAndPost(canvas);
			}
			try {
				sleep(this.threadInterval);
			} catch (InterruptedException e) {}
		}
	}
	
	public void setThreadRun(boolean threadRun) {
		this.threadRun = threadRun;
	}
	
	public void setGameState(GameState state) {
		synchronized(this.holder) {
			switch(state) {
				case WORK:
					if(this.gameState == GameState.READY) {
						this.gameStartedAt = System.currentTimeMillis();
						this.gameState = GameState.WORK;
						this.threadInterval = THREAD_INTERVAL;
					}
					return;
				case PAUSE:
					if(this.gameState == GameState.WORK) {
						BalloonInterpolator.pause();
						this.gameState = GameState.PAUSE;
						this.threadInterval = THREAD_INTERVAL_IDLE;
					}
					return;
				case RESUME:
					if(this.gameState == GameState.PAUSE) {
						BalloonInterpolator.resume();
						this.gameState = GameState.WORK;
						this.threadInterval = THREAD_INTERVAL;
					}
					return;
				case GAMEOVER:
					if(this.gameState == GameState.WORK) {
						this.gameState = GameState.GAMEOVER;
						this.threadInterval = THREAD_INTERVAL_IDLE;
					}
					return;
			}
		}
	}
	
	public void doPressed(float x, float y) {
		synchronized(this.holder) {
			switch(this.gameState) {
			case READY:
				setGameState(GameState.WORK);
				return;
			case PAUSE:
				setGameState(GameState.RESUME);
				return;
			case WORK:
				switch(this.dButtons.onPressed(x, y)) {
					case BUTTON_0:
					case BUTTON_1:
					case BUTTON_2:
					case BUTTON_3:
					case BUTTON_4:
					case BUTTON_5:
					case BUTTON_6:
					case BUTTON_7:
					case BUTTON_8:
					case BUTTON_9:
					case BUTTON_C:
					case BUTTON_E:
						// TODO: logic
						break;
				}
				return;
			}
		}
	}

	public void setDimension(int width, int height) {
		synchronized(this.holder) {
			this.dBackground.setDimension(new RectF(0.0f, 0.0f, width, height));
			float balloonsLeft = 0.0f;
			float balloonsTop = 0.0f;
			float balloonsRight = width;
			float balloonsBottom = height * 0.5f;
			this.dBalloons.setDimension(new RectF(balloonsLeft, balloonsTop, balloonsRight, balloonsBottom));
			float clockSize = width * 0.1f;
			float clockLeft = width - clockSize;
			float clockTop = 0.0f;
			float clockRight = width;
			float clockBottom = clockSize;
			this.dClock.setDimension(new RectF(clockLeft, clockTop, clockRight, clockBottom));
			float scoreLeft = 0.0f;
			float scoreTop = 0.0f;
			float scoreRight = width - clockSize;
			float scoreBottom = height * 0.04f;
			this.dScore.setDimension(new RectF(scoreLeft, scoreTop, scoreRight, scoreBottom));
			float correctSize = (balloonsRight - balloonsLeft) * 0.5f;
			float correctLeft = (balloonsRight - balloonsLeft - correctSize) * 0.5f;
			float correctTop = (balloonsBottom - balloonsTop - clockSize) * 0.5f;
			float correctRight = correctLeft + correctSize;
			float correctBottom = correctTop + correctSize;
			this.dCorrect.setDimension(new RectF(correctLeft, correctTop, correctRight, correctBottom));
			this.dIncorrect.setDimension(new RectF(correctLeft, correctTop, correctRight, correctBottom));
			float displayLeft = width * 0.019f;
			float displayTop = height * 0.5f;
			float displayRight = width * 0.981f;
			float displayBottom = height * 0.6f;
			this.dDisplay.setDimension(new RectF(displayLeft, displayTop, displayRight, displayBottom));
			float buttonsLeft = 0.0f;
			float buttonsTop = height * 0.6f;
			float buttonsRight = width;
			float buttonsBottom = height;
			this.dButtons.setDimension(new RectF(buttonsLeft, buttonsTop, buttonsRight, buttonsBottom));
			this.dReady.setDimension(new RectF(0.0f, 0.0f, width, height));
			this.dGameOver.setDimension(new RectF(0.0f, 0.0f, width, height));
		}
	}
	
	// private
	
	private void doUpdate() {
		
	}
	
	private void doDraw(Canvas canvas) {
		this.dBackground.draw(canvas);
		switch(this.gameState) {
			case READY:
				this.dReady.draw(canvas);
				break;
			case WORK:
				this.dBalloons.draw(canvas);
				this.dCorrect.draw(canvas);
				this.dIncorrect.draw(canvas);
				this.dScore.draw(canvas, this.gameResult.getCorrect());
				this.dClock.draw(canvas, BalloonInterpolator.i(this.gameStartedAt, GAME_DURATION));
			case PAUSE:
				this.dDisplay.draw(canvas, "10"); // TODO: logic
				this.dButtons.draw(canvas);
				break;
			case GAMEOVER:
				this.dGameOver.draw(canvas, this.gameResult.getCorrect());
				break;
		}
	}
}