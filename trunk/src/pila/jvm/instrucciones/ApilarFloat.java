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
public class ApilarFloat implements PseudoInstruccionJVM {

    float valor;

    public ApilarFloat(float valor) {
        this.valor = valor;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        if(valor == 0)
            cc.añadirU1(Constants.FCONST_0);
        else if(valor == 1)
            cc.añadirU1(Constants.FCONST_1);
        else if(valor == 2)
            cc.añadirU1(Constants.FCONST_2);
        else {
            cc.añadirU1(Constants.LDC);
            cc.añadirU2(cc.getConstanteIndex(valor));
        }
    }




}
