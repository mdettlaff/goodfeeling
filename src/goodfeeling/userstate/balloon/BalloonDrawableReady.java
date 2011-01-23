package goodfeeling.userstate.balloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

public class BalloonDrawableReady {
	
	private RectF dimension;
	
	private Paint paint;
	
	public BalloonDrawableReady(Context context) {
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setFakeBoldText(true);
		this.paint.setARGB(200, 0, 87, 144);
		this.paint.setTextAlign(Align.CENTER);
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
		this.paint.setTextSize(dimension.height() * 0.1f);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawText("Press to start", this.dimension.centerX(), this.dimension.centerY(), this.paint);
	}
}
