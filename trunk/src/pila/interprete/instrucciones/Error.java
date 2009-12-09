package pila.interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class Error extends InstruccionInterprete{

    public Error(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Interprete interprete) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
