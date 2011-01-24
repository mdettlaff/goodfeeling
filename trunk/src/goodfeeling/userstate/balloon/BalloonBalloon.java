package goodfeeling.userstate.balloon;

import goodfeeling.userstate.balloon.BalloonDrawableBalloons.BalloonColor;
import goodfeeling.userstate.balloon.BalloonThread.Operation;
import android.graphics.RectF;

public class BalloonBalloon {

	private long createdAt;
	
	private long destroyedAt;
	
	private boolean is_destroyed;
	
	private RectF dimension;
	
	private Operation operation;
	private int firstArgument;
	private int secondArgument;
	
	private BalloonColor color;
	
	public BalloonBalloon(Operation operation, int firstArgument, int secondArgument, BalloonColor color) {
		this.createdAt = System.currentTimeMillis();
		this.destroyedAt = 0l;
		this.is_destroyed = false;
		this.operation = operation;
		this.firstArgument = firstArgument;
		this.secondArgument = secondArgument;
		this.color = color;
	}
	
	public RectF getDimension() {
		return this.dimension;
	}
	
	public void setDimension(RectF dimension) {
		this.dimension = dimension;
	}
	
	public long getCreatedAt() {
		return this.createdAt;
	}
	
	public long getDestroyedAt() {
		return this.destroyedAt;
	}
	
	public void destroy() {
		if(!this.is_destroyed) {
			this.destroyedAt = System.currentTimeMillis();
			this.is_destroyed = true;
		}
	}
	
	public boolean isDestroyed() {
		return this.is_destroyed;
	}
	
	public boolean toRemove() {
		return this.is_destroyed && BalloonInterpolator.i(this.destroyedAt, BalloonDrawableBalloons.ANIMATION_DESTROY) == 1.0f;
	}
	
	public Operation getOperation() {
		return this.operation;
	}
	
	public int getFirstArgument() {
		return this.firstArgument;
	}
	
	public int getSecondArgument() {
		return this.secondArgument;
	}
	
	public BalloonColor getColor() {
		return this.color;
	}
}
