/**
 * 
 */
package puzzle.menu;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.midlet.MIDlet;

import puzzle.util.DadosJogo;
import puzzle.util.Mensagens;

/**
 * Menu de configuração
 * 
 * @author David Almeida Pitanguy data 14/09/2010
 */
public class Configuracao extends Form implements CommandListener {

	private MIDlet midlet;

	private DadosJogo dadosJogo;

	public static final int QTD_MIN = 2;

	private Command ok;

	private Gauge pecas;

	public Configuracao(MIDlet midlet) {
		super(Mensagens.CONFIGURACAO);

		this.dadosJogo = new DadosJogo();

		this.ok = new Command(Mensagens.OK, Command.SCREEN, 1);
		this.addCommand(this.ok);
		this.setCommandListener(this);

		this.pecas = new Gauge(Mensagens.QTD_PCS, true, 10,
				dadosJogo.getQtdPcsJogo());

		this.append(this.pecas);

		this.append(Mensagens.QTD_PCS_MIN);

		this.midlet = midlet;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.CommandListener#commandAction(javax.microedition
	 * .lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == ok) {
			if (pecas.getValue() >= QTD_MIN) {
				dadosJogo.salvarQtdPecasJogo(pecas.getValue());
				Display.getDisplay(midlet).setCurrent(new Menu(midlet));
			}
		}
	}

}
