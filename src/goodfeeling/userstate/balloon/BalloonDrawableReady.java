package goodfeeling.userstate.balloon;

import goodfeeling.gui.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

public class BalloonDrawableReady {
	
	private RectF dimension;
	
	private Paint paint;
	
	private String sPress;
	
	public BalloonDrawableReady(Context context) {
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setFakeBoldText(true);
		this.paint.setARGB(200, 0, 87, 144);
		this.paint.setTextAlign(Align.CENTER);
		this.sPress = context.getResources().getString(R.string.balloon_ready_press);
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
		this.paint.setTextSize(dimension.height() * 0.1f);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawText(this.sPress, this.dimension.centerX(), this.dimension.centerY(), this.paint);
	}
}
