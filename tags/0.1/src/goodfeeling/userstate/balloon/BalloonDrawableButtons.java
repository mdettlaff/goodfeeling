package goodfeeling.userstate.balloon;

import goodfeeling.gui.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public class BalloonDrawableButtons {
	public static enum ButtonsId {
		BUTTON_1,
		BUTTON_2,
		BUTTON_3,
		BUTTON_4,
		BUTTON_5,
		BUTTON_6,
		BUTTON_7,
		BUTTON_8,
		BUTTON_9,
		BUTTON_0,
		BUTTON_C,
		BUTTON_E,
		BUTTON_NON
	}
	
	private final int ANIMATION_PRESSED = 500; // in milliseconds
	
	private final int BUTTON_NUM = 12;

	private final int BUTTON_IN_ROW = 3;
	
	private RectF dimension;
	
	private Drawable texButton;
	
	private Paint paint;
	
	private BalloonButton[] buttons;
	
	private int alpha;
	
	public BalloonDrawableButtons(Context context) {
		this.texButton = context.getResources().getDrawable(R.drawable.balloon_button);
		this.buttons = new BalloonButton[BUTTON_NUM];
		this.buttons[0] = new BalloonButton(ButtonsId.BUTTON_1,
			context.getResources().getString(R.string.balloon_button_label_1));
		this.buttons[1] = new BalloonButton(ButtonsId.BUTTON_2,
			context.getResources().getString(R.string.balloon_button_label_2));
		this.buttons[2] = new BalloonButton(ButtonsId.BUTTON_3,
			context.getResources().getString(R.string.balloon_button_label_3));
		this.buttons[3] = new BalloonButton(ButtonsId.BUTTON_4,
			context.getResources().getString(R.string.balloon_button_label_4));
		this.buttons[4] = new BalloonButton(ButtonsId.BUTTON_5,
			context.getResources().getString(R.string.balloon_button_label_5));
		this.buttons[5] = new BalloonButton(ButtonsId.BUTTON_6,
			context.getResources().getString(R.string.balloon_button_label_6));
		this.buttons[6] = new BalloonButton(ButtonsId.BUTTON_7,
			context.getResources().getString(R.string.balloon_button_label_7));
		this.buttons[7] = new BalloonButton(ButtonsId.BUTTON_8,
			context.getResources().getString(R.string.balloon_button_label_8));
		this.buttons[8] = new BalloonButton(ButtonsId.BUTTON_9,
			context.getResources().getString(R.string.balloon_button_label_9));
		this.buttons[9] = new BalloonButton(ButtonsId.BUTTON_0,
			context.getResources().getString(R.string.balloon_button_label_0));
		this.buttons[10] = new BalloonButton(ButtonsId.BUTTON_C,
			context.getResources().getString(R.string.balloon_button_label_C));
		this.buttons[11] = new BalloonButton(ButtonsId.BUTTON_E,
			context.getResources().getString(R.string.balloon_button_label_E));
		this.paint = new Paint();
		this.paint.setAntiAlias(true);
		this.paint.setFakeBoldText(true);
		this.paint.setTextAlign(Align.CENTER);
		this.paint.setARGB(255, 255, 255, 255);
	}
	
	public ButtonsId onPressed(float x, float y) {
		if(this.dimension.contains(x, y)) {
			for(BalloonButton b : this.buttons)
				if(b.getDimension().contains(x, y)) {
					b.press();
					return b.getButtonId();
				}
		}
		return ButtonsId.BUTTON_NON;
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
		int row_num = (int)Math.ceil((double)BUTTON_NUM / (double)BUTTON_IN_ROW);
		float paddingX = dimension.width() * 0.02f;
		float paddingY = paddingX;
		float width = (dimension.width() - paddingX * (BUTTON_IN_ROW + 1)) / BUTTON_IN_ROW;
		float height = (dimension.height() - paddingY * (row_num + 1)) / row_num;
		int row = 0;
		int col = 0;
		for(BalloonButton b : this.buttons) {
			if(col >= BUTTON_IN_ROW) {
				col = 0;
				row++;
			}
			float left = this.dimension.left + (paddingX + width) * col + paddingX;
			float top = this.dimension.top + (paddingY + height) * row + paddingY;
			b.setDimension(new RectF(left, top, left + width, top + height));
			col++;
		}
		this.paint.setTextSize(height * 0.8f);
	}
	
	public void draw(Canvas canvas) {
		for(BalloonButton b : this.buttons) {
			this.alpha = 150 + (int)(105.0f * BalloonInterpolator.i(b.getPressedAt(), ANIMATION_PRESSED));
			this.texButton.setAlpha(this.alpha);
			this.texButton.setBounds(
				(int)b.getDimension().left,
				(int)b.getDimension().top,
				(int)b.getDimension().right,
				(int)b.getDimension().bottom);
			this.texButton.draw(canvas);
			this.paint.setAlpha(this.alpha);
			canvas.drawText(
				b.getLabel(),
				b.getDimension().centerX(),
				b.getDimension().bottom - b.getDimension().height() * 0.2f,
				this.paint);
		}
	}
}
