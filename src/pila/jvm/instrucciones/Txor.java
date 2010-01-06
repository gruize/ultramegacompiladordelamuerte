/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = value1 XOR value2
 * ..., T value1, T value2 -> ..., T result
 */
public class Txor extends InstJvm {

    public Txor(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.IXOR;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LXOR;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}