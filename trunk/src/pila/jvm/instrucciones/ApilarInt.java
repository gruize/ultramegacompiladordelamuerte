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
public class ApilarInt implements PseudoInstruccionJVM {

    int valor;

    public ApilarInt(int valor) {
        this.valor = valor;
    }
    
    public void toClass(ClassConstructor cc) throws Exception {
        switch(valor) {
            case -1:
                cc.añadirU1(Constants.ICONST_M1);
                break;
            case 0:
                cc.añadirU1(Constants.ICONST_0);
                break;
            case 1:
                cc.añadirU1(Constants.ICONST_1);
                break;
            case 2:
                cc.añadirU1(Constants.ICONST_2);
                break;
            case 3:
                cc.añadirU1(Constants.ICONST_3);
                break;
            case 4:
                cc.añadirU1(Constants.ICONST_4);
                break;
            case 5:
                cc.añadirU1(Constants.ICONST_5);
                break;
            default:
                if(valor >= Byte.MIN_VALUE && valor <= Byte.MAX_VALUE) {
                    cc.añadirU1(Constants.BIPUSH);
                    cc.añadirU2(valor);
                }
                else if(valor >= Short.MIN_VALUE && valor <= Short.MAX_VALUE) {
                    cc.añadirU1(Constants.SIPUSH);
                    cc.añadirU2(valor);
                }
                else {
                    cc.añadirU1(Constants.LDC);
                    cc.añadirU2(cc.getConstanteIndex(valor));
                }
        }
    }

}
