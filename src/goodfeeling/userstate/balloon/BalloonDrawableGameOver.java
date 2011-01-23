package goodfeeling.userstate.balloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Align;

public class BalloonDrawableGameOver {
	
	private RectF dimension;
	
	private Paint paint;
	
	private float fontSize1;
	private float fontSize2;
	private float fontSize3;
	
	public BalloonDrawableGameOver(Context context) {
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setFakeBoldText(true);
		this.paint.setARGB(200, 0, 87, 144);
		this.paint.setTextAlign(Align.CENTER);
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
		this.fontSize1 = dimension.height() * 0.1f;
		this.fontSize2 = dimension.height() * 0.07f;
		this.fontSize3 = dimension.height() * 0.055f;
		
	}
	
	public void draw(Canvas canvas, int score) {
		this.paint.setTextSize(this.fontSize1);
		canvas.drawText("Game Over",
			this.dimension.centerX(),
			this.dimension.centerY() - this.fontSize2,
			this.paint);
		this.paint.setTextSize(this.fontSize2);
		canvas.drawText(String.format("Score: %02d", score),
			this.dimension.centerX(),
			this.dimension.centerY(),
			this.paint);
		this.paint.setTextSize(this.fontSize3);
		canvas.drawText("Congratulations!",
				this.dimension.centerX(),
				this.dimension.centerY() + this.fontSize3,
				this.paint);
	}
}
