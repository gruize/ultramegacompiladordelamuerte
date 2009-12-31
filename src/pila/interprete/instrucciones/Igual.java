package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Igual extends InstruccionInterprete{
    public Igual() throws LectorExc {
        super(InstruccionInterprete.CODIGO_IGUAL);
    }

    public Igual(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_IGUAL);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public void ejecutate(Interprete interprete) {
        /*
        DatoPila d1=(DatoPila) pila.pop();
        DatoPila d2=(DatoPila) pila.pop();
        byte tipo=d1.getTipo();
        if (d1.getTipo() != d2.getTipo() || tipo!=CODIGO_BOOLEAN){
            //error;
        }
        else{
            
        }*/
    }
}
