package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class Resta extends InstruccionPila{

    public Resta(byte o){
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
                    int i=(int)d1.getDato() - (int) d2.getDato();
                    System.out.println(i);
                    break;
                case CODIGO_FLOAT:
                    float f=d1.getDato() - d2.getDato();
                    System.out.println(f);
                    break;
            }
        }
    }

}
