/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Nat;
import pila.interprete.datos.Real;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class Abs extends InstruccionInterprete {

    public byte tipo;

    public Abs() throws LectorExc {
        super(InstruccionInterprete.CODIGO_ABS);
    }

    public Abs(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_ABS, d);
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        ArrayDeque<DatoPila> pila = interprete.getPila();
        DatoPila d = pila.pop();
        DatoPila res = null;

        try {
            switch (d.getTipoDato()) {
                case DatoPila.NAT_T:
                    res = new Nat(d.toNatural());
                    break;
                case DatoPila.INT_T:
                    if (d.toInt() < 0)
                        res = new Entero(-d.toInt());
                    else
                        res = d;
                    break;
                case DatoPila.FLOAT_T:
                    if (d.toFloat() < 0)
                        res = new Real(-d.toFloat());
                    else
                        res = d;
                    break;
                default:
                    throw new InstruccionExc(this, "Tipo inválido (" + d.toString() + ")");
            }
            pila.addFirst(res);
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }

    @Override
    public String toString() {
        return "Abs";
    }
}
