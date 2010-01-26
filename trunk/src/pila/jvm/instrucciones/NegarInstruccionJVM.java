/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import org.apache.bcel.Constants;
import pila.jvm.ClassConstructor;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class NegarInstruccionJVM implements PseudoInstruccionJVM {

    public int dameU1s() {
        return 3;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        cc.añadirU1(Constants.INVOKESTATIC);
        cc.añadirU2(cc.getNotMethodIndex());
    }





}
