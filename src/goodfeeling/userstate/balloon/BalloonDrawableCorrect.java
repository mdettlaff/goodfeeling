package goodfeeling.userstate.balloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BalloonDrawableCorrect {

	private final long ANIMATION_CORRECT = 1000l; // in milliseconds
	
	private Drawable texCorrect;
	
	private long correctedAt;
	
	private int alpha;
	
	public BalloonDrawableCorrect(Context context) {
		this.texCorrect = context.getResources().getDrawable(R.drawable.balloon_correct);
		this.correctedAt = 0l;
	}
	
	public void setDimension(RectF dimension) {
		this.texCorrect.setBounds(
			(int)dimension.left,
			(int)dimension.top,
			(int)dimension.right,
			(int)dimension.bottom);
	}
	
	public void draw(Canvas canvas) {
		this.alpha = (int)(200.0f * (1.0f - BalloonInterpolator.i(this.correctedAt, ANIMATION_CORRECT)));
		this.texCorrect.setAlpha(this.alpha);
		this.texCorrect.draw(canvas);
	}
	
	public void correct() {
		this.correctedAt = System.currentTimeMillis();
	}
}
