package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Nat;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Shr extends InstruccionInterprete{

    public Shr() throws LectorExc {
        super(InstruccionInterprete.CODIGO_SHR);
    }

    public Shr(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_SHR,d);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "Shr";
    }

    /**
     * Semantica:
     * apilar(DatoQueSea(desapilar >> desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        ArrayDeque<DatoPila> pila = interprete.getPila();
        DatoPila d1= pila.pop();
        DatoPila d2= pila.pop();
        DatoPila res;
        byte t1 = d1.getTipoDato();
        byte t2 = d2.getTipoDato();
        if (t1 != t2){
            throw new InstruccionExc(this,"Operandos inválidos ("
                    +d1.toString()+" + "+d2.toString()+")");
        }
        else{
            try {
                switch (d1.getTipoDato()) {
                    case DatoPila.NAT_T:
                        res = new Nat(d2.toNatural() >> d1.toNatural());
                        break;
                    default:
                        throw new InstruccionExc(this, "Tipo inválido (" + d1.toString() + ")");
                }
                pila.addFirst(res);

            } catch (DatoExc ex) {
                //realmente este error no deberia darse nunca, puesto que se
                //comprueba en el if(t1 != t2)
                throw new InstruccionExc(this, ex.getMessage());
            }
        }
        return true;
    }
}
