package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;


/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */

public class New extends InstruccionInterprete 
{

    public New() throws LectorExc 
    {
        super(InstruccionInterprete.CODIGO_NEW);
        throw new LectorExc("La instrucci√≥n requiere un  argumento entero");
    }

    public New(DatoPila d) throws LectorExc 
    {
        super(InstruccionInterprete.CODIGO_NEW, d);
        if (d.getTipoDato() != DatoPila.NAT_T) 
        {
            throw new LectorExc("La instrucci√≥n requiere un argumento entero");
        }
    }

    @Override
    public String toString() 
    {
        return "New " + getDato();
    }

    /**
     * Semantica:
     * 1. reservar memoria para ese tamaÒo
     * 1. apilar la direcciÛn donde se ha reservado
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc 
    {
        try 
        {
            switch (getDato().getTipoDato()) 
            {
                case DatoPila.INT_T:
                	Entero e = new Entero(interprete.getMemoria().reservar(getDato().toInt()));
                    interprete.getPila().addFirst(e);
                    break;
                default:
                    throw new InstruccionExc(this, "Tipo inv√°lido (" + getDato().toString() + ")");
            }
            
        } catch (DatoExc ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
}
