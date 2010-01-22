package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Natural;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Modulo extends InstruccionInterprete{

    public Modulo() throws LectorExc{
        super(InstruccionInterprete.CODIGO_MODULO);
    }

    public Modulo(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MODULO);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "Mayor";
    }

    /**
     * Semantica:
     * apilar(DatoQueSea(desapilar % desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles o si el
     * segundo es 0
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        ArrayDeque<DatoPila> pila = interprete.getPila();
        DatoPila d1= pila.pop();
        DatoPila d2= pila.pop();
        DatoPila res;
        byte t1 = d1.getTipoDato();
        byte t2 = d2.getTipoDato();
        if (t2 != DatoPila.NAT_T){
            throw new InstruccionExc(this,"Segundo operando "+d2.toString()+" inválido");
        }
        else{
            try {
                switch (d1.getTipoDato()) {
                    case DatoPila.NAT_T:
                        res = new Natural(d1.toNatural() % d2.toNatural());
                        break;
                    case DatoPila.INT_T:
                        res = new Entero(d1.toInt() % d2.toNatural());
                        break;
                    default:
                        throw new InstruccionExc(this, "Tipo inválido (" + d1.toString() + ")");
                }
                pila.addFirst(res);

            } catch (DatoExc ex) {
                throw new InstruccionExc(this, ex.getMessage());
            }
        }
        return true;
    }
}
