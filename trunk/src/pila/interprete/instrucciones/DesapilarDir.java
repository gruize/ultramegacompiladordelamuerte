
package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class DesapilarDir extends InstruccionInterprete{

    public DesapilarDir() throws LectorExc {
        super(InstruccionInterprete.CODIGO_DESAPILARDIR);
        throw new LectorExc("La instrucción requiere un " +
                "argumento natural");
    }

    public DesapilarDir(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_DESAPILARDIR, d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción requiere un " +
                    "argumento natural");
    }


    @Override
    public void ejecutate(Interprete interprete) {
    }
}
