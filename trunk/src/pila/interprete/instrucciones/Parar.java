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
public class Parar extends InstruccionInterprete {
        public byte tipo;

    public Parar() throws LectorExc {
        super(InstruccionInterprete.CODIGO_PARAR);
    }

    public Parar(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_PARAR, d);
        throw new LectorExc("La instrucción parar no necesita parámetros");
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        interprete.setParar(true);
        return true;
    }

    @Override
    public String toString() {
        return "Parar";
    }

}
