package goodfeeling.userstate.balloon;

import android.graphics.RectF;
import goodfeeling.userstate.balloon.BalloonDrawableButtons.ButtonsId;

public class BalloonButton {
	
	private RectF dimension;
	
	private ButtonsId buttonId;
	
	private String label;
	
	private long pressedAt;
	
	public BalloonButton(ButtonsId buttonId, String label) {
		this.dimension = new RectF();
		this.buttonId = buttonId;
		this.label = label;
		this.pressedAt = 0l;
	}
	
	public RectF getDimension() {
		return this.dimension;
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
	}
	
	public ButtonsId getButtonId() {
		return this.buttonId;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public long getPressedAt() {
		return this.pressedAt;
	}
	
	public void press() {
		this.pressedAt = System.currentTimeMillis();
	}
}
