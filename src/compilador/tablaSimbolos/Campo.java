/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.tablaSimbolos;

import java.util.ArrayList;

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
    public static boolean existeCampo(ArrayList<Campo> campos, String id){
        for (int i=0; i< campos.size(); i++){
            Campo aux = campos.get(i);
            if (aux.getId().equals(id))
                return true;
        }
        return false;
    }
}
