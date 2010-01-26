package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.Bool;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class No extends InstruccionInterprete {

    public No() throws LectorExc {
        super(InstruccionInterprete.CODIGO_NO);
    }

    public No(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_NO);
        throw new LectorExc("La instrucción no " + "acepta argumentos");
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
        ArrayDeque<DatoPila> pila = interprete.getPila();
        DatoPila d = pila.pop();
        DatoPila res;

        try {
            switch (d.getTipoDato()) {
                case DatoPila.BOOL_T:
                    res = new Bool(!d.toBoolean());
                    break;
                default:
                    throw new InstruccionExc(this, "Tipo inválido (" + d.toString() + ")");
            }
            pila.addFirst(res);
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
    return true;
    }
}
