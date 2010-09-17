/**
 * 
 */
package puzzle.inicio;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import puzzle.menu.Menu;
import puzzle.principal.PuzzleMIDlet;
import puzzle.util.ImagemUtil;
import puzzle.util.Imagens;
import puzzle.util.Mensagens;

/**
 * Cuida da tela inicial.
 * 
 * @author David Almeida Pitanguy
 * data 10/09/2010
 */
public class TelaInicial extends Canvas{

	private PuzzleMIDlet midlet;
	
	private Image fundo;
	
	private Imagens imagens;
	
	private ImagemUtil imagemUtil;
	
	/**
	 * @param midlet
	 */
	public TelaInicial(PuzzleMIDlet midlet) {
		this.midlet = midlet;
		
		this.imagens = new Imagens();
		this.imagemUtil = new ImagemUtil();
		
		try {
			Image image = Image.createImage(imagens.getCaminhoImagem(Imagens.FUNDO));
			
			fundo = imagemUtil.redimencionarImagem(image, getWidth(), getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	protected void paint(Graphics g) {
		g.drawImage(fundo, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.VCENTER);
		g.drawString(Mensagens.START, getWidth() / 2, getHeight(), Graphics.HCENTER | Graphics.BOTTOM);
	}

	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.Canvas#keyPressed(int)
	 */
	protected void keyPressed(int keyCode) {
		
		if((keyCode == Canvas.KEY_NUM5) || (getGameAction(keyCode) == Canvas.FIRE)){
			iniciarMenu();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.Canvas#pointerPressed(int, int)
	 */
	protected void pointerPressed(int x, int y) {
		iniciarMenu();
	}
	
	/**
	 * Passa para proxima tela, que é o menu
	 */
	private void iniciarMenu(){
		Display.getDisplay(this.midlet).setCurrent(new Menu(this.midlet));		
	}
}
