/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import com.sun.org.apache.bcel.internal.Constants;
import pila.jvm.ClassConstructor;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class YInstruccionJVM implements PseudoInstruccionJVM {

    public int dameU1s() {
        return 3;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        cc.añadirU1(Constants.INVOKESTATIC);
        cc.añadirU2(cc.getAndMethodIndex());
    }





}