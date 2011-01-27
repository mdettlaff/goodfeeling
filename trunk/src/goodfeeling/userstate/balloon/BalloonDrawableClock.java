package goodfeeling.userstate.balloon;

import goodfeeling.gui.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BalloonDrawableClock {
	
	private RectF dimension;
	
	private Drawable[] texClock;
	
	private int frame;
	
	public BalloonDrawableClock(Context context) {
		this.texClock = new Drawable[13];
		this.texClock[0] = context.getResources().getDrawable(R.drawable.balloon_clock_00);
		this.texClock[1] = context.getResources().getDrawable(R.drawable.balloon_clock_01);
		this.texClock[2] = context.getResources().getDrawable(R.drawable.balloon_clock_02);
		this.texClock[3] = context.getResources().getDrawable(R.drawable.balloon_clock_03);
		this.texClock[4] = context.getResources().getDrawable(R.drawable.balloon_clock_04);
		this.texClock[5] = context.getResources().getDrawable(R.drawable.balloon_clock_05);
		this.texClock[6] = context.getResources().getDrawable(R.drawable.balloon_clock_06);
		this.texClock[7] = context.getResources().getDrawable(R.drawable.balloon_clock_07);
		this.texClock[8] = context.getResources().getDrawable(R.drawable.balloon_clock_08);
		this.texClock[9] = context.getResources().getDrawable(R.drawable.balloon_clock_09);
		this.texClock[10] = context.getResources().getDrawable(R.drawable.balloon_clock_10);
		this.texClock[11] = context.getResources().getDrawable(R.drawable.balloon_clock_11);
		this.texClock[12] = context.getResources().getDrawable(R.drawable.balloon_clock_12);
		this.frame = 0;
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
	}
	
	public void draw(Canvas canvas, float i) {
		this.frame = (int)(12.0f * i);			
		this.texClock[this.frame].setBounds(
			(int)this.dimension.left,
			(int)this.dimension.top,
			(int)this.dimension.right,
			(int)this.dimension.bottom);
		this.texClock[this.frame].draw(canvas);		
	}
}
