package goodfeeling.userstate.balloon;

import goodfeeling.gui.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;

public class BalloonDrawableScore {
	
	private RectF dimension;
	
	private Paint paint;
	
	private String sScore;

	public BalloonDrawableScore(Context context) {
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setFakeBoldText(true);
		this.paint.setARGB(150, 0, 87, 144);
		this.paint.setTextAlign(Align.LEFT);
		this.sScore = context.getResources().getString(R.string.balloon_score);
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
		this.paint.setTextSize(dimension.height());
	}
	
	public void draw(Canvas canvas, int score) {
		canvas.drawText(String.format("%s: %02d", this.sScore, score),
			this.dimension.left,
			this.dimension.bottom,
			this.paint);
	}
}
