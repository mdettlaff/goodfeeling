package goodfeeling.userstate.balloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BalloonDrawableDisplay {

	private Drawable texDisplay;

	private Paint paint;
	
	private float textX;
	private float textY;

	public BalloonDrawableDisplay(Context context) {
		this.texDisplay = context.getResources().getDrawable(R.drawable.balloon_display);
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setFakeBoldText(true);
		this.paint.setTextAlign(Align.CENTER);
		this.paint.setARGB(200, 0, 0, 0);
	}

	public void setDimension(RectF dimension) {
		this.texDisplay.setBounds(
			(int)dimension.left,
			(int)dimension.top,
			(int)dimension.right,
			(int)dimension.bottom);
		this.textX = dimension.centerX();
		this.textY = dimension.bottom - dimension.height() * 0.2f;
		this.paint.setTextSize(dimension.height() * 0.8f);
	}

	public void draw(Canvas canvas, String text) {
		this.texDisplay.draw(canvas);
		canvas.drawText(text, this.textX, this.textY, this.paint);
	}
}
