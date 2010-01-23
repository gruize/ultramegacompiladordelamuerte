/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class Salida extends InstruccionInterprete{
    public byte tipo;

    public Salida() throws LectorExc {
        super(InstruccionInterprete.CODIGO_SALIDA);
    }

    public Salida(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_SALIDA, d);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            ArrayDeque<DatoPila> pila = interprete.getPila();
            DatoPila d1 = pila.pop();
            switch (d1.getTipoDato()) {
                case DatoPila.BOOL_T:
                    interprete.getWriter().println(d1.toBoolean());
                    break;
                case DatoPila.CHAR_T:
                    interprete.getWriter().println(d1.toChar());
                    break;
                case DatoPila.NAT_T:
                    interprete.getWriter().println(d1.toNatural());
                    break;
                case DatoPila.INT_T:
                    interprete.getWriter().println(d1.toInt());
                    break;
                case DatoPila.FLOAT_T:
                    interprete.getWriter().println(d1.toFloat());
                    break;
            }
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
            return true;

    }

    @Override
    public String toString() {
        return "Salida";
    }

}
