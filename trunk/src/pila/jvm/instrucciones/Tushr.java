/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = value1 >> value2 pero desplazamiento logico (sin extender signo)
 * ..., T value1, T value2 -> ..., T result
 */
public class Tushr extends InstJvm {

    public Tushr(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.IUSHR;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LUSHR;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
