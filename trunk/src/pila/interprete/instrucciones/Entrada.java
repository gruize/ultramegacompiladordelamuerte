/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.interprete.instrucciones;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import pila.interprete.Interprete;
import pila.interprete.datos.Booleano;
import pila.interprete.datos.Caracter;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Natural;
import pila.interprete.datos.Real;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author Laura Reyero
 */
public class Entrada extends InstruccionInterprete{
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public Entrada() throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA);
        throw new LectorExc("La instrucción de entrada necesita " +
                "un parámetro");
    }

    public Entrada(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_ENTRADA, d);
        if(d.getTipoDato() != DatoPila.NAT_T)
            throw new LectorExc("La instrucción requiere un " +
                    "argumento natural");
    }

    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            DatoPila datoLeido = null;
            String leido = reader.readLine();
            if(interprete.getMemoria()[getDato().toNatural()] == null)
                throw new InstruccionExc(this,"Dirección de memoria "
                        +getDato().toNatural()+" no iniciada");
            switch(interprete.getMemoria()[getDato().toNatural()].getTipoDato()) {
                case DatoPila.BOOL_T:
                    boolean b;
                    if(leido.equals("true"))
                        b = true;
                    else
                        if(leido.equals("false"))
                            b = false;
                        else
                            throw new InstruccionExc(this,"El dato leido no " +
                                    "puede asignarse a un booleano");
                    datoLeido = new Booleano(b);
                    break;
                case DatoPila.CHAR_T:
                    datoLeido = new Caracter(leido.charAt(0));
                    break;
                case DatoPila.NAT_T:
                    int i = Integer.valueOf(leido);
                    if(i < 0)
                        throw new InstruccionExc(this,"El dato leído ("
                            +datoLeido.toNatural()+") no es un natural");
                    datoLeido = new Natural(i);
                    break;
                case DatoPila.INT_T:
                    datoLeido = new Entero(Integer.valueOf(leido));
                    break;
                case DatoPila.FLOAT_T:
                    datoLeido = new Real(Float.valueOf(leido));
                    break;
            }
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
        return "Entrada";
    }

}
