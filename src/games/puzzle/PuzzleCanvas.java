package games.puzzle;

import java.util.Random;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

/**
 * @author David Almeida Pitanguy date 06/09/2010
 */
public class PuzzleCanvas extends Canvas implements CommandListener {

	/**
	 * Quantidade de movimentos a fazer para embaralhar
	 */
	private static final int VEZES_EMBARALHAR = 1000;

	/**
	 * Quantidade de pedras por coluna
	 */
	private static final int PEDRAS_POR_COLUNA = 4;

	private static final String TITULO = "15-Puzzle";

	private MIDlet midlet;

	private Command embaralhar;
	private Command sair;

	private int[][] pecas = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 },
			{ 9, 10, 11, 12 }, { 13, 14, 15, 0 } };

	private int posicaoDoZeroX = 3;
	private int posicaoDoZeroY = 3;

	private int ppX;
	private int ppY;
	private int margem = getWidth() < getHeight() ? getWidth()
			/ PEDRAS_POR_COLUNA : getHeight() / PEDRAS_POR_COLUNA;

	/**
	 * Construtor responsavel por fazer todo o necessario para inicializar o
	 * jogo.
	 * 
	 * @param midlet
	 */
	public PuzzleCanvas(MIDlet midlet) {
		setTitle(TITULO);
		this.midlet = midlet;

		embaralhar = new Command("Embaralhar", Command.SCREEN, 1);
		sair = new Command("Sair", Command.EXIT, 1);

		addCommand(embaralhar);
		addCommand(sair);
		setCommandListener(this);

		embaralhar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	protected void paint(Graphics g) {

		int alturaPeca = getHeight() / 4;
		int larguraPeca = getWidth() / 4;

		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(0x000000);

		int pecaY = 0;
		for (int j = 0; j < getWidth() - larguraPeca / 2; j += larguraPeca) {

			int pecaX = 0;
			for (int i = 0; i < getHeight() - alturaPeca / 2; i += alturaPeca) {

				g.drawRect(j, i, larguraPeca, alturaPeca);
				if (pecas[pecaX][pecaY] != 0) {
					g.drawString(Integer.toString(pecas[pecaX][pecaY]), j
							+ alturaPeca / 2 - g.getFont().getHeight() / 2, i
							+ larguraPeca / 2, Graphics.TOP | Graphics.HCENTER);
				}
				pecaX++;
			}
			pecaY++;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.CommandListener#commandAction(javax.microedition
	 * .lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == this.sair) {
			midlet.notifyDestroyed();
		} else if (c == this.embaralhar) {
			embaralhar();
		}
	}

	/**
	 * Altera a posição das peças caso for possivel.
	 * 
	 * @param codeGameAction
	 */
	private void alterarPosicaoPecas(int codeGameAction) {
		switch (codeGameAction) {
		case (Canvas.UP): {
			if (posicaoDoZeroX + 1 < 4) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX + 1][posicaoDoZeroY];
				posicaoDoZeroX++;
			}
			break;
		}
		case (Canvas.DOWN): {
			if (posicaoDoZeroX - 1 > -1) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX - 1][posicaoDoZeroY];
				posicaoDoZeroX--;
			}
			break;
		}
		case (Canvas.LEFT): {
			if (posicaoDoZeroY + 1 < 4) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX][posicaoDoZeroY + 1];
				posicaoDoZeroY++;
			}
			break;
		}
		case (Canvas.RIGHT): {
			if (posicaoDoZeroY - 1 > -1) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX][posicaoDoZeroY - 1];
				posicaoDoZeroY--;
			}
			break;
		}
		}

		pecas[posicaoDoZeroX][posicaoDoZeroY] = 0;

		repaint();

		System.out.println(verificaGanhou());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#keyPressed(int)
	 */
	protected void keyPressed(int keyCode) {
		alterarPosicaoPecas(getGameAction(keyCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#pointerReleased(int, int)
	 */
	protected void pointerReleased(int x, int y) {
		if ((ppY - y) > margem) {
			alterarPosicaoPecas(Canvas.UP);
		}
		if ((y - ppY) > margem) {
			alterarPosicaoPecas(Canvas.DOWN);
		}
		if ((ppX - x) > margem) {
			alterarPosicaoPecas(Canvas.LEFT);
		}
		if ((x - ppX) > margem) {
			alterarPosicaoPecas(Canvas.RIGHT);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#pointerPressed(int, int)
	 */
	protected void pointerPressed(int x, int y) {
		this.ppX = x;
		this.ppY = y;
	}

	/**
	 * Embalha as peças.
	 */
	private void embaralhar() {

		int atual = -1;
		int ultimo = -1;
		int qtdUltimo = 0;
		Random r = new Random();

		for (int i = 0; i < VEZES_EMBARALHAR; i++) {

			do {
				atual = r.nextInt(PEDRAS_POR_COLUNA);

				if (atual == ultimo) {
					qtdUltimo++;
				} else {
					qtdUltimo = 0;
				}
			} while (qtdUltimo >= 4);

			colocaValoresCorretosEmbaralhar(atual);

			ultimo = atual;
		}

		repaint();
	}

	/**
	 * Utilizado para transformar os comandos em codigos do celular.
	 * 
	 * @param code
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
	 * @return Retorna verdadeiro de ganhou e falso caso contrario
	 */
	private boolean verificaGanhou() {

		int aux = 0;

		for (int i = 0; i < pecas.length; i++) {
			for (int j = 0; j < pecas[i].length; j++) {
				aux++;
				if ((aux <= 15) && (pecas[i][j] != aux)) {
					return false;
				}
			}
		}

		return true;
	}
}
