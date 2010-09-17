package puzzle.principal;

import java.util.Calendar;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.TiledLayer;

import puzzle.foto.ManterFoto;
import puzzle.menu.Menu;
import puzzle.parabens.Parabens;
import puzzle.util.DadosJogo;
import puzzle.util.ImagemUtil;
import puzzle.util.JogoUtils;
import puzzle.util.Mensagens;

/**
 * Classe principal, contem toda a regra do jogo.
 * 
 * @author David Almeida Pitanguy data 06/09/2010
 */
public class Puzzle extends Canvas implements CommandListener {

	// itens a desenhar
	private final int DESENHO = 0;
	private final int LINHAS = 1;
	private final int NUMEROS = 2;

	// Altura e largura das pecas
	public final int alturaPeca;
	public final int larguraPeca;

	// Cores utilizadas
	private int corFundo = 0xFFFFFF;
	private int corLinha = 0xFF0000;

	private PuzzleMIDlet midlet;

	// Classe auxiliares
	private JogoUtils jogoUtils;
	private Movimentos movimentos;
	private DadosJogo dadosJogo;

	// Botões
	private Command desenhaFoto;
	private Command desenhaLinha;
	private Command desenhaNumero;
	private Command embaralhar;
	private Command sair;

	private TiledLayer desenho;

	private int[][] pecas;

	private int posicaoDoZeroX;
	private int posicaoDoZeroY;

	private int ppX;
	private int ppY;
	private int margem;

	private long tempoJog = 0;

	private boolean desenharFoto = true;
	private boolean desenharLinha = true;
	private boolean desenharNumero = true;

	/**
	 * Construtor responsavel por fazer todo o necessario para inicializar o
	 * jogo.
	 * 
	 * @param midlet
	 */
	public Puzzle(PuzzleMIDlet midlet) {
		this.midlet = midlet;
		this.midlet.setDisplayable(this);

		dadosJogo = new DadosJogo();

		setTitle(Mensagens.TITULO);

		jogoUtils = new JogoUtils();
		movimentos = new Movimentos(this);

		carregarOpcoesJogo();
		embaralhar = new Command(Mensagens.EMBARALHAR, Command.ITEM, 3);
		sair = new Command(Mensagens.SAIR, Command.ITEM, 4);

		addCommand(embaralhar);
		addCommand(sair);
		setCommandListener(this);

		posicaoDoZeroX = dadosJogo.getQtdPcsJogo() - 1;
		posicaoDoZeroY = dadosJogo.getQtdPcsJogo() - 1;

		margem = getWidth() < getHeight() ? getWidth()
				/ dadosJogo.getQtdPcsJogo() : getHeight()
				/ dadosJogo.getQtdPcsJogo();

		alturaPeca = getHeight() / dadosJogo.getQtdPcsJogo();
		larguraPeca = getWidth() / dadosJogo.getQtdPcsJogo();

		pecas = jogoUtils.carregarPecas(dadosJogo.getQtdPcsJogo());

		movimentos.embaralhar();

		carregarDesenho();

		this.tempoJog = Calendar.getInstance().getTime().getTime();

	}

	/**
	 * Carrega o desenho a ser mostrado
	 */
	private void carregarDesenho() {

		Image imagem = new ManterFoto().carregarFoto(dadosJogo
				.getNumImagemSelecionada());

		imagem = new ImagemUtil().redimencionarImagem(imagem, larguraPeca
				* dadosJogo.getQtdPcsJogo(),
				alturaPeca * dadosJogo.getQtdPcsJogo());

		desenho = new TiledLayer(dadosJogo.getQtdPcsJogo(),
				dadosJogo.getQtdPcsJogo(), imagem, larguraPeca, alturaPeca);
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

		// desenha a foto
		if (desenharFoto) {
			desenhar(g, DESENHO);
			desenho.paint(g);
		}

		// desenha as linhas
		if (desenharLinha) {
			desenhar(g, LINHAS);
		}

		// desenhas os numeros
		if (desenharNumero) {
			desenhar(g, NUMEROS);
		}

	}

	/**
	 * Pinta as fotos e linhas.
	 * 
	 * @param g
	 *            Graphics aonde sera desenhado
	 */
	private void desenhar(Graphics g, int itemDesenhar) {
		int pecaY = 0;
		for (int j = 0; j < getWidth() - larguraPeca / 2; j += larguraPeca) {

			int pecaX = 0;
			for (int i = 0; i < getHeight() - alturaPeca / 2; i += alturaPeca) {

				switch (itemDesenhar) {
				// desenhar imagem
				case DESENHO: {
					desenho.setCell(pecaY, pecaX, pecas[pecaX][pecaY]);
					break;
				}
					// desenha as linhas
				case LINHAS: {
					g.drawRect(j, i, larguraPeca, alturaPeca);
					break;
				}
					// desenhas os numeros
				case NUMEROS: {
					if (pecas[pecaX][pecaY] != 0) {
						g.drawString(Integer.toString(pecas[pecaX][pecaY]),
								j + 3, i, Graphics.LEFT | Graphics.TOP);
					}
					break;
				}
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
		} else if (c == this.desenhaLinha) {
			desenharLinha = !desenharLinha;
			this.removeCommand(desenhaLinha);
			carregaOpcaoDesenharLinhas();
			repaint();
		} else if (c == this.desenhaNumero) {
			desenharNumero = !desenharNumero;
			this.removeCommand(desenhaNumero);
			carregaOpcaoDesenharNumeros();
			repaint();
		} else if (c == this.desenhaFoto) {
			desenharFoto = !desenharFoto;
			this.removeCommand(desenhaFoto);
			carregaOpcaoDesenharFoto();
			repaint();
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
		this.tempoJog = Calendar.getInstance().getTime().getTime()
				- this.tempoJog;

		Display.getDisplay(this.midlet).setCurrent(
				new Parabens(midlet, tempoJog, movimentos.getMovimentosJog()));
	}

	/**
	 * Carrega as opcoes do jogo de acordo com a escolha do jogador
	 */
	private void carregarOpcoesJogo() {
		carregaOpcaoDesenharFoto();
		carregaOpcaoDesenharLinhas();
		carregaOpcaoDesenharNumeros();
	}

	/**
	 * Carrega a opcao de desenhar numeros.
	 */
	private void carregaOpcaoDesenharNumeros() {
		desenhaNumero = new Command(
				desenharNumero ? Mensagens.NAO_DESENHA_NUMERO
						: Mensagens.DESENHA_NUMERO, Command.ITEM, 1);
		addCommand(desenhaNumero);
	}

	/**
	 * Carrega a opcao de desenhar linhas.
	 */
	private void carregaOpcaoDesenharLinhas() {
		desenhaLinha = new Command(desenharLinha ? Mensagens.NAO_DESENHA_LINHA
				: Mensagens.DESENHA_LINHA, Command.ITEM, 2);
		addCommand(desenhaLinha);
	}

	/**
	 * Carrega a opcao de desenhar foto.
	 */
	private void carregaOpcaoDesenharFoto() {
		desenhaFoto = new Command(desenharFoto ? Mensagens.NAO_DESENHA_FOTO
				: Mensagens.DESENHA_FOTO, Command.ITEM, 3);
		addCommand(desenhaFoto);
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
