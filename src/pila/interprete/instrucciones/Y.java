package pila.interprete.instrucciones;

import pila.Dato;
import pila.interprete.Interprete;
import pila.interprete.excepiones.InstruccionExc;
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
    public String toString() {
        return "Y";
    }

    /**
     * Semantica:
     * apilar(Booleano(desapilar && desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos no booleanos
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
