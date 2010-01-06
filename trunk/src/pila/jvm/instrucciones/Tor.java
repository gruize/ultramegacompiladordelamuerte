/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = value1 | value2
 * ..., T value1, T value2 -> ..., T result
 */
public class Tor extends InstJvm {

    public Tor(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.IOR;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LOR;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}