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
public class Tstore extends InstJvmConArg {

    private byte arg;
    public Tstore(byte tipo, byte varIndex) throws Exception {
        setTieneArgumento(false);
        switch(tipo) {
            case Constantes.T_INT:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.ISTORE_0;
                        break;
                    case 1:
                        opcode = Constantes.ISTORE_1;
                        break;
                    case 2:
                        opcode = Constantes.ISTORE_2;
                        break;
                    case 3:
                        opcode = Constantes.ISTORE_3;
                        break;
                    default:
                        opcode = Constantes.ISTORE;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            case Constantes.T_LONG:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.LSTORE_0;
                        break;
                    case 1:
                        opcode = Constantes.LSTORE_1;
                        break;
                    case 2:
                        opcode = Constantes.LSTORE_2;
                        break;
                    case 3:
                        opcode = Constantes.LSTORE_3;
                        break;
                    default:
                        opcode = Constantes.LSTORE;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            case Constantes.T_FLOAT:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.FSTORE_0;
                        break;
                    case 1:
                        opcode = Constantes.FSTORE_1;
                        break;
                    case 2:
                        opcode = Constantes.FSTORE_2;
                        break;
                    case 3:
                        opcode = Constantes.FSTORE_3;
                        break;
                    default:
                        opcode = Constantes.FSTORE;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            case Constantes.T_DOUBLE:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.DSTORE_0;
                        break;
                    case 1:
                        opcode = Constantes.DSTORE_1;
                        break;
                    case 2:
                        opcode = Constantes.DSTORE_2;
                        break;
                    case 3:
                        opcode = Constantes.DSTORE_3;
                        break;
                    default:
                        opcode = Constantes.DSTORE;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            case Constantes.T_ADDRESS:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.ASTORE_0;
                        break;
                    case 1:
                        opcode = Constantes.ASTORE_1;
                        break;
                    case 2:
                        opcode = Constantes.ASTORE_2;
                        break;
                    case 3:
                        opcode = Constantes.ASTORE_3;
                        break;
                    default:
                        opcode = Constantes.ASTORE;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }

    @Override
    public void appendArgumento(Tabla<U1> tabla) {
        if(tieneArgumento()) {
            U1 u1 = new U1();
            u1.setValor(arg);
            tabla.add(u1);
        }
    }

    @Override
    public void salvarArgumento(DataOutputStream dos) throws IOException {
        if(tieneArgumento()) {
            dos.writeByte(arg);
        }
    }

    @Override
    public int dameAnchuraArgumentos() {
        if(tieneArgumento())
            return 1;
        else
            return 0;
    }

}
