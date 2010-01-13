package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class CastInt extends InstruccionInterprete{

    public CastInt() throws LectorExc {
        super(InstruccionInterprete.CODIGO_CASTINT);
    }

    public CastInt(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_CASTINT);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "(Int)";
    }

    /**
     * Semantica:
     * apilar(desapilar().toInt())
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si se produce algun error al hacer el casting
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            Entero e = new Entero(interprete.getPila().removeFirst().toInt());
            interprete.getPila().addFirst(e);
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
}
