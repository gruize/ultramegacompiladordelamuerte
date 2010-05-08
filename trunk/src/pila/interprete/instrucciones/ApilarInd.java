package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
@author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
**/

public class ApilarInd  extends InstruccionInterprete {

    public ApilarInd() throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILAR_IND);
    }

    public ApilarInd(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILAR_IND);
        throw new LectorExc("La instruccion no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "ApilarInd "+getDato();
    }

    /**
     * Semantica:
     * x <- desapilar()
     * apilar memoria[x]
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            ArrayDeque<DatoPila> pila = interprete.getPila();
            DatoPila d = pila.removeFirst();
            pila.addFirst(interprete.getMemoria().getMemoria()[d.toNatural()]);
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
}
