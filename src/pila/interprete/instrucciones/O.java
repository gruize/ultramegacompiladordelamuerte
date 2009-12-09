package pila.interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class O extends InstruccionInterprete{

    public O(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
