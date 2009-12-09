
package pila.interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class DesapilarDir extends InstruccionInterprete{

    public DesapilarDir(byte o){
        super(o);
    }

    @Override
    public void ejecutate(Interprete interprete) {
        pila.pop();
    }
}
