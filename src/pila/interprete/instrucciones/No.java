package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class No extends InstruccionInterprete{

    public No() throws LectorExc{
        super(InstruccionInterprete.CODIGO_NO);
    }

    public No(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_NO);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
