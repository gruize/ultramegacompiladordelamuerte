package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class Shr extends InstruccionPila{

    public Shr(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Stack pila) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
