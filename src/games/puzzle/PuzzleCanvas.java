package games.puzzle;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

public class PuzzleCanvas extends Canvas implements CommandListener {

	private MIDlet midlet;
	private Command sair;
	private int[][] pecas = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 },
			{ 9, 10, 11, 12 }, { 13, 14, 15, 0 } };
	private int posicaoDoZeroX = 3;
	private int posicaoDoZeroY = 3;

	private int ppX;
	private int ppY;
	private int margem = getWidth() < getHeight() ? getWidth() / 4
			: getHeight() / 4;

	public PuzzleCanvas(MIDlet midlet) {
		setTitle("Jogo Maluco");
		this.midlet = midlet;

		sair = new Command("Sair", Command.EXIT, 1);

		addCommand(sair);
		setCommandListener(this);
	}

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

	public void commandAction(Command c, Displayable d) {
		if (c == this.sair) {
			midlet.notifyDestroyed();
		}
	}

	protected void keyPressed(int keyCode) {
		int tecla = getGameAction(keyCode);

		if (tecla == Canvas.UP) {
			if (posicaoDoZeroX + 1 < 4) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX + 1][posicaoDoZeroY];
				posicaoDoZeroX++;
			}
		}
		if (tecla == Canvas.DOWN) {
			if (posicaoDoZeroX - 1 > -1) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX - 1][posicaoDoZeroY];
				posicaoDoZeroX--;
			}
		}
		if (tecla == Canvas.LEFT) {
			if (posicaoDoZeroY + 1 < 4) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX][posicaoDoZeroY + 1];
				posicaoDoZeroY++;
			}
		}
		if (tecla == Canvas.RIGHT) {
			if (posicaoDoZeroY - 1 > -1) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX][posicaoDoZeroY - 1];
				posicaoDoZeroY--;
			}
		}
		pecas[posicaoDoZeroX][posicaoDoZeroY] = 0;

		repaint();
	}

	protected void pointerPressed(int x, int y) {
		this.ppX = x;
		this.ppY = y;
	}

	protected void pointerReleased(int x, int y) {
		if ((ppY - y) > margem) {
			if (posicaoDoZeroX + 1 < 4) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX + 1][posicaoDoZeroY];
				posicaoDoZeroX++;
			}
		}
		if ((y - ppY) > margem) {
			if (posicaoDoZeroX - 1 > -1) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX - 1][posicaoDoZeroY];
				posicaoDoZeroX--;
			}
		}
		if ((ppX - x) > margem) {
			if (posicaoDoZeroY + 1 < 4) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX][posicaoDoZeroY + 1];
				posicaoDoZeroY++;
			}
		}
		if ((x - ppX) > margem) {
			if (posicaoDoZeroY - 1 > -1) {
				pecas[posicaoDoZeroX][posicaoDoZeroY] = pecas[posicaoDoZeroX][posicaoDoZeroY - 1];
				posicaoDoZeroY--;
			}
		}

		pecas[posicaoDoZeroX][posicaoDoZeroY] = 0;

		repaint();
	}

}
