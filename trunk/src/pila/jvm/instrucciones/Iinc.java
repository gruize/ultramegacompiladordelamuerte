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
public class Iinc extends InstJvmConArg {

    private byte index,incremento;

    public Iinc(short index, short incremento) {
        this.index = (byte)index;
        this.incremento = (byte)incremento;
        opcode = Constantes.IINC;
    }

    @Override
    public void appendArgumento(Tabla<U1> tabla) {
        U1 u1 = new U1();
        u1.setValor(index);
        tabla.add(u1);

        u1 = new U1();
        u1.setValor(incremento);
        tabla.add(u1);
    }

    @Override
    public void salvarArgumento(DataOutputStream dos) throws IOException {
        dos.writeByte(index);
        dos.writeByte(incremento);
    }

    @Override
    public int dameAnchuraArgumentos() {
        return 2;
    }

}
