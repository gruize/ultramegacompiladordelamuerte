/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.excepiones;

import pila.interprete.instrucciones.InstruccionInterprete;

public class InstruccionExc extends Exception {
    
    private InstruccionInterprete inst;

    public InstruccionExc(InstruccionInterprete inst) {
        super();
        this.inst = inst;
    }

    public InstruccionExc(InstruccionInterprete inst, String str) {
        super(str);
        this.inst = inst;
    }

}
