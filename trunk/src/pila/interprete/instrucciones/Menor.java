package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Menor extends InstruccionInterprete{

    public Menor() throws LectorExc {
        super(InstruccionInterprete.CODIGO_MENOR);
    }

    public Menor(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MENOR);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }


    @Override
    public void ejecutate(Interprete interprete) {
        /*
        DatoPila d1=(DatoPila) pila.pop();
        DatoPila d2=(DatoPila) pila.pop();
        byte tipo=d1.getTipo();
        if (d1.getTipo() != d2.getTipo() || tipo==CODIGO_BOOLEAN || tipo==CODIGO_CHAR){
            //error;
        }
        else{
            switch (d1.getTipo()){
                case CODIGO_NATURAL:
                case CODIGO_INTEGER:
                    boolean b=(int)d1.getDato() < (int) d2.getDato();
                    System.out.println(b);
                    break;
                case CODIGO_FLOAT:
                    Boolean b1=d1.getDato() < d2.getDato();
                    System.out.println(b1);
                    break;
            }
        }*/
    }
}
