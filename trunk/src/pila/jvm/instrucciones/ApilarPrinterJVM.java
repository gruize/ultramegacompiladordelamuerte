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
public class ApilarPrinterJVM implements PseudoInstruccionJVM{

    public void toClass(ClassConstructor cc) {
        cc.añadirU1(Constants.GETSTATIC);
        cc.añadirU2(cc.getPrinterFieldIndex());
    }

    public int dameU1s() {
        return 3;
    }



}
