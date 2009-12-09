package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Error extends InstruccionInterprete{

    public Error() throws LectorExc {
        super(InstruccionInterprete.CODIGO_E);
    }

    //Gonzalo: Que hace esta instr?
    public Error(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_NOIGUAL);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
