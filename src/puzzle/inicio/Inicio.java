/**
 * 
 */
package puzzle.inicio;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

import puzzle.principal.Mensagens;
import puzzle.principal.Puzzle;

/**
 * Cuida da tela inicial.
 * 
 * @author David Almeida Pitanguy
 * data 10/09/2010
 */
public class Inicio extends Canvas{

	private MIDlet midlet;
	
	private Image fundo;
	
	/**
	 * @param midlet
	 */
	public Inicio(MIDlet midlet) {
		this.midlet = midlet;
		
		try {
			fundo = Image.createImage("/fundo.png");
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
			Display.getDisplay(this.midlet).setCurrent(new Puzzle(this.midlet));
		}
		
	}
}
