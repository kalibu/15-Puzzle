/**
 * 
 */
package puzzle.util;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

/**
 * Guarda os dados do jogo no banco
 * 
 * @author David Almeida Pitanguy data 14/09/2010
 */
public class DadosJogo {

	// valores das posicoes do dados do jogo
	private final int QTD_PCS_JOGO = 1;
	private final int NUM_IMAGEM_SELECIONADA = 2;

	// valores padroes dos dados do jogo
	private final String VALOR_INICIAL_QTD_PCS_JOGO = "4";
	private final String VALOR_INICIAL_NUM_IMAGEM_SELECIONADA = "0";

	private final String BANCO = "dadosJogo";

	private int qtdPecasJogo = -1;
	private int numImagemSelecionada = -1;

	/**
	 * @param qtd
	 *            Quantidade de pçs a serem salvas
	 */
	public void salvarQtdPecasJogo(int qtd) {
		qtdPecasJogo = qtd;
		salvar(QTD_PCS_JOGO, qtd);
	}

	/**
	 * @param cod
	 *            Codigo da imagem a ser salvo.
	 */
	public void salvarImagemSelecionada(int cod) {
		numImagemSelecionada = cod;
		salvar(NUM_IMAGEM_SELECIONADA, cod);
	}

	/**
	 * Salva o dado no banco.
	 * 
	 * @param dado
	 *            Dado a ser salvo.
	 * @param valor
	 *            Valor do item a ser salvo.
	 */

	private void salvar(int dado, int valor) {

		try {
			RecordStore rs = RecordStore.openRecordStore(BANCO, true);

			// se o banco estiver vazio, inicializa ele com valores iniciais
			if (rs.getNumRecords() == 0) {
				inicializarBanco(rs);
			}

			String valorS = String.valueOf(valor);

			rs.setRecord(dado, valorS.getBytes(), 0, valorS.getBytes().length);

			rs.closeRecordStore();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicializa os valores caso for a primeira vez a jogar. Obs: as posições
	 * são importantes, pois são prefixadas
	 * 
	 * @param rs
	 *            Record store aonde seram salvos os dados
	 * @throws RecordStoreNotOpenException
	 * @throws RecordStoreFullException
	 * @throws RecordStoreException
	 */
	private void inicializarBanco(RecordStore rs)
			throws RecordStoreNotOpenException, RecordStoreFullException,
			RecordStoreException {

		rs.addRecord(VALOR_INICIAL_QTD_PCS_JOGO.getBytes(), 0,
				VALOR_INICIAL_QTD_PCS_JOGO.getBytes().length);
		rs.addRecord(VALOR_INICIAL_NUM_IMAGEM_SELECIONADA.getBytes(), 0,
				VALOR_INICIAL_NUM_IMAGEM_SELECIONADA.getBytes().length);

	}

	/**
	 * @return Retorna o valor do dado passado.
	 */
	private int getValor(int dado) {

		int valor = 0;

		try {
			RecordStore rs = RecordStore.openRecordStore(BANCO, true);

			// /se o banco estiver vazio, inicializa ele com valores iniciais
			if (rs.getNumRecords() == 0) {
				inicializarBanco(rs);
			}

			valor = Integer.parseInt(new String(rs.getRecord(dado)));

			rs.closeRecordStore();
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

		return valor;
	}

	/**
	 * @return Retorna a quantidade de pcs para o jogo
	 */
	public int getQtdPcsJogo() {
		if (qtdPecasJogo == -1) {
			qtdPecasJogo = getValor(QTD_PCS_JOGO);
		}
		return qtdPecasJogo;
	}

	/**
	 * @return Retorna o numero da imagem selecionada
	 */
	public int getNumImagemSelecionada() {
		if (numImagemSelecionada == -1) {
			numImagemSelecionada = getValor(NUM_IMAGEM_SELECIONADA);
		}
		return numImagemSelecionada;
	}

}
