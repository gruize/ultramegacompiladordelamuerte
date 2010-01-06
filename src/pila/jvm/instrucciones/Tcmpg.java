/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * if(value1 es NAN || value2 is NAN) result = 1
 * if(value1 mayor value2) result = 1
 * else if(value1 menor value2) result = -1
 * else result = 0
 *
 * ..., T value1, T value2 -> ..., int result
 */
public class Tcmpg extends InstJvm {

    public Tcmpg(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_FLOAT:
                opcode = Constantes.FCMPG;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DCMPG;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
