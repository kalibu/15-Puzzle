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

	//IMAGENS
	public final static String FUNDO = "fundo";
	public final static String INICIAR = "iniciar";
	public final static String NAO = "nao";
	public final static String RANKING = "ranking";
	public final static String SAIR = "sair";
	public final static String SIM = "sim";

	public final static String SELECIONADO = "_selecionado";
	
	//CONFIGURAÇÃO - USO INTERNO DA CLASSE
	public final static String CAMINHO = "/";
	private final String TIPO_IMAGEM = ".png";

	public String getCaminhoImagem(String imagem) {
		return CAMINHO + imagem + TIPO_IMAGEM;
	}
}
