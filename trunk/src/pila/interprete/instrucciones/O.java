package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;


/**
 *
 * @author ruben
 */
public class O extends InstruccionInterprete{

    public O() throws LectorExc{
        super(InstruccionInterprete.CODIGO_O);
    }

    public O(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_O);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }


    @Override
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
