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
public class Multiplica extends InstruccionInterprete {
    public Multiplica() throws LectorExc{
        super(InstruccionInterprete.CODIGO_MULTIPLICA);
    }

    public Multiplica(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MULTIPLICA);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public void ejecutate(Interprete interprete) {
        DatoPila d1 = interprete.getPila().pop();
        DatoPila d2 = interprete.getPila().pop();
        byte tipo = d1.getTipoDato();
        if (d1.getTipoDato() != d2.getTipoDato() || tipo==DatoPila.BOOL_T || tipo==DatoPila.CHAR_T){
            //error;
            Logger.getLogger(CastInt.class.getName()).log(Level.SEVERE, null);
        }
        else{
            switch (d1.getTipoDato()){
                case DatoPila.NAT_T:
                case DatoPila.INT_T:
                    int i = (int)d1.getValor() * (int) d2.getValor();
                    System.out.println(i);
                    break;
                case DatoPila.FLOAT_T:
                    float f = d1.getValor() * d2.getValor();
                    System.out.println(f);
                    break;
            }
        }
    }
}
