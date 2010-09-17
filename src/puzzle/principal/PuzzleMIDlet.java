package puzzle.principal;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import puzzle.inicio.TelaInicial;

/**
 * @author David Almeida Pitanguy data 06/09/2010
 */
public class PuzzleMIDlet extends MIDlet {

	private Displayable displayable;

	/**
	 * Construtor responsavel por definir a tela que ira aparecer.
	 */
	public PuzzleMIDlet() {
		displayable = new TelaInicial(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean flag) throws MIDletStateChangeException {

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
		Display.getDisplay(this).setCurrent(displayable);
	}

	/**
	 * Serve para tratar o pause.
	 * 
	 * @param canvas
	 *            Recebe o canvas que ira setar como principal ao voltar ao
	 *            jogo.
	 */
	public void setDisplayable(Displayable displayable) {
		this.displayable = displayable;
	}

}
