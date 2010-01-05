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
public class U1  implements UAbstracto {

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
        dos.writeByte(getValor());
    }

    final public int dameNumBytes() {
        return 1;
    }

    public void appendTo(Tabla<U1> tabla) {
        tabla.add(this);
    }

}
