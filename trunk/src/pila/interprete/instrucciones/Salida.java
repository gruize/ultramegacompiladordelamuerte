/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class Salida extends InstruccionInterprete{
    public byte tipo;

    public Salida() throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILAR);
        throw new LectorExc("La instrucción apilar necesita " +
                "un parámetro");
    }

    public Salida(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILAR, d);
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar
         * Acabo de añadirla,tengo q mirarlo
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String toString() {
        return "Salida";
    }

}