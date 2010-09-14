/**
 * 
 */
package puzzle.util;

import javax.microedition.rms.RecordStore;

/**
 * Metodos para auxiliar o acesso ao banco.
 * 
 * @author David Almeida Pitanguy data 14/09/2010
 */
public class BancoUtil {
	/**
	 * @param nome
	 *            Nome do banco a verificar.
	 * @return Retorna se o banco existe ou não.
	 */
	public static boolean existeRS(String nome) {
		String[] bancos = RecordStore.listRecordStores();
		if (bancos != null) {
			for (int i = 0; i < bancos.length; i++) {
				if (bancos[i].equals(nome)) {
					return true;
				}
			}
		}
		return false;
	}
}
