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
public class U2  implements UAbstracto {

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
        dos.writeShort(getValor());
    }

    final public int dameNumBytes() {
        return 2;
    }

    public void appendTo(Tabla<U1> tabla) {
        U1 peque = new U1();
        peque.setValor(valor >> 16); //los 16 bits mayores
        tabla.add(peque);

        peque = new U1();
        peque.setValor(valor & 0xFFFF); //los 16 bits mas bajos
        tabla.add(peque);
    }
}
