package goodfeeling.util;

import java.awt.Color;

public class Picture {

	public static enum Category {
		HAPPY,
		SAD,
		NEUTRAL,
		;
	}

	public final Category category;
	// TODO zmienić z Color na obraz rastrowy
	public final Color image;

	public Picture(Category category, Color image) {
		this.category = category;
		this.image = image;
	}
}
