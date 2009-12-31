package pila.interprete.instrucciones;

import java.util.ArrayDeque;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Multiplica extends InstruccionInterprete {
    public Multiplica() throws LectorExc{
        super(InstruccionInterprete.CODIGO_MULTIPLICA);
    }

    public Multiplica(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MULTIPLICA);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public void ejecutate(Interprete interprete) {

    }
}
