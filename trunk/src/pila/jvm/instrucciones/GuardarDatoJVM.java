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
public class GuardarDatoJVM implements PseudoInstruccionJVM {

    int tipoDato, dir;

    public GuardarDatoJVM(int tipoDato, int dir) {
        this.tipoDato = tipoDato;
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public int getTipoDato() {
        return tipoDato;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        cc.guardarDato(tipoDato, dir);
    }

}
