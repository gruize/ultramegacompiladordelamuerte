/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * Carga un valor desde la posicion indez del array array en la pila:
 * ..., arrayref array, int index -> ..., T value
 */
public class Taload extends InstJvm {

    /**
     * 
     * @param tipo el tipo de valor que se cargara en la pila (T_INT, T_FLOAT,
     * etc de Constantes)
     */
    public Taload(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_BYTE:
            case Constantes.T_BOOLEAN:
                opcode = Constantes.BALOAD; //la misma para bytes y booleans
                break;
            case Constantes.T_SHORT:
                opcode = Constantes.SALOAD;
                break;
            case Constantes.T_INT:
                opcode = Constantes.IALOAD;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LALOAD;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.FALOAD;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DALOAD;
                break;
            case Constantes.T_CHAR:
                opcode = Constantes.CALOAD;
                break;
            case Constantes.T_ADDRESS:
                opcode = Constantes.AALOAD;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
