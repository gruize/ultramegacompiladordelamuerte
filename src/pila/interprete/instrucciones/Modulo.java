package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
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
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
