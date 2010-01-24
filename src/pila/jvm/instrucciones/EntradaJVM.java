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
public class EntradaJVM implements PseudoInstruccionJVM{
    int tipoDato;
    int dirVar;

    public EntradaJVM(int tipoDato, int dirVar) {
        this.tipoDato = tipoDato;
        this.dirVar = dirVar;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        cc.leerDato(tipoDato, dirVar);
    }


}
