/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import com.sun.org.apache.bcel.internal.Constants;
import compilador.tablaSimbolos.InfoTs.Tipos;
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
                dirMetodo = cc.getPrintZIndex();
                break;
            case CHAR:
                dirMetodo = cc.getPrintCIndex();
                break;
            case REAL:
                dirMetodo = cc.getPrintFIndex();
                break;
            case ENTERO:
                dirMetodo = cc.getPrintIIndex();
                break;
            case NATURAL:
                dirMetodo = cc.getPrintIIndex();
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
