package goodfeeling.userstate.balloon;

import goodfeeling.gui.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BalloonDrawableBackground {
	
	private Drawable texBackground;
	
	public BalloonDrawableBackground(Context context) {
		this.texBackground = context.getResources().getDrawable(R.drawable.balloon_bg);
	}
	
	public void setDimension(RectF dimension) {
		this.texBackground.setBounds(
			(int)dimension.left,
			(int)dimension.top,
			(int)dimension.right,
			(int)dimension.bottom);
	}
	
	public void draw(Canvas canvas) {
		this.texBackground.draw(canvas);
	}
}
