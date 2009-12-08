/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interprete;

/**
 *
 * @author ruben
 */
public class DatoPila {
    public byte tipo;
    public float dato;

    public DatoPila(byte t,float d){
        tipo=t;
        dato=d;
    }

    public byte getTipo(){
        return tipo;
    }

    public float getDato(){
        return dato;
    }
}
