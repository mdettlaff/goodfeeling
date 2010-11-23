package goodfeeling;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;

public class GoodFeelingMIDlet extends MIDlet {

	private Display display;

	public GoodFeelingMIDlet() {
		display = Display.getDisplay(this);
	}

	@Override
	public void startApp() {
		StringItem greeting = new StringItem(null,
				"Witaj w programie Good Feeling, drogi użytkowniku!");
		Form form = new Form(null, new Item[] {greeting});
		display.setCurrent(form);
	}

	@Override
	public void pauseApp() {
	}

	@Override
	public void destroyApp(boolean unconditional) {
	}
}
