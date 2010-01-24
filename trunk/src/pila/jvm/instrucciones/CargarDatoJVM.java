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
public class CargarDatoJVM implements PseudoInstruccionJVM {

    int tipoDato, dir;

    public CargarDatoJVM(int tipoDato, int dir) {
        this.tipoDato = tipoDato;
        this.dir = dir;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        cc.cargarDato(tipoDato, dir);
    }

}
