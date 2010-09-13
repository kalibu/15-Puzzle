/**
 * 
 */
package puzzle.menu;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import puzzle.principal.Puzzle;
import puzzle.util.Imagens;

/**
 * Menu principal do jogo.
 * 
 * @author David Almeida Pitanguy data 13/09/2010
 */
public class Menu extends Canvas {

	private MIDlet midlet;

	private final int MENU_PRINCIPAL = 0;
	private final int MENU_CONFIRMACAO = 1;

	private Imagens imagens;

	private int menuSelecionado = MENU_PRINCIPAL;
	private int itemSelecionado = 0;

	private int alturaItem = 20;
	private int margemItem = alturaItem / 2;

	// Guarda os menus do jogo
	private String menu[][] = { { Imagens.INICIAR, Imagens.SAIR },
			{ Imagens.SIM, Imagens.NAO } };

	public Menu(MIDlet midlet) {
		this.midlet = midlet;

		this.imagens = new Imagens();
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
			Image image = null;

			try {
				if (i == itemSelecionado) {
					image = Image.createImage(imagens
							.getCaminhoImagem(menu[menuSelecionado][i]
									+ Imagens.SELECIONADO));
				} else {
					image = Image.createImage(imagens
							.getCaminhoImagem(menu[menuSelecionado][i]));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			g.drawImage(image, x, y, Graphics.VCENTER | Graphics.HCENTER);

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

			// menu principal
			if (menuSelecionado == MENU_PRINCIPAL) {
				// iniciar
				if (itemSelecionado == 0) {
					Display.getDisplay(midlet).setCurrent(new Puzzle(midlet));
				} else
				// sair
				if (itemSelecionado == 1) {
					menuSelecionado = MENU_CONFIRMACAO;
				}
			} else
			// menu confirmação
			if (menuSelecionado == MENU_CONFIRMACAO) {
				if (itemSelecionado == 0) {
					midlet.notifyDestroyed();
				} else {
					menuSelecionado = MENU_PRINCIPAL;
					itemSelecionado = 0;
				}
			}

		}

		repaint();
	}

}
