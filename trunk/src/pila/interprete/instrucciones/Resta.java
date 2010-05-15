package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Dato_Entero;
import pila.interprete.datos.Dato_Nat;
import pila.interprete.datos.Dato_Real;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class Resta extends InstruccionInterprete{

    public Resta() throws LectorExc{
        super(InstruccionInterprete.CODIGO_RESTA);
    }

    public Resta(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_RESTA);
        throw new LectorExc("La instrucción no admite operadores");
    }

    @Override
    public String toString() {
        return "Resta";
    }

    /**
     * Semantica:
     * apilar(DatoQueSea(desapilar - desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles o si dos
     * naturales dan como resultado un numero negativo
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
                    + d1.toString()+" - "+ d2.toString()+")");
        }
        else{
            try {
                switch (d1.getTipoDato()) {
                    case DatoPila.NAT_T:
                        if (d1.comparar(d2) > 0) {
                            throw new InstruccionExc(this, d1.toString() + " mayor que " + d2.toString());
                        }
                        res = new Dato_Nat(d2.toNatural() - d1.toNatural());
                        break;
                    case DatoPila.INT_T:
                        res = new Dato_Entero(d2.toInt() - d1.toInt());
                        break;
                    case DatoPila.FLOAT_T:
                        res = new Dato_Real(d2.toFloat() - d1.toFloat());
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
