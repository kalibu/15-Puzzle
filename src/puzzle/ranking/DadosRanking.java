/**
 * 
 */
package puzzle.ranking;

/**
 * Dados do ranking do jogo.
 * 
 * @author David Almeida Pitanguy data 13/09/2010
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

	public String getTempoFormatado() {
		long totalSegundos = getTempo() / 1000;
		long segundos = totalSegundos % 60;
		long minutos = totalSegundos / 60;
		long horas = minutos / 60;
		minutos = minutos % 60;
		
		horas = horas%24;

		return horas + "h" + minutos + "m" + segundos + "s";
	}
}
