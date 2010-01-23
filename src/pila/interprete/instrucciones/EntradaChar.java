/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import pila.interprete.datos.Caracter;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class EntradaChar extends InstruccionInterprete{
    public EntradaChar() throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_CHAR);
        throw new LectorExc("La instrucción de entrada necesita " +
                "un parámetro");
    }

    public EntradaChar(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_CHAR, d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción requiere un " +
                    "argumento caracter");
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            DatoPila datoLeido = null;
            String leido = interprete.getReader().readLine();
            datoLeido = new Caracter(leido.charAt(0));
            interprete.getMemoria()[getDato().toNatural()] = datoLeido;           
        } catch (Exception ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entrada Caracter";
    }

}
