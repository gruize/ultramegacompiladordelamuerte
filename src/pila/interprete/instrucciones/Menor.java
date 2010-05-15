package pila.interprete.instrucciones;

import java.util.ArrayDeque;

import pila.interprete.Interprete;
import pila.interprete.datos.Bool;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class Menor extends InstruccionInterprete{

    public Menor() throws LectorExc {
        super(InstruccionInterprete.CODIGO_MENOR);
    }

    public Menor(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MENOR);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }


    @Override
    public String toString() {
        return "Menor";
    }

    /**
     * Semantica:
     * apilar(Bool(desapilar < desapilar))
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
                        res = new Bool(d2.toNatural() < d1.toNatural());
                        break;
                    case DatoPila.INT_T:
                        res = new Bool(d2.toInt() < d1.toInt());
                        break;
                    case DatoPila.FLOAT_T:
                        res = new Bool(d2.toFloat() < d1.toFloat());
                        break;
                    case DatoPila.CHAR_T:
                    	res = new Bool(d2.toChar() < d1.toChar());
                    	break;
                    case DatoPila.BOOL_T:
                        if (!d2.toBoolean() && d1.toBoolean()) //false < true
                            res = new Bool(true);
                        else
                            res = new Bool(false);
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
