
package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class DesapilarDir extends InstruccionInterprete{

    public DesapilarDir() throws LectorExc {
        super(InstruccionInterprete.CODIGO_DESAPILARDIR);
        throw new LectorExc("La instrucción requiere un " +
                "argumento natural");
    }

    public DesapilarDir(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_DESAPILARDIR, d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción requiere un " +
                    "argumento natural");
    }


    @Override
    public String toString() {
        return "DesapilarDir "+getDato();
    }

    /**
     * Semantica:
     * memoria[dato] = desapilar
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            ArrayDeque<DatoPila> pila = interprete.getPila();
            DatoPila d = pila.removeFirst();
            interprete.getMemoria().getMemoria()[getDato().toNatural()] = d;
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getLocalizedMessage());
        }
        return true;
    }
}
