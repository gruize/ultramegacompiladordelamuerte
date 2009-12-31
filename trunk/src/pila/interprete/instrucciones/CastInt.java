package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
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
    public void ejecutate(Interprete interprete) {
        /*
        Entero e = new Entero(interprete.getPila().removeFirst().toInt());
        interprete.getPila().addFirst(e);
         */
    }
}
