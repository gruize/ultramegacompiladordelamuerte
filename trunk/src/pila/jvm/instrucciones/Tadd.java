/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = value1 + value2
 * ..., T value1, T value2 -> ..., T result
 */
public class Tadd extends InstJvm {

    public Tadd(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.IADD;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LADD;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.FADD;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DADD;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
