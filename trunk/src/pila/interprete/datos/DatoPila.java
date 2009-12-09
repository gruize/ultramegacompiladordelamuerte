package pila.interprete.datos;

import pila.Dato;
import pila.interprete.excepiones.DatoExc;

/**
 *
 * Clase de la que heredan todos los tipos de dato de la pila. Tambien podria
 * usarse de manera generica un Object
 */
public abstract class DatoPila extends Dato {
    
    public static final byte FLOAT_T = 1;
    public static final byte BOOL_T = 2;
    public static final byte NAT_T = 3;
    public static final byte INT_T = 4;
    public static final byte CHAR_T = 5;

    private Byte tipoDato;

    public DatoPila(byte tipo) {
        this.tipoDato = tipo;
    }

    /**
     * @return the tipoDato
     */
    public Byte getTipoDato() {
        return tipoDato;
    }

    /**
     * @return el dato en su formato original
     */
    public abstract Object getValor();

    /**
     * * Compara dos datos.
     * @param arg0 El dato a comparar
     * @return negativo si this menor que arg0, 0 si son iguales, positivo si this mayor que arg0
     * @throws DatoExc si son dos datos incomparables
     */
     
    public abstract int comparar(DatoPila arg0) throws DatoExc;

    
}
