/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = value1 * value2
 * ..., T value1, T value2 -> ..., T result
 */
public class Tmul extends InstJvm {

    public Tmul(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.IMUL;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LMUL;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.FMUL;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DMUL;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}