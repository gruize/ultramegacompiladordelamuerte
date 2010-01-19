package compilador.tablaSimbolos;

public class InfoTs {
	private String tipo;
	private int dir;
	
	
	public InfoTs(String tipo, int dir){
		this.tipo=tipo;
		this.dir=dir;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	
	
}
