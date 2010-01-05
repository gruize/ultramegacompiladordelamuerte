package pila.interprete.instrucciones;

import java.util.logging.Level;
import java.util.logging.Logger;
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
        DatoPila d1 = (DatoPila) interprete.getPila().pop();
        DatoPila d2 = (DatoPila) interprete.getPila().pop();
        byte tipo = d1.getTipoDato();
        if (d1.getTipoDato() != d2.getTipoDato() || tipo!=CODIGO_BOOLEAN){
            //error;
            Logger.getLogger(CastInt.class.getName()).log(Level.SEVERE, null);
        }
        else{
            
        }
    }
}
