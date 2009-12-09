/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila;

/**
 * Se trata de una representación muy abstracta de una
 * instrucción
 */
public abstract class Instruccion {

    abstract public Dato getDato();

    @Override
    public String toString() {
        return "Abstracta";
    }

}
