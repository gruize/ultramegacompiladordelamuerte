package compilador.tablaSimbolos;


public class InfoTs {

    private String clase;
    private TipoTs tipo;
    private int dir;
    private int nivel;

    public InfoTs(String c, TipoTs t, int n,int d){
	this.clase = c;
	this.tipo = t;
        this.nivel = n;
        this.dir = d;
    }
    public InfoTs(){

    }

    public TipoTs getTipo() {
	return tipo;
    }
    public void setTipo(TipoTs t){
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
    public void setDir(int d){
        dir=d;
    }
    public InfoTs clone(InfoTs props){
        InfoTs res = new InfoTs();
        if ( props == null){
            return null;
        }
        res.clase=props.getClase();
        res.dir = props.getDir();
        res.nivel = props.getNivel();
        res.tipo.clone(props.tipo);
        return res;
    }
	
}
