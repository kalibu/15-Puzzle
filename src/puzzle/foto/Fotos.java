/**
 * 
 */
package puzzle.foto;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.List;
import javax.microedition.midlet.MIDlet;

import puzzle.menu.Menu;
import puzzle.util.DadosJogo;
import puzzle.util.ImagemUtil;
import puzzle.util.Mensagens;

/**
 * Responsavel por gerenciar toda parte de fotos.
 * 
 * @author David Almeida Pitanguy data 15/09/2010
 */
public class Fotos implements CommandListener {

	private MIDlet midlet;

	private Command voltar;
	private Command salvar;
	private Command deletar;
	private Command tirarFoto;

	private ImagemUtil imagemUtil;
	private ManterFoto manterFoto;
	private DadosJogo dadosJogo;

	private int tamFoto = 32;

	public static final int QTD_FOTOS_PADRAO = 4;

	private List fotos;

	public Fotos(MIDlet midlet) {
		this.midlet = midlet;

		this.imagemUtil = new ImagemUtil();
		this.manterFoto = new ManterFoto();
		this.dadosJogo = new DadosJogo();

		voltar = new Command(Mensagens.VOLTAR, Command.EXIT, 1);
		tirarFoto = new Command(Mensagens.TIRAR_FOTO, Command.SCREEN, 1);
		salvar = new Command(Mensagens.DEFINIR, Command.SCREEN, 2);
		deletar = new Command(Mensagens.DELETAR, Command.SCREEN, 3);

		fotos = new List(Mensagens.FOTOS, List.EXCLUSIVE);

		fotos.addCommand(voltar);
		fotos.addCommand(tirarFoto);
		fotos.addCommand(salvar);
		fotos.addCommand(deletar);
		fotos.setCommandListener(this);

		verificarPrecisaAddImagensPadrao();

		carregarFotos();

		Display.getDisplay(midlet).setCurrent(fotos);
	}

	/**
	 * Verifica se precisa adicionar imagens padrao
	 */
	private void verificarPrecisaAddImagensPadrao() {
		if (manterFoto.getTodasImagens().length == 0) {

			byte[] arrayFotosPadroes = {0};

			for (int i = 1; i <= QTD_FOTOS_PADRAO; i++) {
				manterFoto.salvarFoto(arrayFotosPadroes);
			}
		}
	}

	/**
	 * Carrega todas as fotos do jogo Obs: redimensiona as mesmas para 32x32
	 * (tamanho de icone.)
	 */
	private void carregarFotos() {
		fotos.deleteAll();
		Image imagens[] = manterFoto.getTodasImagens();
		for (int i = 0; i < imagens.length; i++) {

			fotos.append(Mensagens.FOTO + ": " + (i + 1), imagemUtil
					.redimencionarImagem(imagens[i], tamFoto, tamFoto));

		}

		// seleciona foto salva
		fotos.setSelectedIndex(dadosJogo.getNumImagemSelecionada(), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.CommandListener#commandAction(javax.microedition
	 * .lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == voltar) {
			voltarMenu();
		} else if (c == tirarFoto) {
			Display.getDisplay(midlet).setCurrent(new TirarFoto(midlet));
		} else if (c == salvar) {
			dadosJogo.salvarImagemSelecionada(fotos.getSelectedIndex());
			voltarMenu();
		} else if (c == deletar) {
			// add msg para ñ deletar foto padrao
			if (fotos.getSelectedIndex() < QTD_FOTOS_PADRAO) {
				Alert a = new Alert(Mensagens.DELETAR_FOTO_PADRAO);
				a.setTimeout(Alert.FOREVER);
				Display.getDisplay(midlet).setCurrent(a, fotos);
			} else {
				manterFoto.deletaFoto(fotos.getSelectedIndex());
				// zerar classe para atualizar valores
				dadosJogo = new DadosJogo();
				carregarFotos();
			}
		}
	}

	private void voltarMenu() {
		Display.getDisplay(midlet).setCurrent(new Menu(midlet));
	}
}
