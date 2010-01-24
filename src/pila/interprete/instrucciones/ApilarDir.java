package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class ApilarDir extends InstruccionInterprete {

    public ApilarDir() throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILARDIR);
        throw new LectorExc("La instrucción requiere un " +
                "argumento natural");
    }

    public ApilarDir(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILARDIR, d);
        if (d.getTipoDato() != DatoPila.NAT_T) {
            throw new LectorExc("La instrucción requiere un " +
                    "argumento natural");
        }
    }

    @Override
    public String toString() {
        return "ApilarDir "+getDato();
    }

    /**
     * Semantica:
     * apilar memoria[this.getDato()]
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            ArrayDeque<DatoPila> pila = interprete.getPila();
            DatoPila d = pila.pop();
            switch (d.getTipoDato()) {
                case DatoPila.NAT_T:
                    interprete.getPila().addFirst(interprete.getMemoria()[getDato().toNatural()]);
                    break;
                default:
                    throw new InstruccionExc(this, "Tipo inválido (" + d.toString() + ")");
            }
            
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
}
