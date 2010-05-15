package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.Dato_Caracter;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class CastChar extends InstruccionInterprete{

    public CastChar() throws LectorExc {
        super(InstruccionInterprete.CODIGO_CASTCHAR);
    }

    public CastChar(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_CASTCHAR);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "(Char)";
    }

    /**
     * Semantica:
     * apilar(desapilar().toChar())
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si se produce algun error al hacer el casting
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            Dato_Caracter e = new Dato_Caracter(interprete.getPila().removeFirst().toChar());
            interprete.getPila().addFirst(e);
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
    
}
