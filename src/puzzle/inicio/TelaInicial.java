/**
 * 
 */
package puzzle.inicio;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import puzzle.menu.Menu;
import puzzle.principal.PuzzleMIDlet;
import puzzle.util.ImagemUtil;
import puzzle.util.Imagens;
import puzzle.util.Mensagens;

/**
 * Cuida da tela inicial.
 * 
 * @author David Almeida Pitanguy data 10/09/2010
 */
public class TelaInicial extends Canvas implements CommandListener {

	private PuzzleMIDlet midlet;

	private Command bSomOn;
	private Command bSomOff;

	private Image fundo;
	private Image somOn;
	private Image somOff;

	private Imagens imagens;
	private ImagemUtil imagemUtil;

	private int largAltImgSom = getWidth() / 10;

	private boolean selecionarSom = true;

	/**
	 * @param midlet
	 */
	public TelaInicial(PuzzleMIDlet midlet) {
		this.midlet = midlet;

		this.imagens = new Imagens();
		this.imagemUtil = new ImagemUtil();

		try {
			fundo = Image.createImage(imagens.getCaminhoImagem(Imagens.FUNDO));
			somOn = Image.createImage(imagens
					.getCaminhoImagem(Imagens.AUDIO_ON));
			somOff = Image.createImage(imagens
					.getCaminhoImagem(Imagens.AUDIO_OFF));

			fundo = imagemUtil.redimencionarImagem(fundo, getWidth(),
					getHeight());
			somOn = imagemUtil.redimencionarImagem(somOn, largAltImgSom,
					largAltImgSom);
			somOff = imagemUtil.redimencionarImagem(somOff, largAltImgSom,
					largAltImgSom);

			bSomOn = new Command(Mensagens.LIGADO, Command.EXIT, 1);
			bSomOff = new Command(Mensagens.DESLIGADO, Command.SCREEN, 2);

			this.addCommand(bSomOn);
			this.addCommand(bSomOff);
			this.setCommandListener(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	protected void paint(Graphics g) {
		g.drawImage(fundo, getWidth() / 2, getHeight() / 2, Graphics.HCENTER
				| Graphics.VCENTER);

		if (selecionarSom) {

			g.drawImage(somOn, 0, getHeight(), Graphics.LEFT | Graphics.BOTTOM);
			g.drawImage(somOff, getWidth(), getHeight(), Graphics.RIGHT
					| Graphics.BOTTOM);

		} else {
			g.drawString(Mensagens.START, getWidth() / 2, getHeight(),
					Graphics.HCENTER | Graphics.BOTTOM);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#keyPressed(int)
	 */
	protected void keyPressed(int keyCode) {
		if (!selecionarSom)
			if ((keyCode == Canvas.KEY_NUM5)
					|| (getGameAction(keyCode) == Canvas.FIRE)) {
				iniciarMenu();
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#pointerPressed(int, int)
	 */
	protected void pointerPressed(int x, int y) {
		if (selecionarSom) {
			if ((x <= largAltImgSom) && (y >= getHeight() - largAltImgSom)) {
				setarEstadoSom(true);
			} else if ((x >= getWidth() - largAltImgSom)
					&& (y >= getHeight() - largAltImgSom)) {
				setarEstadoSom(false);
			}
		} else {
			iniciarMenu();
		}
	}

	/**
	 * Passa para proxima tela, que é o menu
	 */
	private void iniciarMenu() {
		Display.getDisplay(this.midlet).setCurrent(new Menu(this.midlet));
	}

	/**
	 * @param ligado
	 *            Estado para ligar ou ñ o som
	 */
	private void setarEstadoSom(boolean ligado) {
		if (ligado) {
			this.midlet.getAudio().start();
		}
		selecionarSom = false;

		this.removeCommand(bSomOn);
		this.removeCommand(bSomOff);

		repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.CommandListener#commandAction(javax.microedition
	 * .lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {
		if (selecionarSom) {
			if (c == bSomOn) {
				setarEstadoSom(true);
			} else if (c == bSomOff) {
				setarEstadoSom(false);
			}
		}
	}
}
