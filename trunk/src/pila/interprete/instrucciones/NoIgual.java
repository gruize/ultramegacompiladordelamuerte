package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class NoIgual extends InstruccionInterprete{

    public NoIgual() throws LectorExc {
        super(InstruccionInterprete.CODIGO_NOIGUAL);
    }

    public NoIgual(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_NOIGUAL);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }


    @Override
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
