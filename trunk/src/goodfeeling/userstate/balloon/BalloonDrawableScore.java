package goodfeeling.userstate.balloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

public class BalloonDrawableScore {
	
	private RectF dimension;
	
	private Paint paint;

	public BalloonDrawableScore(Context context) {
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setFakeBoldText(true);
		this.paint.setARGB(150, 0, 87, 144);
		this.paint.setTextAlign(Align.LEFT);
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
		this.paint.setTextSize(dimension.height());
	}
	
	public void draw(Canvas canvas, int score) {
		canvas.drawText(String.format("Score: %02d", score),
			this.dimension.left,
			this.dimension.bottom,
			this.paint);
	}
}
