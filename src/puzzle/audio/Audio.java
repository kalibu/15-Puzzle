/**
 * 
 */
package puzzle.audio;

import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

/**
 * Cuida da parte de som do jogo.
 * 
 * @author David Almeida Pitanguy data 17/09/2010
 */
public class Audio implements Runnable {

	private Player player;

	private final String AUDIO = "/sons/bach.mid";
	private final String TIPO_AUDIO = "audio/midi";

	private boolean tocando = false;

	public Audio() {
		new Thread(this).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		try {
			InputStream in = getClass().getResourceAsStream(AUDIO);
			player = Manager.createPlayer(in, TIPO_AUDIO);
			player.setLoopCount(-1);
			player.prefetch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Start musica
	 */
	public void start() {
		try {
			player.start();
			this.tocando = true;
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Para musica
	 */
	public void stop() {
		try {
			player.stop();
			this.tocando = false;
		} catch (MediaException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the tocando
	 */
	public boolean isTocando() {
		return tocando;
	}
}
