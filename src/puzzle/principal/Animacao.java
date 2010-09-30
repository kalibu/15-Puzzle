/**
 * 
 */
package puzzle.principal;

import javax.microedition.lcdui.Canvas;

/**
 * Responsavel pela animação do jogo.
 * 
 * @author David Almeida Pitanguy data 30/09/2010
 */
public class Animacao {

	private final int QTD_VEZES_ANIMAR = 10;
	private int qtdVezesAnimado = QTD_VEZES_ANIMAR;

	public final int TEMPO_ANIMACAO = 1000;

	public int deslocamentoX;
	public int deslocamentoY;

	private int dir;

	private Puzzle jogo;

	public Animacao(Puzzle puzzle) {
		this.jogo = puzzle;
	}

	/**
	 * Seta os valores para animação
	 * 
	 * @param dir
	 *            Recebe a direção a animar
	 */
	public void animar(int dir) {
		qtdVezesAnimado = 0;
		this.dir = dir;

		switch (dir) {
		case Canvas.UP: {
			deslocamentoX = 0;
			deslocamentoY = jogo.larguraPeca;
			break;
		}
		case Canvas.DOWN: {
			deslocamentoX = 0;
			deslocamentoY = -jogo.larguraPeca;
			break;
		}
		case Canvas.LEFT: {
			deslocamentoY = 0;
			deslocamentoX = jogo.larguraPeca;
			break;
		}
		case Canvas.RIGHT: {
			deslocamentoY = 0;
			deslocamentoX = -jogo.larguraPeca;
			break;
		}
		}

		animando();
	}

	/**
	 * Atualiza os valores das posições para animar
	 * 
	 * @param dir
	 *            Recebe a direção a animar
	 */
	private void atualizarAnimacao(int dir) {

		switch (dir) {
		case Canvas.UP: {
			deslocamentoY = deslocamentoY
					- (jogo.alturaPeca / QTD_VEZES_ANIMAR);
			break;
		}
		case Canvas.DOWN: {
			deslocamentoY = deslocamentoY
					+ (jogo.alturaPeca / QTD_VEZES_ANIMAR);
			break;
		}
		case Canvas.LEFT: {
			deslocamentoX = deslocamentoX
					- (jogo.larguraPeca / QTD_VEZES_ANIMAR);
			break;
		}
		case Canvas.RIGHT: {
			deslocamentoX = deslocamentoX
					+ (jogo.larguraPeca / QTD_VEZES_ANIMAR);
			break;
		}
		}

		qtdVezesAnimado++;
	}

	/**
	 * Faz a animação
	 */
	private void animando() {
		new Thread(new Runnable() {

			public void run() {
				while (qtdVezesAnimado < QTD_VEZES_ANIMAR) {
					atualizarAnimacao(dir);
					jogo.repaint();
					try {
						Thread.sleep(TEMPO_ANIMACAO / QTD_VEZES_ANIMAR);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				deslocamentoX = 0;
				deslocamentoY = 0;
				jogo.repaint();
			}
		}).start();
	}

}
