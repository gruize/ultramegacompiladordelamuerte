/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Dato_Nat;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
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

        try {
            Dato_Nat n = new Dato_Nat(interprete.getPila().removeFirst().toNatural());
            interprete.getPila().addFirst(n);
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }

}
