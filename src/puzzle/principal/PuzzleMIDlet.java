package puzzle.principal;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import puzzle.audio.Audio;
import puzzle.inicio.TelaInicial;

/**
 * Classe responsavel pelo MIDlet que o processo que roda no celular.
 * 
 * @author David Almeida Pitanguy data 06/09/2010
 */
public class PuzzleMIDlet extends MIDlet {

	private Displayable displayable;
	private Audio audio;

	/**
	 * Construtor responsavel por definir a tela que ira aparecer.
	 */
	public PuzzleMIDlet() {
		displayable = new TelaInicial(this);
		audio = new Audio();
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
		if (audio.isTocando()) {
			audio.stop();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {
		Display.getDisplay(this).setCurrent(displayable);
		if (audio.isTocando()) {
			audio.start();
		}
	}

	/**
	 * Serve para tratar o pause.
	 * 
	 * @param displayable
	 *            Recebe o canvas que ira setar como principal ao voltar ao
	 *            jogo.
	 */
	public void setDisplayable(Displayable displayable) {
		this.displayable = displayable;
	}

	/**
	 * @return the audio
	 */
	public Audio getAudio() {
		return audio;
	}
}
