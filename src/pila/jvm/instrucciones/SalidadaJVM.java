/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import org.apache.bcel.Constants;
import pila.interprete.datos.DatoPila;
import pila.jvm.ClassConstructor;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class SalidadaJVM implements PseudoInstruccionJVM {
    
    int tipoDato;

    public SalidadaJVM(int tipoDato) {
        this.tipoDato = tipoDato;
    }
    
    public void toClass(ClassConstructor cc) throws Exception {
        int dirMetodo;
        switch (tipoDato) {
            case DatoPila.BOOL_T:
                dirMetodo = cc.getPrintlnZIndex();
                break;
            case DatoPila.CHAR_T:
                dirMetodo = cc.getPrintlnCIndex();
                break;
            case DatoPila.FLOAT_T:
                dirMetodo = cc.getPrintlnFIndex();
                break;
            case DatoPila.INT_T:
                dirMetodo = cc.getPrintlnIIndex();
                break;
            case DatoPila.NAT_T:
                dirMetodo = cc.getPrintlnIIndex();
                break;
            default:
                throw new Exception("Tipo de dato a escribir inválido");
        }
        cc.añadirU1(Constants.INVOKEVIRTUAL);
        cc.añadirU2(dirMetodo);
    }

    public int dameU1s() {
        return 3;
    }



}
