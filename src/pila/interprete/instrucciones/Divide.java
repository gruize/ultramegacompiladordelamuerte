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
        DatoPila d1 = (DatoPila) interprete.getPila().pop();
        DatoPila d2 = (DatoPila) interprete.getPila().pop();
        byte tipo = d1.getTipoDato();
        if (d1.getTipoDato() != d2.getTipoDato() ||
           // Ruben, esto no lo entiendo demasiado bien,
           //Que quieres decir de los tipos???
           tipo == super(interprete.CODIGO_BOOLEAN) ||
           tipo == super(InstruccionInterprete.CODIGO_CHAR)){
            //error;
            Logger.getLogger(CastInt.class.getName()).log(Level.SEVERE, null);
        }
        else{
            switch (d1.getTipoDato()){
                case CODIGO_NATURAL:
                case CODIGO_INTEGER:
                    int i = (int) (d1.getValor() / d2.getValor());
                    System.out.println(i);
                    break;
                case CODIGO_FLOAT:
                    float f = d1.getDato() / (int) d2.getDato();
                    System.out.println(f);
                    break;
            }
        }
    }
}
