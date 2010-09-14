/**
 * 
 */
package puzzle.util;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

/**
 * Guarda os dados do jogo no banco
 * 
 * @author David Almeida Pitanguy data 14/09/2010
 */
public class DadosJogo {

	private final String BANCO = "dadosJogo";

	private int valor = 0;

	/**
	 * @return Retorna se o banco existe ou não.
	 */
	private boolean existeRS() {
		String[] bancos = RecordStore.listRecordStores();
		if (bancos != null) {
			for (int i = 0; i < bancos.length; i++) {
				if (bancos[i].equals(BANCO)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Salva o dado no banco.
	 * 
	 * @param valor
	 *            Valor do item a ser salvo.
	 */
	public void salvar(int valor) {
		this.valor = valor;

		try {
			if (existeRS()) {
				RecordStore.deleteRecordStore(BANCO);
			}
			RecordStore rs = RecordStore.openRecordStore(BANCO, true);

			String dado = String.valueOf(valor);

			rs.addRecord(dado.getBytes(), 0, dado.getBytes().length);

			rs.closeRecordStore();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Retorna o valor com a qtd de peças para jogar.
	 */
	public int getValor() {

		if (valor == 0) {
			// 4 é o valor padrao
			valor = 4;

			try {
				RecordStore rs = RecordStore.openRecordStore(BANCO, true);

				if (rs.getNumRecords() > 0) {
					valor = Integer.parseInt(new String(rs.getRecord(1)));
				}

				rs.closeRecordStore();
			} catch (RecordStoreFullException e) {
				e.printStackTrace();
			} catch (RecordStoreNotFoundException e) {
				e.printStackTrace();
			} catch (RecordStoreException e) {
				e.printStackTrace();
			}
		}

		return valor;
	}

}
