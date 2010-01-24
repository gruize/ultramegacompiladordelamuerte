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
public class ApilarPrinterJVM implements PseudoInstruccionJVM{

    public void toClass(ClassConstructor cc) {
        cc.apilarPrinter();
    }

}
