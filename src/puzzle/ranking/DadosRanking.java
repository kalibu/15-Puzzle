/**
 * 
 */
package puzzle.ranking;

/**
 * @author David Almeida Pitanguy
 * data 13/09/2010
 */
public class DadosRanking {

	private String nome;
	private long tempo;
	private long movimentos;
	
	public DadosRanking(String nome, long tempo, long movimentos) {
		super();
		this.nome = nome;
		this.tempo = tempo;
		this.movimentos = movimentos;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @return the tempo
	 */
	public long getTempo() {
		return tempo;
	}

	/**
	 * @return the movimentos
	 */
	public long getMovimentos() {
		return movimentos;
	}
}
