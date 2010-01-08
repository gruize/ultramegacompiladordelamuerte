package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Shl extends InstruccionInterprete{

    public Shl() throws LectorExc{
        super(InstruccionInterprete.CODIGO_SHL);
        throw new LectorExc("La instrucción necesista " +
                "un valor natural como argumento");
    }

    public Shl(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_SHL,d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción necesista " +
                    "un valor natural como argumento");
    }

    @Override
    public String toString() {
        return "shl";
    }

    /**
     * Semantica:
     * apilar(DatoQueSea(desapilar shl desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles (el
     * segundo debe ser siempre un natural)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar. shl == <<. No lo pongo en el javadoc porque lo
         * reconoce como etiqueta html xD
         * Estoy mirando documentacion en internet sobre como hacer la operacion
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
