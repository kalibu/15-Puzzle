/**
 * 
 */
package puzzle.ranking;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.TextField;

import puzzle.principal.PuzzleMIDlet;
import puzzle.util.Mensagens;

/**
 * Adiciona o jogador ao ranking
 * 
 * @author David Almeida Pitanguy data 14/09/2010
 */
public class AdicionarRanking extends Form implements CommandListener {

	private PuzzleMIDlet midlet;

	private TextField tf;

	private Command nSalvar;
	private Command salvar;
	
	private Ranking ranking;

	private final int QTD_MAX_LETRAS_NOME = 100;
	
	private long tempoJog;
	private long movimentosJog;

	/**
	 * @param midlet 
	 * @param tempo Tempo do jogador.
	 * @param movimentos Movimentos do jogador.
	 */
	public AdicionarRanking(PuzzleMIDlet midlet, long tempo, long movimentos) {
		super(Mensagens.MSG_ADD_RANKING);

		this.midlet = midlet;
		
		this.ranking = new Ranking(midlet);
		
		this.tempoJog = tempo;
		this.movimentosJog = movimentos;

		this.nSalvar = new Command(Mensagens.NAO_SALVAR, Command.EXIT, 1);
		this.salvar = new Command(Mensagens.SALVAR, Command.SCREEN, 1);
		this.addCommand(this.nSalvar);
		this.addCommand(this.salvar);
		this.setCommandListener(this);

		tf = new TextField(Mensagens.NOME, Mensagens.STRING_VAZIO, QTD_MAX_LETRAS_NOME, TextField.ANY);
		append(tf);
	}

	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {

		if (c == this.salvar) {

			//adiciona nome padrao caso ñ for selecionado um nome
			if(tf.getString().length() == 0){
				tf.setString(Mensagens.NOME_PADRAO);
			}
			
			ranking.addRecord(new DadosRanking(tf.getString(), this.tempoJog, this.movimentosJog));
		}

		Display.getDisplay(midlet).setCurrent(new Ranking(midlet));
	}

}
