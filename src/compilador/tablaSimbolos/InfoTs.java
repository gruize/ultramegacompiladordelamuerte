package compilador.tablaSimbolos;


public class InfoTs {
	public enum Tipos{ERROR,ENTERO,NATURAL,REAL,CHAR,BOOL}
	private Tipos tipo;
	private int dir;
        private int nivel;    //verificarlos
        private String clase; //verificarlos
        private String modo;    //verificarlos
	
	
	public InfoTs(Tipos tipo, int dir){
		this.tipo=tipo;
		this.dir=dir;
	}
	
	public Tipos getTipo() {
		return tipo;
	}
        public int getNivel(){
            return nivel;
        }
        public String getClase(){
            return clase;
        }
        public String getModo(){
            return modo;
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
