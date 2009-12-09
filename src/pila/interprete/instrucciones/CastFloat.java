package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Real;
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
    public void ejecutate(Interprete interprete) {
        Real e = new Real(interprete.getPila().removeFirst().toFloat());
        interprete.getPila().addFirst(e);
    }
}
