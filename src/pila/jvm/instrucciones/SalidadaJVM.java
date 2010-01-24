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
public class SalidadaJVM implements PseudoInstruccionJVM {
    
    int tipoDato;

    public SalidadaJVM(int tipoDato) {
        this.tipoDato = tipoDato;
    }
    
    public void toClass(ClassConstructor cc) throws Exception {
        cc.escribirDato(tipoDato);
    }

}
