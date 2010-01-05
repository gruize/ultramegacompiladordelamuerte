package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
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
    public String toString() {
        return "O";
    }

    /**
     * Semantica:
     * apilar(booleano(desapilar || desapilar))
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
