package puzzle.principal;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import puzzle.inicio.Inicio;

/**
 * @author David Almeida Pitanguy data 06/09/2010
 */
public class PuzzleMIDlet extends MIDlet {

	private Canvas canvas;

	/**
	 * Construtor responsavel por definir a tela que ira aparecer.
	 */
	public PuzzleMIDlet() {
		canvas = new Inicio(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(canvas);
	}

}
