/**
 * 
 */
package puzzle.util;

/**
 * Guarda o caminho para as todas as imagens do jogo.
 * 
 * @author David Almeida Pitanguy data 13/09/2010
 */
public class Imagens {

	// IMAGENS
	public final static String AUDIO_ON = "audio_on";
	public final static String AUDIO_OFF = "audio_off";
	public final static String CONFIGURACAO = "botoes/configuracao";
	public final static String CREDITOS = "botoes/creditos";
	public final static String FUNDO = "fundo";
	public final static String IMAGEM_PADRAO = "imagensPadrao/imagemPadrao_";
	public final static String INICIAR = "botoes/iniciar";
	public final static String NAO = "botoes/nao";
	public final static String RANKING = "botoes/ranking";
	public final static String SAIR = "botoes/sair";
	public final static String SIM = "botoes/sim";
	public final static String TIRAR_FOTO = "botoes/tirarFoto";

	public final static String SELECIONADO = "_selecionado";

	// CONFIGURAÇÃO - USO INTERNO DA CLASSE
	public final static String CAMINHO = "/";
	private final String TIPO_IMAGEM = ".png";

	/**
	 * @param imagem
	 *            Nome da imagem a carregar o caminho.
	 * @return Retorna a imagem com seu respectivo caminho.
	 */
	public String getCaminhoImagem(String imagem) {
		return CAMINHO + imagem + TIPO_IMAGEM;
	}
}
