
package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class Desapilar extends InstruccionPila{

    public Desapilar(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Stack pila) {
        pila.pop();
    }
}
