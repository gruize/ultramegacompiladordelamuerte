package pila.interprete.instrucciones;

import pila.Dato;
import pila.interprete.Interprete;
import pila.interprete.excepiones.LectorExc;


/**
 *
 * @author ruben
 */
public class Y extends InstruccionInterprete{

    public Y() throws LectorExc{
        super(InstruccionInterprete.CODIGO_Y);
    }

    public Y(Dato d) throws LectorExc {
        this();
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
