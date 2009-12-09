/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.excepiones;

import pila.Dato;

public class DatoExc extends Exception {
    
    private Dato dato;

    public DatoExc(Dato dato) {
        super();
        this.dato = dato;
    }

    public DatoExc(Dato dato, String str) {
        super(str);
        this.dato = dato;
    }

}
