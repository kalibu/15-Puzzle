package puzzle.parabens;

/**
 * @author David Almeida Pitanguy
 * data 10/09/2010
 */
public class PontoVoador {

	public int cor;
	public int x;
	public int y;
	private int oscilacao = 0;
	
	private final int maxOscilacao = 10;
	private boolean esquerda = false;
	
	private int altura;
	
	public PontoVoador(int cor, int x, int y, int height) {
		this.cor = cor;
		this.x = x;
		this.y = y;
		
		this.altura = height;
	}
	
	public void atualizar(){
		y-=2;
		
		//se chegar ao topo reinicia
		if(y < 0){
			y = altura;
		}
		
		//oscila da esquerda para a direita
		if (esquerda){
			x--;
		}else{
			x++;
		}
		oscilacao++;
		if(oscilacao >= maxOscilacao){
			oscilacao = 0;
			esquerda = !esquerda;
		}
	}
}
