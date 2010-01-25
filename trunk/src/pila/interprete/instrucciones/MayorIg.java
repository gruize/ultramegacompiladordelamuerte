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
 * @author ruben
 */
public class MayorIg extends InstruccionInterprete{

    public MayorIg() throws LectorExc {
        super(InstruccionInterprete.CODIGO_MAYORIG);
    }

    public MayorIg(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MAYORIG);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }


   @Override
    public String toString() {
        return "MayorIG";
    }

    /**
     * Semantica:
     * apilar(booleano(desapilar >= desapilar))
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
                        res = new Bool(d1.toNatural() >= d2.toNatural());
                        break;
                    case DatoPila.INT_T:
                        res = new Bool(d1.toInt() >= d2.toInt());
                        break;
                    case DatoPila.FLOAT_T:
                        res = new Bool(d1.toFloat() >= d2.toFloat());
                        break;
                    case DatoPila.CHAR_T:
                    	res = new Bool(d1.toChar() >= d2.toChar());
                    	break;
                    case DatoPila.BOOL_T:
                        if ((d1.toBoolean() && !d2.toBoolean()) //true > false
                                || (d1.toBoolean() == d2.toBoolean())) //ambos iguales
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
