/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;


/**
 *
 * @author ruben
 */
public class Apilar extends InstruccionInterprete{
    public byte tipo;

    public Apilar() throws LectorExc{
        super(InstruccionInterprete.CODIGO_APILAR);
        throw new LectorExc("La instrucción apilar necesita " +
                "un parámetro");
    }

    public Apilar(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILAR,d);
    }

    @Override
    public void ejecutate(Interprete interprete) {
        interprete.getPila().addFirst(dato);
    }

    @Override
    public String toString() {
        return "Apilar";
    }
}
