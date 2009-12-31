package pila.interprete.instrucciones;

import java.util.ArrayDeque;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class MenorIg extends InstruccionInterprete{

    public MenorIg() throws LectorExc{
        super(InstruccionInterprete.CODIGO_MENORIG);
    }

    public MenorIg(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MENORIG);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }


    @Override
    public void ejecutate(Interprete interprete) {
        /*
        ArrayDeque<DatoPila> pila = interprete.getPila();
        DatoPila d1=(DatoPila) pila.pop();
        DatoPila d2=(DatoPila) pila.pop();
        byte tipo=d1.getTipo();
        if (d1.getTipo() != d2.getTipo()){
            //error;
        }
        else{
            switch (d1.getTipo()){
                case DatoPila.BOOL_T:
                case DatoPila.CHAR_T:
                case DatoPila.NAT_T:
                case DatoPila.INT_T:
                    boolean b=(int)d1.getDato() <= (int) d2.getDato();
                    System.out.println(b);
                    break;
                case DatoPila.FLOAT_T:
                    Boolean b1=d1.getDato() <= d2.getDato();
                    System.out.println(b1);
                    break;
            }
        }
         * */
    }
}
