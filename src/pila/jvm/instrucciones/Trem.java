/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * result = value1 % value2
 * ..., T value1, T value2 -> ..., T result
 */
public class Trem extends InstJvm {

    public Trem(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                opcode = Constantes.IREM;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LREM;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.FREM;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DREM;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}