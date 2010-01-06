/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import pila.jvm.Constantes;

/**
 * ar[index] = value
 * ..., arrayref ar, int index, T value -> ...
 */
public class Tastore extends InstJvm {

    /**
     *
     * @param tipo el tipo que de elementos del array (T_INT, T_FLOAT, etc de
     * Constantes)
     */
    public Tastore(byte tipo) throws Exception {
        switch(tipo) {
            case Constantes.T_BYTE:
            case Constantes.T_BOOLEAN:
                opcode = Constantes.BASTORE; //la misma para bytes y booleans
                break;
            case Constantes.T_SHORT:
                opcode = Constantes.SASTORE;
                break;
            case Constantes.T_INT:
                opcode = Constantes.IASTORE;
                break;
            case Constantes.T_LONG:
                opcode = Constantes.LASTORE;
                break;
            case Constantes.T_FLOAT:
                opcode = Constantes.FASTORE;
                break;
            case Constantes.T_DOUBLE:
                opcode = Constantes.DASTORE;
                break;
            case Constantes.T_CHAR:
                opcode = Constantes.CASTORE;
                break;
            case Constantes.T_ADDRESS:
                opcode = Constantes.AASTORE;
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }
}
