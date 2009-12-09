
package pila.interprete.instrucciones;

import interprete.*;
import java.util.Stack;

/**
 *
 * @author ruben
 */
public class ApilarDir extends InstruccionInterprete{
    public byte tipo;
    public float dato;

    public ApilarDir(byte o,byte t,float d){
        super(o);
        tipo=t;
        dato=d;
    }

    @Override
    public void ejecutate(Interprete interprete) {
        DatoPila d=new DatoPila(tipo,dato);
        pila.push(d);
    }
}
