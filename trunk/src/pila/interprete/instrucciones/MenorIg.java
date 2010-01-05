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
public class MenorIg extends InstruccionInterprete{

    public MenorIg() throws LectorExc{
        super(InstruccionInterprete.CODIGO_MENORIG);
    }

    public MenorIg(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MENORIG);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }


    @Override
    public void ejecutate(Interprete interprete) {
        DatoPila d1 = (DatoPila) interprete.getPila().pop();
        DatoPila d2 = (DatoPila) interprete.getPila().pop();
        //byte tipo = d1.getTipoDato();
        if (d1.getTipoDato() != d2.getTipoDato()){
            //error;
            Logger.getLogger(CastInt.class.getName()).log(Level.SEVERE, null);
        }
        else{
            switch (d1.getTipoDato()){
                case DatoPila.BOOL_T:
                case DatoPila.CHAR_T:
                case DatoPila.NAT_T:
                case DatoPila.INT_T:
                    boolean b = (int)d1.getValor() <= (int) d2.getValor();
                    System.out.println(b);
                    break;
                case DatoPila.FLOAT_T:
                    Boolean b1 = d1.getValor() <= d2.getValor();
                    System.out.println(b1);
                    break;
            }
        }
    }
}
