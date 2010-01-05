/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class U4 implements UAbstracto {

    private int valor;

    /**
     * @return the valor
     */
    public int getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeInt(getValor());
    }

    final public int dameNumBytes() {
        return 4;
    }

    public void appendTo(Tabla<U1> tabla) {
        U1 peque = new U1();
        peque.setValor(valor >> 48); //los 16 bits mayores
        tabla.add(peque);

        peque = new U1();
        peque.setValor((valor >> 32) & 0xFFFF); //otros 16
        tabla.add(peque);

        peque = new U1();
        peque.setValor((valor >> 16) & 0xFFFF); //otros 16
        tabla.add(peque);

        peque = new U1();
        peque.setValor(valor & 0xFFFF); //los 16 mas bajos
        tabla.add(peque);
    }
}
