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
 * Salta a la posicion pasada como parametro si se cumple una condicion
 * determinada
 * ..., T value1, T value2 -> ...
 */
public class If_Tcond extends InstJvmConArg {

    private short destino;

    public If_Tcond(byte tipo, byte condicion, int destino) throws Exception {
        this.destino = (short)destino;
        switch(tipo) {
            case Constantes.T_INT:
                switch(condicion) {
                    case Constantes.COND_EQ:
                        opcode = Constantes.IF_ICMPEQ;
                        break;
                    case Constantes.COND_NE:
                        opcode = Constantes.IF_ICMPNE;
                        break;
                    case Constantes.COND_GE:
                        opcode = Constantes.IF_ICMPGE;
                        break;
                    case Constantes.COND_GT:
                        opcode = Constantes.IF_ICMPGT;
                        break;
                    case Constantes.COND_LE:
                        opcode = Constantes.IF_ICMPLE;
                        break;
                    case Constantes.COND_LT:
                        opcode = Constantes.IF_ICMPLT;
                        break;
                    default:
                        throw new Exception("Condicion "+condicion+" inválida " +
                                "para el tipo entero");
                }
                break;
            case Constantes.T_ADDRESS:
                switch(condicion) {
                    case Constantes.COND_EQ:
                        opcode = Constantes.IF_ACMPEQ;
                        break;
                    case Constantes.COND_NE:
                        opcode = Constantes.IF_ACMPNE;
                        break;
                    default:
                        throw new Exception("Condicion "+condicion+" inválida " +
                                "para el tipo referencia");
                }
                break;
            default:
                throw new Exception("Tipo "+tipo+" inválido");
        }
    }

    @Override
    public void appendArgumento(Tabla<U1> tabla) {
        U1 u1 = new U1();
        u1.setValor(destino >> 8);
        tabla.add(u1);

        u1 = new U1();
        u1.setValor(destino & 0xFF);
        tabla.add(u1);
    }

    @Override
    public void salvarArgumento(DataOutputStream dos) throws IOException {
        dos.writeByte(destino >> 8);
        dos.writeByte(destino & 0xFF);
    }

    @Override
    public int dameAnchuraArgumentos() {
        return 2;
    }

}
