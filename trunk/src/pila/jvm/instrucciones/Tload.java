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
 * Carga una variable local a la pila
 */
public class Tload extends InstJvmConArg {

    private byte arg;
    public Tload(byte tipo, byte varIndex) throws Exception {
        setTieneArgumento(false);
        switch(tipo) {
            case Constantes.T_INT:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.ILOAD_0;
                        break;
                    case 1:
                        opcode = Constantes.ILOAD_1;
                        break;
                    case 2:
                        opcode = Constantes.ILOAD_2;
                        break;
                    case 3:
                        opcode = Constantes.ILOAD_3;
                        break;
                    default:
                        opcode = Constantes.ILOAD;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            case Constantes.T_LONG:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.LLOAD_0;
                        break;
                    case 1:
                        opcode = Constantes.LLOAD_1;
                        break;
                    case 2:
                        opcode = Constantes.LLOAD_2;
                        break;
                    case 3:
                        opcode = Constantes.LLOAD_3;
                        break;
                    default:
                        opcode = Constantes.LLOAD;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            case Constantes.T_FLOAT:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.FLOAD_0;
                        break;
                    case 1:
                        opcode = Constantes.FLOAD_1;
                        break;
                    case 2:
                        opcode = Constantes.FLOAD_2;
                        break;
                    case 3:
                        opcode = Constantes.FLOAD_3;
                        break;
                    default:
                        opcode = Constantes.FLOAD;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            case Constantes.T_DOUBLE:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.DLOAD_0;
                        break;
                    case 1:
                        opcode = Constantes.DLOAD_1;
                        break;
                    case 2:
                        opcode = Constantes.DLOAD_2;
                        break;
                    case 3:
                        opcode = Constantes.DLOAD_3;
                        break;
                    default:
                        opcode = Constantes.DLOAD;
                        setTieneArgumento(true);
                        arg = varIndex;
                        break;
                }
                break;
            case Constantes.T_ADDRESS:
                switch(varIndex) {
                    case 0:
                        opcode = Constantes.ALOAD_0;
                        break;
                    case 1:
                        opcode = Constantes.ALOAD_1;
                        break;
                    case 2:
                        opcode = Constantes.ALOAD_2;
                        break;
                    case 3:
                        opcode = Constantes.ALOAD_3;
                        break;
                    default:
                        opcode = Constantes.ALOAD;
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
