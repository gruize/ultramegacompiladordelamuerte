package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

public class Mueve  extends InstruccionInterprete {

    public Mueve() throws LectorExc {
        super(InstruccionInterprete.CODIGO_MUEVE);
        throw new LectorExc("La instruccion requiere un " +
                "argumento entero");
    }

    public Mueve(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_MUEVE, d);
        if (d.getTipoDato() != DatoPila.NAT_T) {
            throw new LectorExc("La instruccion requiere un " +
                    "argumento entero");
        }
    }

    @Override
    public String toString() {
        return "Mueve " + getDato();
    }

    /**
     * Semantica:
     * o = desapila()
     * d = desapila()
     * mueve t celdas de o a d y libera t celdas desde o
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            switch (getDato().getTipoDato()) {
                case DatoPila.INT_T:
                    DatoPila o = interprete.getPila().removeFirst();
                    DatoPila d = interprete.getPila().removeFirst();
                    interprete.getMemoria().mover(o.toInt(), d.toInt(), getDato().toInt());
                    break;
                default:
                    throw new InstruccionExc(this, "Tipo invalido (" + getDato().toString() + ")");
            }
            
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
}
