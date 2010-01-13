package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Real;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class CastFloat extends InstruccionInterprete{

    public CastFloat() throws LectorExc {
        super(InstruccionInterprete.CODIGO_CASTFLOAT);
    }

    public CastFloat(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_CASTFLOAT);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "(Float)";
    }

    /**
     * Semantica:
     * apilar(desapilar().toFloat())
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si se produce algun error al hacer el casting
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            Real e = new Real(interprete.getPila().removeFirst().toFloat());
            interprete.getPila().addFirst(e);
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
}
