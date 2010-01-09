/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import java.util.logging.Level;
import java.util.logging.Logger;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Natural;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class CastNat extends InstruccionInterprete{
        public CastNat() throws LectorExc {
        super(InstruccionInterprete.CODIGO_CASTNAT);
    }

    public CastNat(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_CASTNAT);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "(Natural)";
    }

    /**
     * Semantica:
     * apilar(desapilar().toNatural())
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si se produce algun error al hacer el casting
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar excepcion. Debe atrapar la DatoExc y lanzar una
         * InstruccionExc
         */
        try {
            Natural n = new Natural(interprete.getPila().removeFirst().toNatural());
            interprete.getPila().addFirst(n);
        } catch (DatoExc ex) {
            Logger.getLogger(CastNat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

}
