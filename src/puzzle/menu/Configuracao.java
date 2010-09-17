/**
 * 
 */
package puzzle.menu;

import java.io.IOException;

import javax.microedition.lcdui.Choice;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Gauge;
import javax.microedition.lcdui.Image;

import puzzle.principal.PuzzleMIDlet;
import puzzle.util.DadosJogo;
import puzzle.util.Imagens;
import puzzle.util.Mensagens;

/**
 * Menu de configuração
 * 
 * @author David Almeida Pitanguy data 14/09/2010
 */
public class Configuracao extends Form implements CommandListener {

	private PuzzleMIDlet midlet;

	private DadosJogo dadosJogo;
	private Imagens imagens;

	public static final int QTD_MIN = 2;

	private Command ok;

	private Gauge pecas;

	private ChoiceGroup audio;

	public Configuracao(PuzzleMIDlet midlet) {
		super(Mensagens.CONFIGURACAO);

		this.midlet = midlet;
		this.midlet.setDisplayable(this);

		this.dadosJogo = new DadosJogo();
		this.imagens = new Imagens();

		this.ok = new Command(Mensagens.OK, Command.SCREEN, 1);
		this.addCommand(this.ok);
		this.setCommandListener(this);

		this.audio = new ChoiceGroup(Mensagens.AUDIO, Choice.EXCLUSIVE);

		Image audioOn = null;
		Image audioOff = null;
		try {
			audioOn = Image.createImage(imagens
					.getCaminhoImagem(Imagens.AUDIO_ON));
			audioOff = Image.createImage(imagens
					.getCaminhoImagem(Imagens.AUDIO_OFF));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.audio.append(Mensagens.LIGADO, audioOn);
		this.audio.append(Mensagens.DESLIGADO, audioOff);
		this.append(audio);
		this.audio.setSelectedIndex(0, this.midlet.getAudio().isTocando());
		this.audio.setSelectedIndex(1, !this.midlet.getAudio().isTocando());

		this.pecas = new Gauge(Mensagens.QTD_PCS, true, 10,
				dadosJogo.getQtdPcsJogo());

		this.append(this.pecas);

		this.append(Mensagens.QTD_PCS_MIN);
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
			if (audio.getSelectedIndex() == 0) {
				this.midlet.getAudio().start();
			} else {
				this.midlet.getAudio().stop();
			}
		}
	}

}
