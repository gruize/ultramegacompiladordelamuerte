/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.Dato;
import pila.Instruccion;
import pila.jvm.Tabla;
import pila.jvm.U1;
import pila.jvm.UAbstracto;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public abstract class InstJvm implements UAbstracto, Instruccion {

    protected short opcode; //solo se usan los 8 primeros bits => se guardan como bytes

    public InstJvm() {
    }

    public boolean tieneArgumento() {
        return false;
    }

    public Dato getDato() {
        return null;
    }

    public int getValor() {
        return opcode;
    }

    public void setValor(int valor) {
        throw new UnsupportedOperationException("Not supported.");
    }

    public void appendTo(Tabla<U1> tabla) {
        U1 u1 = new U1();
        u1.setValor(opcode);
        tabla.add(u1);
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(opcode);
    }

    public int dameNumBytes() {
        return 1;
    }

    

}
