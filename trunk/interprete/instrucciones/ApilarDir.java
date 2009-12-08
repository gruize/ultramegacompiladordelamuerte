
package interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class ApilarDir extends InstruccionPila{
    public byte tipo;
    public float dato;

    public ApilarDir(byte o,byte t,float d){
        super(o);
        tipo=t;
        dato=d;
    }

    @Override
    public void ejecutate(Stack pila) {
        DatoPila d=new DatoPila(tipo,dato);
        pila.push(d);
    }
}
