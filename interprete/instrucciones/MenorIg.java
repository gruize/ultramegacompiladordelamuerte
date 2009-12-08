package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class MenorIg extends InstruccionPila{

    public MenorIg(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Stack pila) {
        DatoPila d1=(DatoPila) pila.pop();
        DatoPila d2=(DatoPila) pila.pop();
        byte tipo=d1.getTipo();
        if (d1.getTipo() != d2.getTipo() || tipo==CODIGO_BOOLEAN || tipo==CODIGO_CHAR){
            //error;
        }
        else{
            switch (d1.getTipo()){
                case CODIGO_NATURAL:
                case CODIGO_INTEGER:
                    boolean b=(int)d1.getDato() <= (int) d2.getDato();
                    System.out.println(b);
                    break;
                case CODIGO_FLOAT:
                    Boolean b1=d1.getDato() <= d2.getDato();
                    System.out.println(b1);
                    break;
            }
        }
    }
}
