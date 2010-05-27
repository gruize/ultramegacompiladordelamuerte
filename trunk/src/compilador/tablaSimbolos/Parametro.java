/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.tablaSimbolos;


public class Parametro implements Cloneable {
    private String modo;
    private TipoTs tipo;
    private int dir;

    public Parametro(String m, TipoTs t, int dir){
        this.modo=m;
        this.tipo=t;
        this.dir=dir;
    }

    public Object clone(){
    	Parametro p=new Parametro(modo,tipo,dir);
    	return p;
    }
    
    public String getModo(){
        return modo;
    }
    public TipoTs getTipo(){
        return tipo;
    }
    public int getDir(){
        return dir;
    }
}
