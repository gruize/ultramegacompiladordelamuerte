package pila.interprete.datos;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import pila.Dato;
import pila.interprete.excepiones.DatoExc;

/**
 *
 * Clase de la que heredan todos los tipos de
 * dato de la pila, dotándo de la capacidad de realizar
 * casting, comparaciones, etc
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
     * @return el valor de este dato como entero, en caso
     * de poderse hacer el casting
     * @throws DatoExc si son datos de tipos distintos y
     * el casting no puede hacerse
     */
    public int toInt() throws DatoExc {
        throw new DatoExc(this,toString()+" no puede transformarse en " +
                "un entero");
    }
    /**
     * El natural se representa con tantos bits como el entero,
     * por lo que puede representar numeros positivos mucho
     * mayores. Por eso lo representamos en java como un long
     * @return el valor de este dato como natural, en caso
     * de poderse hacer el casting
     * @throws DatoExc si son datos de tipos distintos y
     * el casting no puede hacerse
     */
    public long toNatural() throws DatoExc {
        throw new DatoExc(this,toString()+" no puede transformarse en " +
                "un natural");
    }
    /**
     * @return el valor de este dato como float, en caso
     * de poderse hacer el casting
     * @throws DatoExc si son datos de tipos distintos y
     * el casting no puede hacerse
     */
    public float toFloat() throws DatoExc {
        throw new DatoExc(this,toString()+" no puede transformarse en " +
                "un real");
    }
    /**
     * @return el valor de este dato como caracter, en caso
     * de poderse hacer el casting
     * @throws DatoExc si son datos de tipos distintos y
     * el casting no puede hacerse
     */
    public char toChar() throws DatoExc {
        throw new DatoExc(this,toString()+" no puede transformarse en " +
                "un caracter");
    }
    /**
     * @return el valor de este dato como booleano, en caso
     * de poderse hacer el casting
     * @throws DatoExc si son datos de tipos distintos y
     * el casting no puede hacerse
     */
    public boolean toBoolean() throws DatoExc {
        throw new DatoExc(this,toString()+" no puede transformarse en " +
                "un booleano");
    }

    /**
     * @return el tipo de dato al que pertenece. Se trata
     * de un byte que ha de concordar con DatoPila.INT_T,
     * DatoPila.NAT_T, etc
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

    public void escribete(DataOutputStream dos) throws IOException {
        dos.writeByte(tipoDato);
    }
    
}
