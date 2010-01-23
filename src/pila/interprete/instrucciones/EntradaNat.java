/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import pila.interprete.datos.Natural;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class EntradaNat extends InstruccionInterprete{
    public EntradaNat() throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_NAT);
        throw new LectorExc("La instrucción de entrada necesita " +
                "un parámetro");
    }

    public EntradaNat(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_NAT, d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción requiere un " +
                    "argumento natural");
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {

            DatoPila datoLeido = null;
            String leido = interprete.getReader().readLine();
            int i = Integer.valueOf(leido);
            if(i < 0)
                throw new InstruccionExc(this,"El dato leído ("
                        +datoLeido.toNatural()+") no es un natural");
            datoLeido = new Natural(i);
            
            interprete.getMemoria()[getDato().toNatural()] = datoLeido;
        } catch (InstruccionExc ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entrada Natural";
    }

}
