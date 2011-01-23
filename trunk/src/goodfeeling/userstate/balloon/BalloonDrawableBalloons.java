package goodfeeling.userstate.balloon;

import java.util.LinkedList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BalloonDrawableBalloons {
	
	public enum BalloonColor {
		RED,
		GREEN,
		BLUE,
		YELLOW
	}
	
	private final int ANIMATION_CREATE = 1000; // in milliseconds
	private final int ANIMATION_DESTROY = 500; // in milliseconds
	
	private RectF dimension = null;
	
	private LinkedList<BalloonBalloon> balloons;
	
	private Drawable[] texBalloons;
	private Drawable[] texOperation;
	
	private Paint paint;
	
	private float balloonWidth;
	private float balloonHeight;
	
	private float operationWidth;
	private float operationHeight;
	
	private float operationOffsetX;
	private float operationOffsetY;
	
	private float firstArgumentOffsetX;
	private float firstArgumentOffsetY;
	private float secondArgumentOffsetX;
	private float secondArgumentOffsetY;
	
	private int alpha;

	public BalloonDrawableBalloons(Context context, LinkedList<BalloonBalloon> balloons) {
		this.texBalloons = new Drawable[4];
		this.texBalloons[0] = context.getResources().getDrawable(R.drawable.balloon_red);
		this.texBalloons[1] = context.getResources().getDrawable(R.drawable.balloon_green);
		this.texBalloons[2] = context.getResources().getDrawable(R.drawable.balloon_blue);
		this.texBalloons[3] = context.getResources().getDrawable(R.drawable.balloon_yellow);
		this.texOperation = new Drawable[4];
		this.texOperation[0] = context.getResources().getDrawable(R.drawable.balloon_op_add);
		this.texOperation[1] = context.getResources().getDrawable(R.drawable.balloon_op_sub);
		this.texOperation[2] = context.getResources().getDrawable(R.drawable.balloon_op_multi);
		this.texOperation[3] = context.getResources().getDrawable(R.drawable.balloon_op_div);
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setFakeBoldText(true);
		this.paint.setTextAlign(Align.CENTER);
		this.paint.setColor(Color.WHITE);
		this.balloons = balloons;
	}
	
	public void setDimension(RectF dimension) {
		RectF prevDimension = dimension;
		this.dimension = dimension;
		this.balloonWidth = dimension.width() * 0.15f;
		this.balloonHeight = this.balloonWidth * 1.628f;
		this.operationHeight = this.balloonHeight * 0.25f;
		this.operationWidth = this.operationHeight;
		this.operationOffsetX = 0.0f;
		this.operationOffsetY = this.balloonHeight * 0.25f;
		this.firstArgumentOffsetX = this.balloonWidth * 0.63f;
		this.firstArgumentOffsetY = this.balloonHeight * 0.34f;
		this.secondArgumentOffsetX = this.firstArgumentOffsetX;
		this.secondArgumentOffsetY = this.balloonHeight * 0.655f;
		this.paint.setTextSize(this.balloonHeight * 0.315f);
		if(prevDimension != null)
			for(BalloonBalloon b: this.balloons) {
				float left = b.getDimension().left / b.getDimension().width() * dimension.width();
				float top = b.getDimension().top / b.getDimension().height() * dimension.height();
				b.setDimension(new RectF(left, top, left + this.balloonWidth,	top + this.balloonHeight));
			}
	}
	
	public void draw(Canvas canvas) {
		for(BalloonBalloon b: this.balloons) {			
			if(b.isDestroyed())
				this.alpha = (int)(255.0f * (1.0f - BalloonInterpolator.i(b.getDestroyedAt(), ANIMATION_DESTROY)));
			else
				this.alpha = (int)(255.0f * BalloonInterpolator.i(b.getCreatedAt(), ANIMATION_CREATE));
			switch(b.getColor()) {
				case RED:
					this.texBalloons[0].setBounds(
							(int)b.getDimension().left,
							(int)b.getDimension().top,
							(int)b.getDimension().right,
							(int)b.getDimension().bottom);
					this.texBalloons[0].setAlpha(this.alpha);
					this.texBalloons[0].draw(canvas);
					break;
				case GREEN:
					this.texBalloons[1].setBounds(
							(int)b.getDimension().left,
							(int)b.getDimension().top,
							(int)b.getDimension().right,
							(int)b.getDimension().bottom);
					this.texBalloons[1].setAlpha(this.alpha);
					this.texBalloons[1].draw(canvas);
					break;
				case BLUE:
					this.texBalloons[2].setBounds(
							(int)b.getDimension().left,
							(int)b.getDimension().top,
							(int)b.getDimension().right,
							(int)b.getDimension().bottom);
					this.texBalloons[2].setAlpha(this.alpha);
					this.texBalloons[2].draw(canvas);
					break;
				case YELLOW:
					this.texBalloons[3].setBounds(
							(int)b.getDimension().left,
							(int)b.getDimension().top,
							(int)b.getDimension().right,
							(int)b.getDimension().bottom);
					this.texBalloons[3].setAlpha(this.alpha);
					this.texBalloons[3].draw(canvas);
					break;
			}
			switch(b.getOperation()) {
			case ADDITION:
				this.texOperation[0].setBounds(
					(int)(b.getDimension().left + this.operationOffsetX),
					(int)(b.getDimension().top + this.operationOffsetY),
					(int)(b.getDimension().left + this.operationOffsetX + this.operationWidth),
					(int)(b.getDimension().top + this.operationOffsetY + this.operationOffsetY));
				this.texOperation[0].setAlpha(this.alpha);
				this.texOperation[0].draw(canvas);
				break;
			case SUBTRACTION:
				this.texOperation[1].setBounds(
					(int)(b.getDimension().left + this.operationOffsetX),
					(int)(b.getDimension().top + this.operationOffsetY),
					(int)(b.getDimension().left + this.operationOffsetX + this.operationWidth),
					(int)(b.getDimension().top + this.operationOffsetY + this.operationOffsetY));
				this.texOperation[1].setAlpha(this.alpha);
				this.texOperation[1].draw(canvas);
				break;
			case MULTIPLICATION:
				this.texOperation[2].setBounds(
					(int)(b.getDimension().left + this.operationOffsetX),
					(int)(b.getDimension().top + this.operationOffsetY),
					(int)(b.getDimension().left + this.operationOffsetX + this.operationWidth),
					(int)(b.getDimension().top + this.operationOffsetY + this.operationOffsetY));
				this.texOperation[2].setAlpha(this.alpha);
				this.texOperation[2].draw(canvas);
				break;
			case DIVISION:
				this.texOperation[3].setBounds(
					(int)(b.getDimension().left + this.operationOffsetX),
					(int)(b.getDimension().top + this.operationOffsetY),
					(int)(b.getDimension().left + this.operationOffsetX + this.operationWidth),
					(int)(b.getDimension().top + this.operationOffsetY + this.operationOffsetY));
				this.texOperation[3].setAlpha(this.alpha);
				this.texOperation[3].draw(canvas);
				break;
			}
			this.paint.setAlpha(this.alpha);
			canvas.drawText(String.format("%d", b.getFirstArgument()),
				b.getDimension().left + this.firstArgumentOffsetX,
				b.getDimension().left + this.firstArgumentOffsetY,
				this.paint);
			canvas.drawText(String.format("%d", b.getSecondArgument()),
					b.getDimension().left + this.secondArgumentOffsetX,
					b.getDimension().left + this.secondArgumentOffsetY,
					this.paint);
		}
	}
	
	public RectF getDimension() {
		return this.dimension;
	}
	
	public float getBalloonWidth() {
		return this.balloonWidth;
	}
	
	public float getBalloonHeight() {
		return this.balloonHeight;
	}
}
