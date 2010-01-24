/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.Instruccion;
import pila.jvm.ClassConstructor;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public interface PseudoInstruccionJVM extends Instruccion {

    public void toClass(ClassConstructor cc) throws Exception;
}
