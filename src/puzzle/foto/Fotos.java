/**
 * 
 */
package puzzle.foto;

import java.io.IOException;

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
import puzzle.util.Imagens;
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

	private Imagens imagens;
	private ImagemUtil imagemUtil;
	private ManterFoto manterFoto;
	private DadosJogo dadosJogo;

	private List fotos;

	public Fotos(MIDlet midlet) {
		this.midlet = midlet;

		this.imagemUtil = new ImagemUtil();
		this.imagens = new Imagens();
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

		carregarFotos();

		Display.getDisplay(midlet).setCurrent(fotos);
	}

	/**
	 * Carrega todas as fotos do jogo Obs: redimensiona as mesmas para 32x32
	 * (tamanho de icone.)
	 */
	private void carregarFotos() {
		fotos.deleteAll();

		try {
			// carrega imagem padrao
			Image imagem = Image.createImage(imagens
					.getCaminhoImagem(Imagens.IMAGEM_PADRAO));
			fotos.append(Mensagens.FOTO_PADRAO,
					imagemUtil.redimencionarImagem(imagem, 32, 32));

			Image imagens[] = manterFoto.getTodasImagens();
			for (int i = 0; i < imagens.length; i++) {
				fotos.append(Mensagens.FOTO + ": " + (i + 1),
						imagemUtil.redimencionarImagem(imagens[i], 32, 32));
			}
			
			//seleciona foto salva
			fotos.setSelectedIndex(dadosJogo.getNumImagemSelecionada(), true);
			
		} catch (IOException e) {
			e.printStackTrace();
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
		if (c == voltar) {
			voltarMenu();
		} else if (c == tirarFoto) {
			Display.getDisplay(midlet).setCurrent(new TirarFoto(midlet));
		} else if (c == salvar) {
			dadosJogo.salvarImagemSelecionada(fotos.getSelectedIndex());
			voltarMenu();
		} else if (c == deletar) {
			//add msg para ñ deletar foto padrao
			if (fotos.getSelectedIndex() == 0) {
				Alert a = new Alert(Mensagens.DELETAR_FOTO_PADRAO);
				a.setTimeout(Alert.FOREVER);
				Display.getDisplay(midlet).setCurrent(a, fotos);
			} else {
				manterFoto.deletaFoto(fotos.getSelectedIndex());
				//zerar classe para atualizar valores
				dadosJogo = new DadosJogo();
				carregarFotos();
			}
		}
	}

	private void voltarMenu() {
		Display.getDisplay(midlet).setCurrent(new Menu(midlet));
	}
}
