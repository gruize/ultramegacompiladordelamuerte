package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Divide extends InstruccionInterprete{
    public Divide() throws LectorExc {
        super(InstruccionInterprete.CODIGO_DIVIDE);
    }

    public Divide(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_DIVIDE);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public void ejecutate(Interprete interprete) {
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
                    int i=(int)d1.getDato() / (int) d2.getDato();
                    System.out.println(i);
                    break;
                case CODIGO_FLOAT:
                    float f=d1.getDato() / (int) d2.getDato();
                    System.out.println(f);
                    break;
            }
        }
    }
}
