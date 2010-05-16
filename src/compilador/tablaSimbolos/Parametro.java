/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.tablaSimbolos;


public class Parametro {
    private String modo;
    private TipoTs tipo;

    public Parametro(String m, TipoTs t){
        this.modo=m;
        this.tipo=t;
    }

    public String getModo(){
        return modo;
    }
    public TipoTs getTipo(){
        return tipo;
    }
}
