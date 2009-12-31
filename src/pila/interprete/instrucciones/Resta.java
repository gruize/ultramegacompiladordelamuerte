package pila.interprete.instrucciones;

import java.util.ArrayDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Natural;
import pila.interprete.datos.Real;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
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
    public void ejecutate(Interprete interprete) throws InstruccionExc{
        
        ArrayDeque<DatoPila> pila = interprete.getPila();
        DatoPila d1= pila.removeFirst();
        DatoPila d2= pila.removeFirst();
        DatoPila res;
        byte t1 = d1.getTipoDato();
        byte t2 = d2.getTipoDato();
        if (t1 != t2){
            throw new InstruccionExc(this,"Operadores invalidos ("
                    +d1.toString()+" - "+d2.toString()+")");
        }
        else{
            try {
                switch (d1.getTipoDato()){
                    case DatoPila.NAT_T:
                    try {
                        if (d1.comparar(d2) < 0) {
                            throw new InstruccionExc(this, d1.toString() + " mayor que " + d2.toString());
                        }
                    } catch (DatoExc ex) {}
                        res = new Natural(d1.toNatural()-d2.toNatural());
                        break;
                    case DatoPila.INT_T:
                        res = new Entero(d1.toInt()-d2.toInt());
                        break;
                    case DatoPila.FLOAT_T:
                        res = new Real(d1.toFloat()-d2.toFloat());
                        break;
                    default:
                        throw new InstruccionExc(this,"Tipo inválido ("+d1.toString()+")");
                }
            }
            catch (DatoExc e) {
                throw new InstruccionExc(this, "Error en un dato: "+e.getMessage());
            }
            pila.addFirst(res);
            interprete.setCp(interprete.getCp());
        }
    }

}