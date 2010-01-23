/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.Booleano;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class EntradaBool extends InstruccionInterprete {

    public EntradaBool() throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_BOOL);
        throw new LectorExc("La instrucción de entrada necesita " +
                "un parámetro");
    }

    public EntradaBool(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA_BOOL, d);
        if (d.getTipoDato() != DatoPila.NAT_T) {
            throw new LectorExc("La instrucción requiere un " +
                    "argumento booleano");
        }
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            DatoPila datoLeido = null;
            String leido = interprete.getReader().readLine();
            boolean b;
            if (leido.equals("true")) {
                b = true;
            } else if (leido.equals("false")) {
                b = false;
            } else {
                throw new InstruccionExc(this, "El dato leido no " +
                        "es un un booleano");
            }
            datoLeido = new Booleano(b);
            
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
        return "Entrada Booleana";
    }
}
