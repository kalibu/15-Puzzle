/**
 * 
 */
package puzzle.util;

import javax.microedition.lcdui.Image;

/**
 * Responsavel por tratar imagens.
 * 
 * @author David Almeida Pitanguy data 15/09/2010
 */
public class ImagemUtil {

	private int[] reescalaArray(int[] ini, int x, int y, int x2, int y2) {
		int out[] = new int[x2 * y2];

		for (int yy = 0; yy < y2; yy++) {
			int dy = yy * y / y2;

			for (int xx = 0; xx < x2; xx++) {
				int dx = xx * x / x2;
				out[(x2 * yy) + xx] = ini[(x * dy) + dx];
			}
		}
		return out;
	}

	public Image redimencionarImagem(Image imagem, int novaLargura, int novaAltura) {

		// array para guardar o rgb da imagem original
		int rgb[] = new int[imagem.getWidth() * imagem.getHeight()];
		// carrega array com rgb da imagem
		imagem.getRGB(rgb, 0, imagem.getWidth(), 0, 0, imagem.getWidth(),
				imagem.getHeight());
		// Reescala array para transformar imagem
		int rgb2[] = reescalaArray(rgb, imagem.getWidth(), imagem.getHeight(),
				novaLargura, novaAltura);
		// Cria imagem de retorno com novas configurações
		return Image.createRGBImage(rgb2, novaLargura, novaAltura, true);
	}

}
