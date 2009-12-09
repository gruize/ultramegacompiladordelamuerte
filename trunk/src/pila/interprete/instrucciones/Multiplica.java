package pila.interprete.instrucciones;

import java.util.ArrayDeque;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Multiplica extends InstruccionInterprete {
    public Multiplica() throws LectorExc{
        super(InstruccionInterprete.CODIGO_MULTIPLICA);
    }

    public Multiplica(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MULTIPLICA);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public void ejecutate(Interprete interprete) {
        ArrayDeque<DatoPila> pila = interprete.getPila();
        DatoPila d1= pila.pop();
        DatoPila d2= pila.pop();
        byte tipo=d1.getTipoDato();
        if (d1.getTipoDato() != d2.getTipoDato() || tipo==DatoPila.BOOL_T || tipo==DatoPila.CHAR_T){
            //error;
        }
        else{
            switch (d1.getTipoDato()){
                case DatoPila.NAT_T:
                case DatoPila.INT_T:
                    int i=(int)d1.getDato() * (int) d2.getDato();
                    System.out.println(i);
                    break;
                case DatoPila.FLOAT_T:
                    float f=d1.getDato() * d2.getDato();
                    System.out.println(f);
                    break;
            }
        }
    }
}
