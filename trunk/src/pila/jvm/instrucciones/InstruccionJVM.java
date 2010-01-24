/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.ClassConstructor;

/**
 * Una instancia de esta clase es una instrucción que reconoce la jvm, por
 * lo que su funcion toClass es trivial
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class InstruccionJVM implements PseudoInstruccionJVM {

    int tipo;

    public InstruccionJVM(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void toClass(ClassConstructor cc) {
        cc.añadirU1(tipo);
    }

}
