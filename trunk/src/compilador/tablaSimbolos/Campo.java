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
    private TipoTS tipo;
    private int desp;

    public Campo(){

    }

    public String getId(){
        return id;
    }
    public TipoTS getTipo(){
        return tipo;
    }
    public int getDesp(){
        return desp;
    }
}
