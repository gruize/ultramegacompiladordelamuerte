/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.jvm.Constantes;
import pila.jvm.Tabla;
import pila.jvm.U1;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class Tconst extends InstJvm {

    public Tconst(byte tipo, byte val) throws Exception {
        switch(tipo) {
            case Constantes.T_INT:
                switch(val) {
                    case -1:
                        opcode = Constantes.ICONST_M1;
                        break;
                    case 0:
                        opcode = Constantes.ICONST_0;
                        break;
                    case 1:
                        opcode = Constantes.ICONST_1;
                        break;
                    case 2:
                        opcode = Constantes.ICONST_2;
                        break;
                    case 3:
                        opcode = Constantes.ICONST_3;
                        break;
                    case 4:
                        opcode = Constantes.ICONST_4;
                        break;
                    default:
                        throw new Exception("Valor "+val+" inválido");
                }
                break;
            case Constantes.T_LONG:
                switch(val) {
                    case 0:
                        opcode = Constantes.LCONST_0;
                        break;
                    case 1:
                        opcode = Constantes.LCONST_1;
                        break;
                    default:
                        throw new Exception("Valor "+val+" inválido");
                }
                break;
            case Constantes.T_FLOAT:
                switch(val) {
                    case 0:
                        opcode = Constantes.FCONST_0;
                        break;
                    case 1:
                        opcode = Constantes.FCONST_1;
                        break;
                    case 2:
                        opcode = Constantes.FCONST_2;
                        break;
                    default:
                        throw new Exception("Valor "+val+" inválido");
                }
                break;
            case Constantes.T_DOUBLE:
                switch(val) {
                    case 0:
                        opcode = Constantes.DCONST_0;
                        break;
                    case 1:
                        opcode = Constantes.DCONST_1;
                        break;
                    default:
                        throw new Exception("Valor "+val+" inválido");
                }
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }

    }

    public Tconst(byte tipo) throws Exception {
        if(tipo == Constantes.T_ADDRESS)
            opcode = Constantes.ACONST_NULL;
        else
            throw new Exception("Tcons sin valor solo puede llamarse con tipo "+Constantes.T_ADDRESS);
    }

}
