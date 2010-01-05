package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Menos extends InstruccionInterprete{

    public Menos() throws LectorExc{
        super(InstruccionInterprete.CODIGO_MENOS);
    }

    public Menos(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MENOS);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "Menos";
    }

    /**
     * Semantica:
     * apilar(DatoQueSea(desapilar - desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        //TODO: Implementar
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
