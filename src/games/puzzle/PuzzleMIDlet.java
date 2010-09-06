package games.puzzle;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class PuzzleMIDlet extends MIDlet {

	private Canvas canvas;

	public PuzzleMIDlet() {
		canvas = new PuzzleCanvas(this);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

	}

	protected void pauseApp() {

	}

	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(canvas);
	}

}
