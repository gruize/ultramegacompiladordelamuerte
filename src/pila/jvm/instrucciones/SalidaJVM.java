/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import compilador.tablaSimbolos.InfoTs.Tipos;
import org.apache.bcel.Constants;
import pila.interprete.datos.DatoPila;
import pila.jvm.ClassConstructor;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class SalidaJVM implements PseudoInstruccionJVM {
    
    Tipos tipoDato;

    public SalidaJVM(Tipos tipoDato) {
        this.tipoDato = tipoDato;
    }
    
    public void toClass(ClassConstructor cc) throws Exception {
        int dirMetodo;
        switch (tipoDato) {
            case BOOL:
                dirMetodo = cc.getPrintlnZIndex();
                break;
            case CHAR:
                dirMetodo = cc.getPrintlnCIndex();
                break;
            case REAL:
                dirMetodo = cc.getPrintlnFIndex();
                break;
            case ENTERO:
                dirMetodo = cc.getPrintlnIIndex();
                break;
            case NATURAL:
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
