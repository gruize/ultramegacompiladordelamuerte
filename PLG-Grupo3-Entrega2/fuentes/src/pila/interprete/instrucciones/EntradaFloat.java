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
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class EntradaFloat extends InstruccionInterprete{
    public EntradaFloat() throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_FLOAT);
//        throw new LectorExc("La instrucción de entrada necesita " +
//                "un parámetro");
    }

    public EntradaFloat(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_FLOAT, d);
        throw new LectorExc("La instruccion no "
                +"acepta argumentos");
        
//        if(d.getTipoDato() != DatoPila.NAT_T)
//            throw new LectorExc("La instrucción requiere un " +
//                    "argumento natural");
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {

            DatoPila datoLeido = null;
            interprete.getWriter().print("Introduzca un real >>>");
            interprete.getWriter().flush();
            String leido = interprete.getReader().readLine();
            float i = Float.valueOf(leido);          
            datoLeido = new Real(i);
            interprete.getPila().addFirst(datoLeido);
            //interprete.getMemoria().getMemoria()[getDato().toNatural()] = datoLeido;
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
