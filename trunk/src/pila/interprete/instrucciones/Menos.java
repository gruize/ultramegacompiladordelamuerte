package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
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
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
