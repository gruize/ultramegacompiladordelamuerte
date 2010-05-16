/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.tablaSimbolos;

import java.util.ArrayList;

public class TipoTS {
    private String t;
    private int tam;
    private ArrayList<Campo> campos;
    private ArrayList<Parametro> parametros;
    private String id;
    private int inicio;

    public TipoTS(){

    }
    public String getT(){
        return t;
    }
    public int getTam(){
        return tam;
    }
    public ArrayList<Campo> getCampos(){
        return campos;
    }
    public ArrayList<Parametro> getParametros(){
        return parametros;
    }
    public String getId(){
        return id;
    }
    public int getInicio(){
        return inicio;
    }
}
