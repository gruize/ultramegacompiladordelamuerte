/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.tablaSimbolos;


public class Parametro {
    private String modo;
    private TipoTs tipo;
    private int dir;

    public Parametro(String m, TipoTs t, int dir){
        this.modo=m;
        this.tipo=t;
        this.dir=dir;
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
