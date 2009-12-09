
package pila.interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class Desapilar extends InstruccionInterprete{

    public Desapilar(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Interprete interprete) {
        pila.pop();
    }
}
