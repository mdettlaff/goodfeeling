package goodfeeling.userstate.balloon;

public class BalloonInterpolator {
	public static final float BEGIN = 0.0f;
	public static final float END = 1.0f;
	private static boolean is_paused = false;
	private static long pausedAt = 0l;
	private static long resumedAt = 0l;
	private static long currentTime;
	private static long dTime;
	
	public static void pause() {
		if(!is_paused) {
			is_paused = true;
			pausedAt = System.currentTimeMillis();
		}
	}
	
	public static void resume() {
		if(is_paused) {
			is_paused = false;
			resumedAt = System.currentTimeMillis();
		}
	}
	
	public static float i(long startAt, long duration) {
		currentTime = System.currentTimeMillis();
		if(startAt >= currentTime || startAt < 0l || duration <= 0l)
			return BEGIN;
		if(startAt == 0l)
			return END;
		if(is_paused)
			dTime = pausedAt - startAt;
		else
			if(startAt >= resumedAt)
				dTime = currentTime - startAt;
			else
				dTime = currentTime - startAt - (resumedAt - pausedAt);
		if(dTime >= duration)
			return END;
		return (float)dTime / duration;
	}
}
