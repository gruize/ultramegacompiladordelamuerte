package pila.interprete.instrucciones;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.interprete.*;
import pila.*;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 * De esta clase heredan todas las instrucciones del lenguaje a pila
 * Contiene las constantes de todos los tipos de instrucción y la
 * interfaz del método ejecutar, el más importante de las instrucciones
 */
public abstract class InstruccionInterprete extends Instruccion {

    public static final byte CODIGO_APILAR = (byte) 1;
    public static final byte CODIGO_APILARDIR = (byte) 2;
    public static final byte CODIGO_DESAPILAR = (byte) 3;
    public static final byte CODIGO_DESAPILARDIR = (byte) 4;
    public static final byte CODIGO_MENOR = (byte) 5;
    public static final byte CODIGO_MAYOR = (byte) 6;
    public static final byte CODIGO_MENORIG = (byte) 7;
    public static final byte CODIGO_MAYORIG = (byte) 8;
    public static final byte CODIGO_IGUAL = (byte) 9;
    public static final byte CODIGO_NOIGUAL = (byte) 10;
    public static final byte CODIGO_SUMA = (byte) 11;
    public static final byte CODIGO_RESTA = (byte) 12;
    public static final byte CODIGO_MULTIPLICA = (byte) 13;
    public static final byte CODIGO_DIVIDE = (byte) 14;
    public static final byte CODIGO_MODULO = (byte) 15;
    public static final byte CODIGO_Y = (byte) 16;
    public static final byte CODIGO_O = (byte) 17;
    public static final byte CODIGO_NO = (byte) 18;
    public static final byte CODIGO_MENOS = (byte) 19;
    public static final byte CODIGO_SHL = (byte) 20;
    public static final byte CODIGO_SHR = (byte) 21;
    public static final byte CODIGO_CASTINT = (byte) 22;
    public static final byte CODIGO_CASTCHAR = (byte) 23;
    public static final byte CODIGO_CASTFLOAT = (byte) 24;

    private DatoPila dato;
    private byte tipoIns;

    public InstruccionInterprete(byte tipoIns) throws LectorExc{
        this.tipoIns = tipoIns;
        this.dato = null;
    }

    public InstruccionInterprete(byte tipoIns, DatoPila dato) throws LectorExc{
        this.tipoIns = tipoIns;
        this.dato = dato;
    }

    /**
     *
     * Ha de enmascararse para darle una implementación a la instrucción.
     * Lo normal será que al final de la ejecución aumente el cp del
     * interprete en uno.
     * @param interprete el interprete que ejecuta la instrucción
     * @throws InstruccionExc Si ocurre un error al ejecutar la instrucción
     */
    public abstract void ejecutate(Interprete interprete) throws InstruccionExc;

    /**
     * @return El dato asociado a esta instrucción en el caso de tenerlo
     */
    public DatoPila getDato() {
        return dato;
    }

    /**
     * @return el byate que identifica el tipo de instrucción
     */
    public byte getTipoIns() {
        return tipoIns;
    }

    public void escribete(DataOutputStream dos) throws IOException {
        dos.writeByte(getTipoIns());
        if(getDato() != null) {
            getDato().escribete(dos);
        }
    }

}
