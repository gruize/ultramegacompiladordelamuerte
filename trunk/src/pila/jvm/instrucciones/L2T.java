/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = (T) value
 * ..., long value -> ..., T result
 */
public class L2T extends InstJvm {

    public L2T(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.L2I;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.L2F;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.L2D;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
