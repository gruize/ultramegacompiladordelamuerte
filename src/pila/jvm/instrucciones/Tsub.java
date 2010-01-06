/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = value1 - value2
 * ..., T value1, T value2 -> ..., T result
 */
public class Tsub extends InstJvm {

    public Tsub(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.ISUB;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LSUB;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.FSUB;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DSUB;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
