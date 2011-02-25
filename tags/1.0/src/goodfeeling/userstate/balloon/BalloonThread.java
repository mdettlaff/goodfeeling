package goodfeeling.userstate.balloon;

import goodfeeling.userstate.balloon.BalloonDrawableBalloons.BalloonColor;
import java.util.LinkedList;
import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Handler;
import android.view.SurfaceHolder;

public class BalloonThread extends Thread {
	/*
	 * thread sleep time, in milliseconds
	 */
	private final long THREAD_INTERVAL = 10L;
	/*
	 * thread sleep time when it is in idle mode, in milliseconds
	 */
	private final long THREAD_INTERVAL_IDLE = 200L;
	/*
	 * duration of the game, in milliseconds
	 */
	private final long GAME_DURATION = 45000L;
	/*
	 * maximum number of balloons at the same time
	 */
	private final int GAME_BALLOON_MAX = 3;
	/*
	 * percentage chance to create a new balloon, 0.0 - never, 1.0 - always
	 */
	private final double GAME_BALLOON_CHANCE = 0.05;
	/*
	 * time before the balloon flies away, in milliseconds
	 */
	private final long GAME_BALLOON_DURATION = 10000L;
	/*
	 * waiting time to finish the game
	 */
	private final long GAME_GAMEOVER_WAIT = 2000L;
	
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
	
	private Handler handler;
	
	private long threadInterval;
	
	private GameState gameState;
	
	private long gameStartedAt;
	
	private BalloonResult gameResult;
	
	private LinkedList<BalloonBalloon> balloons;
	
	private LinkedList<BalloonBalloon> balloonsToDestroy;
	
	private String gameEntry;
	
	private Random random;
	
	private long balloonCreateAt;
	
	private long gameOverAt;
	
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
		this.balloonsToDestroy = new LinkedList<BalloonBalloon>();
		this.gameEntry = "";
		this.random = new Random();
		this.balloonCreateAt = 0l;
		this.gameOverAt = 0l;
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
	
	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public GameState getGameState() {
		synchronized(this.holder) {
			return this.gameState;
		}
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
						this.gameOverAt = System.currentTimeMillis();
						this.gameState = GameState.GAMEOVER;
						this.threadInterval = THREAD_INTERVAL_IDLE;
					}
					return;
			}
		}
	}
	
	public BalloonResult getGameResult(){
		synchronized(this.holder) {
			return this.gameResult;
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
							gameEntryAdd(0);
							break;
						case BUTTON_1:
							gameEntryAdd(1);
							break;
						case BUTTON_2:
							gameEntryAdd(2);
							break;
						case BUTTON_3:
							gameEntryAdd(3);
							break;
						case BUTTON_4:
							gameEntryAdd(4);
							break;
						case BUTTON_5:
							gameEntryAdd(5);
							break;
						case BUTTON_6:
							gameEntryAdd(6);
							break;
						case BUTTON_7:
							gameEntryAdd(7);
							break;
						case BUTTON_8:
							gameEntryAdd(8);
							break;
						case BUTTON_9:
							gameEntryAdd(9);
							break;
						case BUTTON_C:
							gameEntryClear();
							break;
						case BUTTON_E:
							gameEntryEnter();
							break;
					}
					return;
				case GAMEOVER:
					if(BalloonInterpolator.i(this.gameOverAt, GAME_GAMEOVER_WAIT) == BalloonInterpolator.END)
						this.handler.sendEmptyMessage(Balloon.MSG_EXIT);
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
			float correctLeft = balloonsLeft + (balloonsRight - balloonsLeft - correctSize) * 0.5f;
			float correctTop = balloonsTop + (balloonsBottom - balloonsTop - correctSize) * 0.5f;
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
	
	private void doDraw(Canvas canvas) {
		this.dBackground.draw(canvas);
		switch(this.gameState) {
			case READY:
				this.dReady.draw(canvas);
				break;
			case WORK:
				this.dCorrect.draw(canvas);
				this.dIncorrect.draw(canvas);
				this.dBalloons.draw(canvas);
				this.dScore.draw(canvas, this.gameResult.getCorrect());
				this.dClock.draw(canvas, BalloonInterpolator.i(this.gameStartedAt, GAME_DURATION));
			case PAUSE:
				this.dDisplay.draw(canvas, this.gameEntry);
				this.dButtons.draw(canvas);
				break;
			case GAMEOVER:
				this.dGameOver.draw(canvas, this.gameResult.getCorrect(),
					BalloonInterpolator.i(this.gameOverAt, GAME_GAMEOVER_WAIT));
				break;
		}
	}
	
	private void doUpdate() {
		if(this.gameState != GameState.WORK)
			return;
		if(BalloonInterpolator.i(this.gameStartedAt, GAME_DURATION) == BalloonInterpolator.END) {
			setGameState(GameState.GAMEOVER);
			return;
		}
		float i;
		for(BalloonBalloon b: this.balloons) {
			if(b.toRemove())
				this.balloonsToDestroy.add(b);
			else if(!b.isDestroyed()) {
				i = BalloonInterpolator.i(b.getCreatedAt(), GAME_BALLOON_DURATION);
				if(i == BalloonInterpolator.END)
					b.destroy();
				else
					b.getDimension().offsetTo(
						b.getDimension().left,
						this.dBalloons.getDimension().bottom -
							i * this.dBalloons.getDimension().height());
			}
		}
		for(BalloonBalloon b: this.balloonsToDestroy)
			this.balloons.remove(b);
		this.balloonsToDestroy.clear();
		if(this.balloons.isEmpty())
			balloonCreate();
		else if(this.balloons.size() < GAME_BALLOON_MAX &&
			BalloonInterpolator.i(this.balloonCreateAt, GAME_BALLOON_DURATION / 3) == BalloonInterpolator.END &&
			this.random.nextDouble() < GAME_BALLOON_CHANCE)
				balloonCreate();
			
	}
	
	private void balloonCreate() {
		Operation op;
		int fArg;
		int sArg;
		BalloonColor color;
		switch(this.random.nextInt(4)) {
			case 1:
				op = Operation.SUBTRACTION;
				color = BalloonColor.GREEN;
				fArg = this.random.nextInt(10) + 1;
				sArg = this.random.nextInt(fArg + 1);
				break;
			case 2:
				op = Operation.MULTIPLICATION;
				color = BalloonColor.BLUE;
				fArg = this.random.nextInt(11);
				sArg = this.random.nextInt(11);
				break;
			case 3:
				op = Operation.DIVISION;
				color = BalloonColor.YELLOW;
				sArg = this.random.nextInt(10) + 1;
				fArg = this.random.nextInt(11) * sArg;
				break;
			default:
				op = Operation.ADDITION;
				color = BalloonColor.RED;
				fArg = this.random.nextInt(21);
				sArg = this.random.nextInt(21);
				break;
		}
		BalloonBalloon b = new BalloonBalloon(op, fArg, sArg, color);
		this.balloonCreateAt = System.currentTimeMillis();
		RectF dim = new RectF();
		dim.top = this.dBalloons.getDimension().bottom;
		dim.bottom = dim.top + this.dBalloons.getBalloonHeight();
		dim.left = this.dBalloons.getDimension().left + this.dBalloons.getBalloonWidth() * 0.25f +
			(this.dBalloons.getDimension().width() - this.dBalloons.getBalloonWidth() * 1.5f) * this.random.nextFloat();
		dim.right = dim.left + this.dBalloons.getBalloonWidth();
		b.setDimension(dim);
		this.balloons.add(b);
		this.gameResult.incAll();
	}
	
	private void gameEntryAdd(int entry) {
		if(entry < 0 || entry > 9)
			return;
		this.gameEntry = this.gameEntry + entry;
	}
	
	private void gameEntryEnter() {
		if(this.gameEntry.length() == 0)
			return;
		int entry = Integer.parseInt(this.gameEntry);
		boolean wasCorrect = false;
		for(BalloonBalloon b: this.balloons) {
			if(b.isDestroyed())
				continue;
			switch(b.getOperation()) {
			case ADDITION:
				if(b.getFirstArgument() + b.getSecondArgument() == entry) {
					this.gameResult.incCorrect();
					wasCorrect = true;
					b.destroy();
				}
				break;
			case SUBTRACTION:
				if(b.getFirstArgument() - b.getSecondArgument() == entry) {
					this.gameResult.incCorrect();
					wasCorrect = true;
					b.destroy();
				}
				break;
			case MULTIPLICATION:
				if(b.getFirstArgument() * b.getSecondArgument() == entry) {
					this.gameResult.incCorrect();
					wasCorrect = true;
					b.destroy();
				}
				break;
			case DIVISION:
				if(b.getFirstArgument() / b.getSecondArgument() == entry) {
					this.gameResult.incCorrect();
					wasCorrect = true;
					b.destroy();
				}
				break;
			}
		}
		if(wasCorrect)
			this.dCorrect.correct();
		else {
			this.gameResult.incIncorrect();
			this.dIncorrect.incorrect();
		}
		gameEntryClear();
	}
	
	private void gameEntryClear() {
		this.gameEntry = "";
	}
}