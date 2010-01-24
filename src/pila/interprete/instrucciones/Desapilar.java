
package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Desapilar extends InstruccionInterprete{

    public Desapilar() throws LectorExc {
        super(InstruccionInterprete.CODIGO_DESAPILAR);
    }

    public Desapilar(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_DESAPILAR);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }
    @Override
    public String toString() {
        return "Despilar "+getDato();
    }

    /**
     * Semantica:
     * desapilar()
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        interprete.getPila().removeFirst();
        return true;
    }
}
