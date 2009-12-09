/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar
 */
public abstract class Dato {

    /**
     * @return el valor de este dato como entero, en caso
     * de poderse hacer el casting
     */
    public abstract int toInt();
    /**
     * @return el valor de este dato como natural, en caso
     * de poderse hacer el casting
     */
    public abstract long toNatural();
    /**
     * @return el valor de este dato como float, en caso
     * de poderse hacer el casting
     */
    public abstract float toFloat();
    /**
     * @return el valor de este dato como caracter, en caso
     * de poderse hacer el casting
     */
    public abstract char toChar();
    /**
     * @return el valor de este dato como booleano, en caso
     * de poderse hacer el casting
     */
    public abstract boolean toBoolean();
}
