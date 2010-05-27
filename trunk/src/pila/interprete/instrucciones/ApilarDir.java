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
public class ApilarDir extends InstruccionInterprete {

    public ApilarDir() throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILARDIR);
        throw new LectorExc("La instrucción requiere un " +
                "argumento natural");
    }

    public ApilarDir(DatoPila d) throws LectorExc {
        super(InstruccionInterprete.CODIGO_APILARDIR, d);
        if (d.getTipoDato() != DatoPila.NAT_T) {
            throw new LectorExc("La instrucción requiere un " +
                    "argumento natural");
        }
    }

    @Override
    public String toString() {
        return "ApilarDir "+getDato();
    }

    /**
     * Semantica:
     * apilar memoria[this.getDato()]
     * @return siempre true (nunca modifica el cp del interprete)
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        try {
            switch (getDato().getTipoDato()) {
                case DatoPila.NAT_T:
                	DatoPila d=interprete.getMemoria().getMemoria()[getDato().toNatural()];
                	if (d==null) //no se saca nada util de memoria. Se pone un valor poco probable
                		d=new Entero(Integer.MIN_VALUE); 
                    interprete.getPila().addFirst(d);
                    break;
                default:
                    throw new InstruccionExc(this, "Tipo inválido (" + getDato().toString() + ")");
            }
            
        } catch (DatoExc ex) {
        	ex.printStackTrace();
            throw new InstruccionExc(this, ex.getMessage());
        }
        return true;
    }
}
