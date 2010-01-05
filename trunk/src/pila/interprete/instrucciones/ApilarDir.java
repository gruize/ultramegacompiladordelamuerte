
package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class ApilarDir extends InstruccionInterprete{

    public ApilarDir() throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILARDIR);
        throw new LectorExc("La instrucción requiere un " +
                "argumento natural");
    }

    public ApilarDir(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_APILARDIR, d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción requiere un " +
                    "argumento natural");
    }

    @Override
    public String toString() {
        return "ApilarDir";
    }

    /**
     * Semantica:
     * apilar memoria[this.getDato()]
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) {
        /*
         * TODO: Implementar
        interprete.getPila().push(d);
         */
        return true;
    }
}
