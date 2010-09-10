/**
 * 
 */
package puzzle.parabens;

import java.util.Random;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Ticker;
import javax.microedition.midlet.MIDlet;

import puzzle.principal.Mensagens;
import puzzle.principal.Puzzle;

/**
 * Responsavel pela tela de parabens.
 * 
 * @author David Almeida Pitanguy data 10/09/2010
 */
public class Parabens extends Canvas implements CommandListener {

	private MIDlet midlet;

	private static final int QUANTIDADE_PONTOS = 100;
	private static final int TAMANHO_PONTO = 3;

	private int coresPontos[] = { 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00,
			0xFF00FF, 0x00FFFF, 0xFFFFFF };

	private PontoVoador pontos[] = new PontoVoador[QUANTIDADE_PONTOS];

	private int corFundo = 0x000000;

	private Command voltar;

	private boolean animar = true;

	public Parabens(MIDlet midlet) {
		this.midlet = midlet;

		voltar = new Command(Mensagens.VOLTAR, Command.SCREEN, 1);

		this.setTicker(new Ticker(Mensagens.PARABENS));

		this.addCommand(voltar);
		this.setCommandListener(this);

		inicializarPontos();

		animar();
	}

	private void inicializarPontos() {
		new Thread(new Runnable() {

			public void run() {

				Random r = new Random();

				for (int i = 0; i < pontos.length; i++) {
					pontos[i] = new PontoVoador(coresPontos[i
							% coresPontos.length], r.nextInt(getWidth()),
							getHeight(), getHeight());

					// dorme antes de inicializar o proximo ponto
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
					}
				}

			}
		}).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	protected void paint(Graphics g) {

		g.setColor(corFundo);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < pontos.length; i++) {
			if (pontos[i] != null) {
				g.setColor(pontos[i].cor);
				g.fillRect(pontos[i].x, pontos[i].y, TAMANHO_PONTO,
						TAMANHO_PONTO);
				pontos[i].atualizar();
			} else {
				break;
			}
		}
	}

	public void commandAction(Command c, Displayable d) {
		if (c == this.voltar) {
			animar = false;
			Display.getDisplay(this.midlet).setCurrent(new Puzzle(this.midlet));
		}
	}

	private void animar() {
		new Thread(new Runnable() {

			public void run() {
				while (animar) {
					repaint();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
	}

}
