package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

public class Seg extends InstruccionInterprete {

    public Seg() throws LectorExc {
        super(InstruccionInterprete.CODIGO_SEG);
        throw new LectorExc("La instruccion requiere un argumento entero");
    }

    public Seg(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_SEG, d);
        if (d.getTipoDato() != DatoPila.NAT_T) {
            throw new LectorExc("La instruccion requiere un argumento entero");
        }
    }

    @Override
    public String toString() {
        return "Seg " + getDato();
    }

    /**
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            switch (getDato().getTipoDato()) {
                case DatoPila.INT_T:
                    interprete.getMemoria().setHeapIndex(getDato().toInt());
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
