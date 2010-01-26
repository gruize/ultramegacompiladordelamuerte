/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.ClassConstructor;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class InstruccionJVMU2 extends InstruccionJVM {
    int dato;

    public InstruccionJVMU2(int tipo, int dato) {
        super(tipo);
        this.dato = dato;
    }
    
    public void toClass(ClassConstructor cc) {
        super.toClass(cc);
        cc.añadirU2(dato);
    }

    @Override
    public int dameU1s() {
        return super.dameU1s()+2;
    }




}
