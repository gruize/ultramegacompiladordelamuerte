package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class Modulo extends InstruccionPila{

    public Modulo(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Stack pila) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
