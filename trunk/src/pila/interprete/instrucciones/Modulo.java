package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Modulo extends InstruccionInterprete{

    public Modulo() throws LectorExc{
        super(InstruccionInterprete.CODIGO_MODULO);
    }

    public Modulo(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MODULO);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "Mayor";
    }

    /**
     * Semantica:
     * apilar(DatoQueSea(desapilar % desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles o si el
     * segundo es 0
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
