/**
 * 
 */
package puzzle.foto;

import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

import puzzle.util.DadosJogo;

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

	/**
	 * @param foto
	 *            Id da foto a ser carregada.
	 * @return Retorna uma imagem com a foto que foi carregada.
	 */
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

	/**
	 * @param foto
	 *            Id da foto a ser deletada.
	 */
	public void deletaFoto(int foto) {

		verificaFotoSelecionada(foto);

		try {
			byte[][] records;

			RecordStore rs = RecordStore.openRecordStore(BANCO, true);

			records = new byte[rs.getNumRecords()][];

			// carrega tds os records em um array e depois salva o mesmo, para
			// evitar buracos entre ids
			for (int i = 1; i <= rs.getNumRecords(); i++) {
				records[i - 1] = rs.getRecord(i);
			}

			rs.closeRecordStore();

			// deleta rs antigo
			RecordStore.deleteRecordStore(BANCO);

			// abre novo rs para salvar novas fotos
			rs = RecordStore.openRecordStore(BANCO, true);

			for (int i = 0; i < records.length; i++) {
				if (i != (foto - 1)) {
					rs.addRecord(records[i], 0, records[i].length);
				}
			}

			rs.closeRecordStore();

		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Retorna todas as imagens da camera.
	 */
	public Image[] getTodasImagens() {
		Image[] imagens = null;

		try {
			RecordStore rs = RecordStore.openRecordStore(BANCO, true);

			imagens = new Image[rs.getNumRecords()];

			for (int i = 0; i < imagens.length; i++) {
				byte[] fotoBytes = rs.getRecord(i + 1);
				imagens[i] = Image.createImage(fotoBytes, 0, fotoBytes.length);
			}

			rs.closeRecordStore();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

		return imagens;
	}

	/**
	 * Verifica se foto selecionada é a que vai ser excluida, caso for alterar
	 * foto selecionada para foto padrão.
	 */
	private void verificaFotoSelecionada(int foto) {
		DadosJogo dadosJogo = new DadosJogo();

		int numImagemSelecionada = dadosJogo.getNumImagemSelecionada();

		if (numImagemSelecionada == foto) {
			dadosJogo.salvarImagemSelecionada(0);
		}
	}
}
