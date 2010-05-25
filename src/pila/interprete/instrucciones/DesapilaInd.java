package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
*@author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
**/

public class DesapilaInd extends InstruccionInterprete {

    public DesapilaInd() throws LectorExc {
        super(InstruccionInterprete.CODIGO_DESAPILAR_IND);
    }

    public DesapilaInd(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_DESAPILAR_IND);
        throw new LectorExc("La instruccion no "
                +"acepta argumentos");
    }
    @Override
    public String toString() {
        return "Despilar_Ind ";//+getDato();
    }


    /**
     * Semantica:
     * v <- desapilar()
     * d <- desapilar()
     * memoria[d] = v
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            ArrayDeque<DatoPila> pila = interprete.getPila();
            DatoPila v = pila.removeFirst();
            DatoPila d = pila.removeFirst();
            interprete.getMemoria().getMemoria()[d.toNatural()] = v;
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
}
