package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class NoIgual extends InstruccionInterprete{

    public NoIgual() throws LectorExc {
        super(InstruccionInterprete.CODIGO_NOIGUAL);
    }

    public NoIgual(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_NOIGUAL);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }


    @Override
    public String toString() {
        return "NoIgual";
    }

    /**
     * Semantica:
     * apilar(booleano(desapilar != desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar. Por distinto quiere decir no equals()
         */
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
