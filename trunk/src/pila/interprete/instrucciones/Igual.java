package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
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
    public String toString() {
        return "Igual";
    }

    /**
     * Semantica:
     * apilar(desapilar + desapilar)
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar
        DatoPila d1 = (DatoPila) interprete.getPila().pop();
        DatoPila d2 = (DatoPila) interprete.getPila().pop();
        byte tipo = d1.getTipoDato();
        if (d1.getTipoDato() != d2.getTipoDato() || tipo!=CODIGO_BOOLEAN){
            //error;
            Logger.getLogger(CastInt.class.getName()).log(Level.SEVERE, null);
        }
        else{
            
        }
         */
        return true;
    }
}
