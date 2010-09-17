/**
 * 
 */
package puzzle.parabens;

import java.util.Random;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Ticker;

import puzzle.foto.ManterFoto;
import puzzle.principal.PuzzleMIDlet;
import puzzle.ranking.AdicionarRanking;
import puzzle.ranking.Ranking;
import puzzle.util.DadosJogo;
import puzzle.util.ImagemUtil;
import puzzle.util.Mensagens;

/**
 * Responsavel pela tela de parabens.
 * 
 * @author David Almeida Pitanguy data 10/09/2010
 */
public class Parabens extends Canvas {

	private PuzzleMIDlet midlet;

	private static final int QUANTIDADE_PONTOS = 100;
	private static final int TAMANHO_PONTO = 3;

	private int coresPontos[] = { 0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00,
			0xFF00FF, 0x00FFFF, 0xFFFFFF };

	private PontoVoador pontos[] = new PontoVoador[QUANTIDADE_PONTOS];

	private boolean animar = true;

	private Ranking ranking;

	private ImagemUtil imagemUtil;
	private DadosJogo dadosJogo;
	private ManterFoto manterFoto;

	private Image foto;

	private long tempoJog;
	private long movimentosJog;

	public Parabens(PuzzleMIDlet midlet, long tempo, long movimentos) {

		this.midlet = midlet;
		this.midlet.setDisplayable(this);

		this.imagemUtil = new ImagemUtil();
		this.dadosJogo = new DadosJogo();
		this.manterFoto = new ManterFoto();

		this.setTicker(new Ticker(Mensagens.PARABENS));

		this.ranking = new Ranking(midlet);

		this.tempoJog = tempo;
		this.movimentosJog = movimentos;

		foto = imagemUtil.redimencionarImagem(
				manterFoto.carregarFoto(dadosJogo.getNumImagemSelecionada()),
				getWidth(), getHeight());

		inicializarPontos();

		animar();
	}

	/**
	 * Inicializa todos os pontos.
	 */
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

		g.drawImage(foto, getWidth() / 2, getHeight() / 2, Graphics.HCENTER
				| Graphics.VCENTER);

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

	/**
	 * Cuida da animação da tela de parabens.
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#keyPressed(int)
	 */
	protected void keyPressed(int keyCode) {

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
		iniciarMenu();
	}

	/**
	 * Passa para proxima tela, que é o menu
	 */
	private void iniciarMenu() {
		animar = false;

		if (ranking.isPodeAdicionar(this.movimentosJog)) {
			Display.getDisplay(this.midlet).setCurrent(
					new AdicionarRanking(this.midlet, this.tempoJog,
							this.movimentosJog));
		} else {
			Display.getDisplay(this.midlet)
					.setCurrent(new Ranking(this.midlet));
		}
	}

}
