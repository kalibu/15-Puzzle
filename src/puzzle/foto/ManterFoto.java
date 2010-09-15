/**
 * 
 */
package puzzle.foto;

import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

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
			RecordStore rs = RecordStore.openRecordStore(BANCO, true);

			rs.addRecord(foto, 0, foto.length);

			rs.closeRecordStore();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	public Image carregarFoto(int foto) {
		Image imagem = null;

		try {
			RecordStore rs = RecordStore.openRecordStore(BANCO, true);

			byte[] fotoBytes = rs.getRecord(foto);

			imagem = Image.createImage(fotoBytes, 0, fotoBytes.length);

			rs.closeRecordStore();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

		return imagem;
	}

}
