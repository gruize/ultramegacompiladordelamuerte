/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = (T) value
 * ..., int value -> ..., T result
 */
public class I2T extends InstJvm {

    public I2T(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_BYTE:
            case Constantes.T_BOOLEAN:
                opcode = Constantes.I2B; //los booleans son como bytes
                break;
            case Constantes.T_SHORT:
                opcode = Constantes.I2S;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.I2F;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.I2L;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.I2D;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
