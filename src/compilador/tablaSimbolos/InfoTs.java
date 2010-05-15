package compilador.tablaSimbolos;


public class InfoTs {
    private enum Clase{tipo,var,proc};

    private Clase clase;
    private Tipo tipo;
    private int nivel;

    public InfoTs(Clase c, Tipo t, int n){
	this.clase = c;
	this.tipo = t;
        this.nivel = n;
}

    public Tipo getTipo() {
	return tipo;
    }
    public void setTipo(Tipo t){
        tipo=t;
    }
    public Clase getClase(){
        return clase;
    }
    public void setClase(Clase c){
        clase=c;
    }
    public int getNivel(){
        return nivel;
    }
    public void setNivel(int n){
        nivel=n;
    }
	
}
