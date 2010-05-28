package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Real;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class Menos extends InstruccionInterprete {

    public Menos() throws LectorExc {
        super(InstruccionInterprete.CODIGO_MENOS);
    }

    public Menos(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_MENOS);
        throw new LectorExc("La instrucción no " + "acepta argumentos");
    }

    @Override
    public String toString() {
        return "Menos";
    }

    /**
     * Semantica:
     * apilar(DatoQueSea(- desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        ArrayDeque<DatoPila> pila = interprete.getPila();
        DatoPila d1 = pila.pop();
        DatoPila res;
        try {
            switch (d1.getTipoDato()) {
            	case DatoPila.NAT_T:
                case DatoPila.INT_T:
                    res = new Entero(- d1.toInt());
                    break;
                case DatoPila.FLOAT_T:
                    res = new Real(- d1.toFloat());
                    break;
                default:
                    throw new InstruccionExc(this, "Tipo inválido (" + d1.toString() + ")");
            }
            pila.addFirst(res);

        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
    return true;
    }

}
