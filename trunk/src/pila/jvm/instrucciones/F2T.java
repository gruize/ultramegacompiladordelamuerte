/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = (T) value
 * ..., float value -> ..., T result
 */
public class F2T extends InstJvm {

    public F2T(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.F2I;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.F2L;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.F2D;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}