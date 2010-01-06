/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * Devuelve T y acaba la llamada al metodo
 * ..., T value -> [empty]
 */
public class Treturn extends InstJvm {

    public Treturn(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.IRETURN;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LRETURN;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.FRETURN;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DRETURN;
                break;
            case Constantes.T_ADDRESS:
                opcode = Constantes.ARETURN;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
