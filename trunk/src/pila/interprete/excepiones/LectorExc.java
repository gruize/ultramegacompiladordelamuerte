/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.excepiones;

import pila.Dato;

public class LectorExc extends Exception {
    
    public LectorExc() {
        super();
    }

    public LectorExc(String str) {
        super(str);
    }

}
