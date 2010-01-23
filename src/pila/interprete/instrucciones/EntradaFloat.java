/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import pila.interprete.datos.Real;
import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class EntradaFloat extends InstruccionInterprete{
    public EntradaFloat() throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_FLOAT);
        throw new LectorExc("La instrucción de entrada necesita " +
                "un parámetro");
    }

    public EntradaFloat(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_FLOAT, d);
        if(d.getTipoDato() != DatoPila.FLOAT_T)
            throw new LectorExc("La instrucción requiere un " +
                    "argumento real");
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {

            DatoPila datoLeido = null;
            String leido = interprete.getReader().readLine();
            if(interprete.getMemoria()[getDato().toNatural()] == null)
                throw new InstruccionExc(this,"Dirección de memoria "
                       +getDato().toNatural()+" no iniciada");
            float i = Float.valueOf(leido);          
            datoLeido = new Real(i);
            interprete.getMemoria()[getDato().toNatural()] = datoLeido;
        } catch (InstruccionExc ex) {
            throw ex;
            
        } catch (Exception ex) {
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entrada Real";
    }

}
