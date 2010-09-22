/**
 * 
 */
package puzzle.menu;

import java.io.IOException;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import puzzle.foto.Fotos;
import puzzle.principal.Puzzle;
import puzzle.principal.PuzzleMIDlet;
import puzzle.ranking.Ranking;
import puzzle.util.ImagemUtil;
import puzzle.util.Imagens;
import puzzle.util.Mensagens;

/**
 * Menu principal do jogo.
 * 
 * @author David Almeida Pitanguy data 13/09/2010
 */
public class Menu extends Canvas {

	private PuzzleMIDlet midlet;

	private final int MENU_PRINCIPAL = 0;
	private final int MENU_CONFIRMACAO = 1;

	private Imagens imagens;
	private ImagemUtil imagemUtil;

	private int menuSelecionado = MENU_PRINCIPAL;
	private int itemSelecionado = 0;

	// Guarda os menus do jogo
	private String menu[][] = {
			{ Imagens.INICIAR, Imagens.RANKING, Imagens.CONFIGURACAO,
					Imagens.TIRAR_FOTO, Imagens.CREDITOS, Imagens.SAIR },
			{ Imagens.SIM, Imagens.NAO } };

	private int larguraItem = (getWidth() / 3) * 2;
	private int alturaItem = getHeight() / (menu[MENU_PRINCIPAL].length * 2);
	private int margemItem = alturaItem / 2;

	private Image[][] imagensMenu;
	private Image[][] imagensSelecionadoMenu;

	public Menu(PuzzleMIDlet midlet) {
		this.midlet = midlet;
		this.midlet.setDisplayable(this);

		this.imagens = new Imagens();
		this.imagemUtil = new ImagemUtil();

		carregarImagensMenus();
	}

	/**
	 * Carrega e redimenciona as imagens dos menus.
	 */
	private void carregarImagensMenus() {
		imagensMenu = new Image[menu.length][];
		imagensSelecionadoMenu = new Image[menu.length][];

		for (int i = 0; i < menu.length; i++) {
			imagensMenu[i] = new Image[menu[i].length];
			imagensSelecionadoMenu[i] = new Image[menu[i].length];

			for (int j = 0; j < menu[i].length; j++) {

				try {

					Image imagem = Image.createImage(imagens
							.getCaminhoImagem(menu[i][j]));

					Image imagemSelecionada = Image
							.createImage(imagens.getCaminhoImagem(menu[i][j]
									+ Imagens.SELECIONADO));

					imagensMenu[i][j] = imagemUtil.redimencionarImagem(imagem,
							larguraItem, alturaItem);

					imagensSelecionadoMenu[i][j] = imagemUtil
							.redimencionarImagem(imagemSelecionada,
									larguraItem, alturaItem);

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	protected void paint(Graphics g) {

		int x = getWidth() / 2;
		int y = getAlturaPintaItem(menu[menuSelecionado].length);

		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());

		for (int i = 0; i < menu[menuSelecionado].length; i++) {

			if (i == itemSelecionado) {
				g.drawImage(imagensSelecionadoMenu[menuSelecionado][i], x, y
						+ margemItem, Graphics.VCENTER | Graphics.HCENTER);
			} else {
				g.drawImage(imagensMenu[menuSelecionado][i], x, y + margemItem,
						Graphics.VCENTER | Graphics.HCENTER);
			}

			y += alturaItem + margemItem;
		}
	}

	/**
	 * @param qtdItens
	 *            Quantidade de itens do menu atual.
	 * @return Retorna a altura do primeiro item a ser mostrado.
	 */
	private int getAlturaPintaItem(int qtdItens) {
		int metadeTela = getHeight() / 2;
		int alturaItensRestantes = (qtdItens * alturaItem)
				+ ((qtdItens - 1) * margemItem);

		return metadeTela - (alturaItensRestantes / 2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#keyPressed(int)
	 */
	protected void keyPressed(int keyCode) {
		// cima
		if ((keyCode == Canvas.KEY_NUM2)
				|| (getGameAction(keyCode) == Canvas.UP)) {
			itemSelecionado = itemSelecionado == 0 ? menu[menuSelecionado].length - 1
					: itemSelecionado - 1;
		} else
		// baixo
		if ((keyCode == Canvas.KEY_NUM8)
				|| (getGameAction(keyCode) == Canvas.DOWN)) {
			itemSelecionado = itemSelecionado == menu[menuSelecionado].length - 1 ? 0
					: itemSelecionado + 1;
		} else
		// fire
		if ((keyCode == Canvas.KEY_NUM5)
				|| (getGameAction(keyCode) == Canvas.FIRE)) {

			menus();

		}

		repaint();
	}

	/**
	 * Define qual menu deve ser mostrado.
	 */
	private void menus() {
		// menu principal
		if (menuSelecionado == MENU_PRINCIPAL) {
			menuPrincipal();

		} else
		// menu confirmação
		if (menuSelecionado == MENU_CONFIRMACAO) {
			menuConfirmacao();
		}
	}

	/**
	 * Cuida do menu principal.
	 */
	private void menuPrincipal() {
		switch (itemSelecionado) {
		// iniciar
		case 0: {
			Display.getDisplay(midlet).setCurrent(new Puzzle(midlet));
			break;
		}
			// ranking
		case 1: {
			Display.getDisplay(midlet).setCurrent(new Ranking(midlet));
			break;
		}
			// configuracao
		case 2: {
			Display.getDisplay(midlet).setCurrent(new Configuracao(midlet));
			break;
		}
			// tirar foto
		case 3: {
			new Fotos(midlet);
			break;
		}
			// creditos
		case 4: {
			Alert creditos = new Alert(Mensagens.CREDITOS,
					Mensagens.MSG_CREDITOS, null, AlertType.INFO);
			creditos.setTimeout(Alert.FOREVER);

			Display.getDisplay(midlet).setCurrent(creditos, this);
			break;
		}
			// sair
		default: {
			menuSelecionado = MENU_CONFIRMACAO;
			itemSelecionado = 1;
			break;
		}
		}
	}

	/**
	 * Cuida do menu de confirmação.
	 */
	private void menuConfirmacao() {
		switch (itemSelecionado) {
		// sim
		case 0: {
			midlet.notifyDestroyed();
			break;
		}
			// nao
		default: {
			menuSelecionado = MENU_PRINCIPAL;
			itemSelecionado = 0;
			break;
		}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.microedition.lcdui.Canvas#pointerPressed(int, int)
	 */
	protected void pointerPressed(int x, int y) {
		// trata o touch dos menus
		int xi = (getWidth() / 2) - (larguraItem / 2);
		int yi = getAlturaPintaItem(menu[menuSelecionado].length)
				- (alturaItem / 2) + margemItem;

		for (int i = 0; i < menu[menuSelecionado].length; i++) {
			int xf = xi + larguraItem;
			int yf = yi + alturaItem;

			if ((xi <= x) && (x <= xf) && (yi <= y) && (y <= yf)) {

				// se o menu selecionado for o que estiver clicado, ativa caso
				// contrario so seleciona ele.
				if (itemSelecionado == i) {
					menus();
				} else {
					itemSelecionado = i;
				}
				repaint();
			}

			yi += alturaItem + margemItem;
		}
	}

}
