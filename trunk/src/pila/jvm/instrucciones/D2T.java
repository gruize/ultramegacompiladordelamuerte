/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = (T) value
 * ..., double value -> ..., T result
 */
public class D2T extends InstJvm {

    public D2T(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.D2I;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.D2L;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.D2F;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}