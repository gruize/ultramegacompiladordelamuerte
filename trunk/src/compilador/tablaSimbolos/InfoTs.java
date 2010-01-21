package compilador.tablaSimbolos;

import pila.interprete.datos.DatoPila;

public class InfoTs {
	public enum Tipos{ERROR,ENTERO,NATURAL,REAL,CHAR,BOOL}
	private Tipos tipo;
	private int dir;
	
	
	public InfoTs(Tipos tipo, int dir){
		this.tipo=tipo;
		this.dir=dir;
	}
	
	public Tipos getTipo() {
		return tipo;
	}
	public void setTipo(Tipos tipo) {
		this.tipo = tipo;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	
	
}
