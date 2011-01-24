package goodfeeling.userstate.balloon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BalloonDrawableIncorrect {
	private final long ANIMATION_INCORRECT = 1000l; // in milliseconds
	
	private Drawable texIncorrect;
	
	private long incorrectedAt;
	
	private int alpha;
	
	public BalloonDrawableIncorrect(Context context) {
		this.texIncorrect = context.getResources().getDrawable(R.drawable.balloon_incorrect);
		this.incorrectedAt = 0l;
	}
	
	public void setDimension(RectF dimension) {
		this.texIncorrect.setBounds(
			(int)dimension.left,
			(int)dimension.top,
			(int)dimension.right,
			(int)dimension.bottom);
	}
	
	public void draw(Canvas canvas) {
		this.alpha = (int)(200.0f * (1.0f - BalloonInterpolator.i(this.incorrectedAt, ANIMATION_INCORRECT)));
		this.texIncorrect.setAlpha(this.alpha);
		this.texIncorrect.draw(canvas);
	}
	
	public void incorrect() {
		this.incorrectedAt = System.currentTimeMillis();
	}
}
