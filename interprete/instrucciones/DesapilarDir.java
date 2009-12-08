
package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class DesapilarDir extends InstruccionPila{

    public DesapilarDir(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Stack pila) {
        pila.pop();
    }
}
