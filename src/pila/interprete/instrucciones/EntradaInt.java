/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import pila.interprete.datos.Entero;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class EntradaInt extends InstruccionInterprete{
    public EntradaInt() throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_INT);
        throw new LectorExc("La instrucción de entrada necesita " +
                "un parámetro");
    }

    public EntradaInt(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_INT, d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción requiere un " +
                    "argumento natural");
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            DatoPila datoLeido = null;
            interprete.getWriter().print("Introduzca un entero >>>");
            interprete.getWriter().flush();
            String leido = interprete.getReader().readLine();
            int i = Integer.valueOf(leido);
            datoLeido = new Entero(i);
            interprete.getMemoria()[getDato().toNatural()] = datoLeido;
            }
            catch (Exception ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entrada Entera";
    }

}
