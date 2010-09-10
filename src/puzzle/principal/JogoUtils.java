package puzzle.principal;

/**
 * Classe contem metodos para auxiliar o tratamento do jogo.
 * 
 * @author David Almeida Pitanguy
 * data 09/09/2010
 */
public class JogoUtils {

	/**
	 * @param pecas
	 *            Vetor com as pecas do jogo.
	 * @param qtdPecasPorColuna
	 *            Quantidade de peças por coluna.
	 * @return Retorna verdadeiro de ganhou e falso caso contrario.
	 */
	public boolean isGanhou(int pecas[][], int qtdPecasPorColuna) {

		int qtdPecas = (qtdPecasPorColuna * qtdPecasPorColuna) - 1;
		int aux = 0;

		for (int i = 0; i < pecas.length; i++) {
			for (int j = 0; j < pecas[i].length; j++) {
				aux++;
				if ((aux <= qtdPecas) && (pecas[i][j] != aux)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * @param pecasPorColuna
	 *            Quantidade de peças por coluna.
	 * @return Retorna o vetor carregado com o jogo atual.
	 */
	public int[][] carregarPecas(int pecasPorColuna) {
		int[][] pecas = new int[pecasPorColuna][pecasPorColuna];
		int aux = 1;
		for (int i = 0; i < pecas.length; i++) {
			for (int j = 0; j < pecas[i].length; j++) {
				pecas[i][j] = aux++;
			}
		}

		// seta ultima peça para 0
		pecas[pecasPorColuna - 1][pecasPorColuna - 1] = 0;

		return pecas;

	}

}
