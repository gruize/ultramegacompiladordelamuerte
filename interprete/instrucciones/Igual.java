package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class Igual extends InstruccionPila{
    public Igual(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Stack pila) {
        DatoPila d1=(DatoPila) pila.pop();
        DatoPila d2=(DatoPila) pila.pop();
        byte tipo=d1.getTipo();
        if (d1.getTipo() != d2.getTipo() || tipo!=CODIGO_BOOLEAN){
            //error;
        }
        else{
            
        }
    }
}
