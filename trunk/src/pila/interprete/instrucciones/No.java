package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class No extends InstruccionInterprete{

    public No() throws LectorExc{
        super(InstruccionInterprete.CODIGO_NO);
    }

    public No(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_NO);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "No";
    }

    /**
     * Semantica:
     * d = desapilar
     * if(d no es booleano)
     *      throw new InstruccionExc
     * if(d es cierto)
     *      apilar(booleano(falso))
     * else if (d es falso)
     *      apilar(booleano(cierto))
     *
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
