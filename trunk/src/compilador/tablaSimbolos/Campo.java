/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.tablaSimbolos;

/**
 *
 * @author Paula
 */
public class Campo {
    private String id;
    private TipoTs tipo;
    private int desp;

    public Campo(){

    }
    public Campo(String id, TipoTs tipo, int desp){
        this.id=id;
        this.tipo=tipo;
        this.desp=desp;
    }

    public String getId(){
        return id;
    }
    public TipoTs getTipo(){
        return tipo;
    }
    public int getDesp(){
        return desp;
    }
}
