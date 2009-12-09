package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Shl extends InstruccionInterprete{

    public Shl() throws LectorExc{
        super(InstruccionInterprete.CODIGO_SHL);
        throw new LectorExc("La instrucción necesista " +
                "un valor natural como argumento");
    }

    public Shl(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_SHL,d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción necesista " +
                    "un valor natural como argumento");
    }

    @Override
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
