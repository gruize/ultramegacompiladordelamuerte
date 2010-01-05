/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public interface UAbstracto extends EstructuraClass {
    /**
     * @return the valor
     */
    public int getValor();

    /**
     * @param valor the valor to set
     */
    public void setValor(int valor);

    public void appendTo(Tabla<U1> tabla);
}
