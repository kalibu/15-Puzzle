package puzzle.principal;

import java.util.Calendar;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.midlet.MIDlet;

import puzzle.menu.Menu;
import puzzle.parabens.Parabens;
import puzzle.util.Mensagens;

/**
 * Classe principal, contem toda a regra do jogo.
 * 
 * @author David Almeida Pitanguy data 06/09/2010
 */
public class Puzzle extends Canvas implements CommandListener {


	// Quantidade de pedras por coluna
	public static final int PECAS_POR_COLUNA = 4;
	
	// Quantidade de movimentos a fazer para embaralhar
	public static final int VEZES_EMBARALHAR = PECAS_POR_COLUNA * 1;
	
	//Altura e largura das pecas
	public final int alturaPeca = getHeight() / PECAS_POR_COLUNA;
	public final int larguraPeca = getWidth() / PECAS_POR_COLUNA;

	// Cores utilizadas
	private int corFundo = 0xFFFFFF;
	private int corLinha = 0x000000;

	private MIDlet midlet;

	// Classe auxiliares
	private JogoUtils jogoUtils;
	private Movimentos movimentos;

	// Botões
	private Command embaralhar;
	private Command sair;

	private int[][] pecas;

	private int posicaoDoZeroX = PECAS_POR_COLUNA - 1;
	private int posicaoDoZeroY = PECAS_POR_COLUNA - 1;

	private int ppX;
	private int ppY;
	private int margem = getWidth() < getHeight() ? getWidth()
			/ PECAS_POR_COLUNA : getHeight() / PECAS_POR_COLUNA;
	
	private long tempoJog = 0;

	/**
	 * Construtor responsavel por fazer todo o necessario para inicializar o
	 * jogo.
	 * 
	 * @param midlet
	 */
	public Puzzle(MIDlet midlet) {
		setTitle(Mensagens.TITULO);
		this.midlet = midlet;

		jogoUtils = new JogoUtils();
		movimentos = new Movimentos(this);

		embaralhar = new Command(Mensagens.EMBARALHAR, Command.SCREEN, 1);
		sair = new Command(Mensagens.SAIR, Command.SCREEN, 2);

		addCommand(embaralhar);
		addCommand(sair);
		setCommandListener(this);

		pecas = jogoUtils.carregarPecas(PECAS_POR_COLUNA);

		movimentos.embaralhar();
		
		this.tempoJog = Calendar.getInstance().getTime().getTime();
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

		g.setColor(corLinha);

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
			Display.getDisplay(midlet).setCurrent(new Menu(midlet));
		} else if (c == this.embaralhar) {
			movimentos.embaralhar();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#keyPressed(int)
	 */
	protected void keyPressed(int keyCode) {
		// verifica se tecla selecionada é igual a: 2, 4, 6, 8 que são os
		// movimentos alem do direcional.
		if ((keyCode == Canvas.KEY_NUM2) || (keyCode == Canvas.KEY_NUM4)
				|| (keyCode == Canvas.KEY_NUM6) || (keyCode == Canvas.KEY_NUM8)) {
			movimentos.realizaJogada(keyCode);
		} else {
			movimentos.realizaJogada(getGameAction(keyCode));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#pointerReleased(int, int)
	 */
	protected void pointerReleased(int x, int y) {
		// Verifica se clicou e arrastou, pelo menos a margem q é a
		// altura/largura de uma peça
		if ((ppY - y) > margem) {
			movimentos.realizaJogada(Canvas.UP);
		} else if ((y - ppY) > margem) {
			movimentos.realizaJogada(Canvas.DOWN);
		} else if ((ppX - x) > margem) {
			movimentos.realizaJogada(Canvas.LEFT);
		} else if ((x - ppX) > margem) {
			movimentos.realizaJogada(Canvas.RIGHT);
		} else {
			// pega maior margem para ver aonde o jogador clicou
			int maiorMargemY = (ppY - y) > (y - ppY) ? (ppY - y) : (y - ppY);
			int maiorMargemX = (ppX - x) > (x - ppX) ? (ppX - x) : (x - ppX);
			int maiorMargem = maiorMargemX > maiorMargemY ? maiorMargemX
					: maiorMargemY;

			if (maiorMargem <= margem / 2) {
				movimentos.moverPecaPorClique(ppX / larguraPeca, ppY
						/ alturaPeca);
			}
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
	 * Animação para o ganhador
	 */
	public void ganhou() {
		this.tempoJog = Calendar.getInstance().getTime().getTime() - this.tempoJog;
		
		Display.getDisplay(this.midlet).setCurrent(new Parabens(midlet, tempoJog, movimentos.getMovimentosJog()));
	}

	/**
	 * @return the jogoUtils
	 */
	public JogoUtils getJogoUtils() {
		return jogoUtils;
	}

	/**
	 * @return the posicaoDoZeroX
	 */
	public int getPosicaoDoZeroX() {
		return posicaoDoZeroX;
	}

	/**
	 * @param posicaoDoZeroX
	 *            the posicaoDoZeroX to set
	 */
	public void setPosicaoDoZeroX(int posicaoDoZeroX) {
		this.posicaoDoZeroX = posicaoDoZeroX;
	}

	/**
	 * @return the posicaoDoZeroY
	 */
	public int getPosicaoDoZeroY() {
		return posicaoDoZeroY;
	}

	/**
	 * @param posicaoDoZeroY
	 *            the posicaoDoZeroY to set
	 */
	public void setPosicaoDoZeroY(int posicaoDoZeroY) {
		this.posicaoDoZeroY = posicaoDoZeroY;
	}

	/**
	 * @return the pecas
	 */
	public int[][] getPecas() {
		return pecas;
	}

}
