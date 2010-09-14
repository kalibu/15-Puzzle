/**
 * 
 */
package puzzle.foto;

import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

import puzzle.util.BancoUtil;

/**
 * Responsavel por saltar e excluir fotos.
 * 
 * @author David Almeida Pitanguy data 14/09/2010
 */
public class ManterFoto {

	private final String BANCO = "fotos";

	/**
	 * Salva o foto no banco.
	 * 
	 * @param foto
	 *            Foto a ser inserida no banco.
	 */
	public void salvarFoto(byte[] foto) {
		try {
			if (BancoUtil.existeRS(BANCO)) {
				RecordStore.deleteRecordStore(BANCO);
			}
			RecordStore rs = RecordStore.openRecordStore(BANCO, true);

			rs.addRecord(foto, 0, foto.length);

			rs.closeRecordStore();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

	}

}
