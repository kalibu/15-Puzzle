package puzzle.principal;

import java.util.Random;

import javax.microedition.lcdui.Canvas;

import puzzle.util.DadosJogo;

/**
 * Classe responsavel por cuidar de todos os movimentos.
 * 
 * @author David Almeida Pitanguy data 10/09/2010
 */
public class Movimentos {

	private Puzzle jogo;
	private long movimentosJog = 0;

	private DadosJogo dadosJogo;

	// Quantidade de movimentos a fazer para embaralhar
	public final int VEZES_EMBARALHAR = 10000;

	/**
	 * @param jogo
	 *            Recebe o jogo do qual vai tratar os movimentos.
	 */
	public Movimentos(Puzzle jogo) {
		this.jogo = jogo;

		this.dadosJogo = new DadosJogo();
	}

	/**
	 * Faz a jogada e verifica se ganhou.
	 * 
	 * @param codeGameAction
	 *            Codigo da ação a ser realizada.
	 */
	public void realizaJogada(int codeGameAction) {
		movimentosJog++;

		alterarPosicaoPecas(codeGameAction);

		if (jogo.getJogoUtils().isGanhou(jogo.getPecas(),
				dadosJogo.getQtdPcsJogo())) {
			jogo.ganhou();
		}
	}

	/**
	 * Altera a posição das peças caso for possivel.
	 * 
	 * @param codeGameAction
	 *            Codigo da ação a ser realizada.
	 */
	private void alterarPosicaoPecas(int codeGameAction) {
		switch (codeGameAction) {
		case (Canvas.UP):
		case (Canvas.KEY_NUM2): {
			if (jogo.getPosicaoDoZeroX() + 1 < dadosJogo.getQtdPcsJogo()) {
				jogo.getPecas()[jogo.getPosicaoDoZeroX()][jogo
						.getPosicaoDoZeroY()] = jogo.getPecas()[jogo
						.getPosicaoDoZeroX() + 1][jogo.getPosicaoDoZeroY()];
				jogo.setPosicaoDoZeroX(jogo.getPosicaoDoZeroX() + 1);
			}
			break;
		}
		case (Canvas.DOWN):
		case (Canvas.KEY_NUM8): {
			if (jogo.getPosicaoDoZeroX() - 1 > -1) {
				jogo.getPecas()[jogo.getPosicaoDoZeroX()][jogo
						.getPosicaoDoZeroY()] = jogo.getPecas()[jogo
						.getPosicaoDoZeroX() - 1][jogo.getPosicaoDoZeroY()];
				jogo.setPosicaoDoZeroX(jogo.getPosicaoDoZeroX() - 1);
			}
			break;
		}
		case (Canvas.LEFT):
		case (Canvas.KEY_NUM4): {
			if (jogo.getPosicaoDoZeroY() + 1 < dadosJogo.getQtdPcsJogo()) {
				jogo.getPecas()[jogo.getPosicaoDoZeroX()][jogo
						.getPosicaoDoZeroY()] = jogo.getPecas()[jogo
						.getPosicaoDoZeroX()][jogo.getPosicaoDoZeroY() + 1];
				jogo.setPosicaoDoZeroY(jogo.getPosicaoDoZeroY() + 1);
			}
			break;
		}
		case (Canvas.RIGHT):
		case (Canvas.KEY_NUM6): {
			if (jogo.getPosicaoDoZeroY() - 1 > -1) {
				jogo.getPecas()[jogo.getPosicaoDoZeroX()][jogo
						.getPosicaoDoZeroY()] = jogo.getPecas()[jogo
						.getPosicaoDoZeroX()][jogo.getPosicaoDoZeroY() - 1];
				jogo.setPosicaoDoZeroY(jogo.getPosicaoDoZeroY() - 1);
			}
			break;
		}
		}

		jogo.getPecas()[jogo.getPosicaoDoZeroX()][jogo.getPosicaoDoZeroY()] = 0;

		jogo.repaint();
	}

	/**
	 * Embalha as peças.
	 */
	public void embaralhar() {

		int atual = -1;
		int ultimo = -1;
		int qtdUltimo = 0;
		Random r = new Random();

		for (int i = 0; i < VEZES_EMBARALHAR; i++) {

			do {
				atual = r.nextInt(dadosJogo.getQtdPcsJogo());

				if (atual == ultimo) {
					qtdUltimo++;
				} else {
					qtdUltimo = 0;
				}
			} while (qtdUltimo >= dadosJogo.getQtdPcsJogo());

			colocaValoresCorretosEmbaralhar(atual);

			ultimo = atual;
		}

		jogo.repaint();
	}

	/**
	 * Utilizado para transformar os comandos em codigos do celular.
	 * 
	 * @param code
	 *            Codigo da ação a ser realizada.
	 */
	private void colocaValoresCorretosEmbaralhar(int code) {
		switch (code) {
		case 0: {
			alterarPosicaoPecas(Canvas.UP);
			break;
		}
		case 1: {
			alterarPosicaoPecas(Canvas.DOWN);
			break;
		}
		case 2: {
			alterarPosicaoPecas(Canvas.LEFT);
			break;
		}
		case 3: {
			alterarPosicaoPecas(Canvas.RIGHT);
			break;
		}
		}
	}

	/**
	 * Responsavel por mover as peças ao clicar.
	 * 
	 * @param x
	 *            Posição 'X' clicada.
	 * @param y
	 *            Posição 'Y' clicada.
	 */
	public void moverPecaPorClique(int x, int y) {

		if ((x < dadosJogo.getQtdPcsJogo() - 1)
				&& (jogo.getPecas()[y][x + 1] == 0)) {
			realizaJogada(Canvas.RIGHT);
		} else if ((x > 0) && (jogo.getPecas()[y][x - 1] == 0)) {
			realizaJogada(Canvas.LEFT);
		} else if ((y < dadosJogo.getQtdPcsJogo() - 1)
				&& (jogo.getPecas()[y + 1][x] == 0)) {
			realizaJogada(Canvas.DOWN);
		} else if ((y > 0) && (jogo.getPecas()[y - 1][x] == 0)) {
			realizaJogada(Canvas.UP);
		}

	}

	/**
	 * @return the movimentosJog
	 */
	public long getMovimentosJog() {
		return movimentosJog;
	}

}
