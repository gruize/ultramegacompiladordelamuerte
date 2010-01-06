/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = -value
 * ..., T value1 -> ..., T result
 */
public class Tneg extends InstJvm {

    public Tneg(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.INEG;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LNEG;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.FNEG;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DNEG;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
