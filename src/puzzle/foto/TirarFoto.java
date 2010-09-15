package puzzle.foto;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.ImageItem;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VideoControl;
import javax.microedition.midlet.MIDlet;

import puzzle.menu.Menu;
import puzzle.util.ImagemUtil;
import puzzle.util.Mensagens;

/**
 * Tira foto da camera.
 * 
 * @author David Almeida Pitanguy data 14/09/2010
 */
public class TirarFoto extends Canvas implements CommandListener, Runnable {

	private Player player;
	private VideoControl videoControl;

	private Command capturaFoto;
	private Command salvarFoto;
	private Command voltar;

	private Form mostraFoto;

	private MIDlet midlet;

	private byte[] foto;

	private ManterFoto manterFoto;

	public TirarFoto(MIDlet midlet) {

		this.midlet = midlet;

		this.manterFoto = new ManterFoto();

		mostraFoto = new Form(Mensagens.FOTO);
		this.voltar = new Command(Mensagens.VOLTAR, Command.EXIT, 1);
		this.salvarFoto = new Command(Mensagens.SALVAR, Command.SCREEN, 1);
		mostraFoto.addCommand(this.voltar);
		mostraFoto.addCommand(this.salvarFoto);
		mostraFoto.setCommandListener(this);

		try {
			player = Manager.createPlayer("capture://video");
			player.realize();
		} catch (Exception e) {
			e.printStackTrace();
		}

		videoControl = (VideoControl) player.getControl("VideoControl");

		videoControl.initDisplayMode(VideoControl.USE_DIRECT_VIDEO, this);

		try {
			videoControl.setDisplayLocation(
					(getWidth() - videoControl.getDisplayWidth()) / 2,
					(getHeight() - videoControl.getDisplayHeight()) / 2);
			player.start();
		} catch (MediaException e) {
			e.printStackTrace();
		}

		videoControl.setVisible(true);

		capturaFoto = new Command("Capturar", Command.SCREEN, 1);

		addCommand(capturaFoto);
		addCommand(voltar);
		setCommandListener(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.CommandListener#commandAction(javax.microedition
	 * .lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {

		if (d == this) {

			if (c == this.capturaFoto) {
				new Thread(this).start();
			}
			if (c == this.voltar) {
				Display.getDisplay(midlet).setCurrent(new Menu(midlet));
			}

		} else if (d == mostraFoto) {
			if (c == this.salvarFoto) {
				salvarImagem();
			}

			mostraFoto.deleteAll();

			Display.getDisplay(midlet).setCurrent(this);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {

		foto = null;
		try {
			foto = this.videoControl.getSnapshot(null);

			Image image = Image.createImage(foto, 0, foto.length);

			// redimencionando imagem para mostrar na tela
			image = new ImagemUtil().redimencionarImagem(image, getWidth(),
					getHeight());

			mostraFoto.append(new ImageItem("Imagem Capturada", image,
					ImageItem.LAYOUT_CENTER, "Foto"));

			Display.getDisplay(midlet).setCurrent(mostraFoto);

		} catch (MediaException e) {
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.Canvas#paint(javax.microedition.lcdui.Graphics)
	 */
	protected void paint(Graphics g) {

		g.setColor(0x000000);
		g.fillRect(0, 0, getWidth(), getHeight());

	}

	/**
	 * Salva imagem fotografada.
	 */
	private void salvarImagem() {
		manterFoto.salvarFoto(foto);
	}

}
