/**
 * 
 */
package puzzle.ranking;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Ticker;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreFullException;
import javax.microedition.rms.RecordStoreNotFoundException;

import puzzle.menu.Menu;
import puzzle.util.Mensagens;

/**
 * Ranking do jogo.
 * 
 * @author David Almeida Pitanguy data 13/09/2010
 */
public class Ranking extends Canvas implements CommandListener {

	private MIDlet midlet;

	private final int QTD_MAX_RANKING = 100;

	private final String BANCO_15 = "15_PUZZLE_BANCO";

	private final int QTD_DADOS = 3;

	private Command voltar;

	public Ranking(MIDlet midlet) {
		setTicker(new Ticker("Ranking"));

		this.voltar = new Command(Mensagens.VOLTAR, Command.SCREEN, 1);
		this.addCommand(voltar);
		this.setCommandListener(this);

		this.midlet = midlet;
	}

	/**
	 * @param g
	 */
	protected void paint(Graphics g) {

		g.setColor(0xFFFFFF);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(0x000000);
		int y = 30;
		DadosRanking[] dados = carregaLista(getNomeBanco());
		for (int i = 0; i < dados.length; i++) {
			g.drawString("Nome   -   Jogadas   -   Tempo", getWidth() / 2, 10,
					Graphics.HCENTER | Graphics.BASELINE);
			g.drawString(getRankingFormatado(dados[i]), getWidth() / 2, y,
					Graphics.HCENTER | Graphics.BASELINE);
			y += 10;
		}
	}

	/**
	 * @param nome
	 *            Nome do banco a verificar.
	 * @return Retorna se o banco existe ou não.
	 */
	private boolean existeRS(String nome) {
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

	/**
	 * Salva os dados no banco. Obs: A ordem dos dados importa.
	 * 
	 * @param lista
	 *            Recebe a lista a ser inserida.
	 * @param banco
	 *            Recebe o nome do banco no qual sera inserido.
	 */
	private void salvar(DadosRanking[] lista, String banco) {
		try {
			if (existeRS(banco)) {
				RecordStore.deleteRecordStore(banco);
			}
			RecordStore rs = RecordStore.openRecordStore(banco, true);
			for (int i = 0; i < lista.length; i++) {
				DadosRanking dado = lista[i];
				rs.addRecord(dado.getNome().getBytes(), 0, dado.getNome()
						.getBytes().length);
				rs.addRecord(String.valueOf(dado.getTempo()).getBytes(), 0,
						String.valueOf(dado.getTempo()).getBytes().length);
				rs.addRecord(String.valueOf(dado.getMovimentos()).getBytes(),
						0,
						String.valueOf(dado.getMovimentos()).getBytes().length);
			}
			rs.closeRecordStore();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o ranking completo deste banco. Obs: A ordem dos dados importa.
	 * 
	 * @param nomeBanco
	 *            Nome do banco do qual seram carregados os dados.
	 * @return Retorna o ranking completo do jogo.
	 */
	private DadosRanking[] carregaLista(String nomeBanco) {
		DadosRanking[] dados = null;
		try {
			RecordStore rs = RecordStore.openRecordStore(nomeBanco, true);

			dados = new DadosRanking[rs.getNumRecords() / QTD_DADOS];

			int posVetor = 0;

			for (int i = 1; i <= rs.getNumRecords(); i += QTD_DADOS) {
				String nome = new String(rs.getRecord(i));
				long tempo = Long.parseLong(new String(rs.getRecord(i + 1)));
				long movimentos = Long
						.parseLong(new String(rs.getRecord(i + 2)));

				dados[posVetor++] = new DadosRanking(nome, tempo, movimentos);
			}
			rs.closeRecordStore();
		} catch (RecordStoreFullException e) {
			e.printStackTrace();
		} catch (RecordStoreNotFoundException e) {
			e.printStackTrace();
		} catch (RecordStoreException e) {
			e.printStackTrace();
		}

		return dados;
	}

	/**
	 * Adiciona um ranking.
	 * 
	 * @param record
	 *            Record a ser inserido.
	 * @param nomeBanco
	 *            Nome do banco no qual sera inserido.
	 * @return Retorna verdadeiro caso for inserido e falso caso contrario.
	 */
	public boolean addRecord(DadosRanking record) {
		DadosRanking[] lista = carregaLista(getNomeBanco());
		boolean adicionou = false;

		// insere ordenado na lista
		for (int i = 0; i < lista.length; i++) {
			if (lista[i].getMovimentos() > record.getMovimentos()) {
				DadosRanking aux = lista[i];
				lista[i] = record;
				record = aux;
				adicionou = true;
			}
		}

		// caso ainda ñ tiver completado o ranking adiciona o ultimo resultado
		// ao final
		if (lista.length < QTD_MAX_RANKING) {
			DadosRanking[] listaAux = lista;
			lista = new DadosRanking[listaAux.length + 1];

			for (int i = 0; i < listaAux.length; i++) {
				lista[i] = listaAux[i];
			}

			lista[lista.length - 1] = record;

			adicionou = true;
		}

		if (adicionou) {
			salvar(lista, getNomeBanco());
		}

		return adicionou;
	}

	/**
	 * @param movimentos
	 *            Quantidade de movimentos executador pelo jogador.
	 * @return Retorna verdadeiro caso o jogador possa ser adicionado ao banco.
	 */
	public boolean isPodeAdicionar(long movimentos) {
		DadosRanking[] dados = carregaLista(getNomeBanco());

		if ((dados.length < QTD_MAX_RANKING)
				|| (dados[dados.length - 1].getMovimentos() > movimentos)) {
			return true;
		}
		return false;
	}

	/**
	 * @param d
	 *            Dados a serem formatados.
	 * @return Retorna uma string com os dados do ranking formatado.
	 */
	private String getRankingFormatado(DadosRanking d) {
		String dado = d.getNome() + " - " + d.getMovimentos() + " - "
				+ d.getTempoFormatado();

		return dado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.microedition.lcdui.CommandListener#commandAction(javax.microedition
	 * .lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == voltar) {
			Display.getDisplay(this.midlet).setCurrent(new Menu(this.midlet));
		}
	}
	
	/**
	 * @return Retorna o nome correto do banco.
	 */
	private String getNomeBanco(){
		return BANCO_15;
	}
}
