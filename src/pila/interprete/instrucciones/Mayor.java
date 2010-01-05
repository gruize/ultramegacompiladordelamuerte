package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Mayor extends InstruccionInterprete{

    public Mayor() throws LectorExc {
        super(InstruccionInterprete.CODIGO_MAYOR);
    }

    public Mayor(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_MAYOR);
        throw new LectorExc("La instrucción no "
                +"acepta argumentos");
    }

    @Override
    public String toString() {
        return "Mayor";
    }

    /**
     * Semantica:
     * apilar(booleano(desapilar > desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar
        DatoPila d1 = (DatoPila) interprete.getPila().pop();
        DatoPila d2 = (DatoPila) interprete.getPila().pop();
        byte tipo=d1.getTipoDato();
        if (d1.getTipoDato() != d2.getTipoDato() || tipo==CODIGO_BOOLEAN || tipo==CODIGO_CHAR){
            //error;
            Logger.getLogger(CastInt.class.getName()).log(Level.SEVERE, null);
        }
        else{
            switch (d1.getTipoDato()){
                case CODIGO_NATURAL:
                case CODIGO_INTEGER:
                    boolean b = (int) d1.getValor() > (int) d2.getValor();
                    System.out.println(b);
                    break;
                case CODIGO_FLOAT:
                    Boolean b1 = d1.getValor() > d2.getValor();
                    System.out.println(b1);
                    break;
            }
        }
         */
        return true;
    }
}
