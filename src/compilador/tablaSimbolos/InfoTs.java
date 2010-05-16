package compilador.tablaSimbolos;


public class InfoTs {

    private String clase;
    private TipoTS tipo;
    private int dir;
    private int nivel;

    public InfoTs(String c, TipoTS t, int n){
	this.clase = c;
	this.tipo = t;
        this.nivel = n;
}

    public TipoTS getTipo() {
	return tipo;
    }
    public void setTipo(TipoTS t){
        tipo=t;
    }
    public String getClase(){
        return clase;
    }
    public void setClase(String c){
        clase=c;
    }
    public int getNivel(){
        return nivel;
    }
    public void setNivel(int n){
        nivel=n;
    }
    public int getDir(){
        return dir;
    }
	
}
