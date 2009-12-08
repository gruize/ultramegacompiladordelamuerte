/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class Apilar extends InstruccionPila{
    public byte tipo;
    public float dato;

    public Apilar(byte o,byte t,float d){
        super(o);
        tipo=t;
        dato=d;
    }

    @Override
    public void ejecutate(Stack pila) {
        DatoPila d=new DatoPila(tipo,dato);
        pila.push(d);
    }


}
