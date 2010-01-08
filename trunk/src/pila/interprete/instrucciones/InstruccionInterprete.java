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
public abstract class InstruccionInterprete implements Instruccion {

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
    //añado algunos que no estan, pero que verdaderamente no
    //estoy segura de q se usen asi...
    public static final byte CODIGO_ERROR = (byte) 25;
    public static final byte CODIGO_BOOLEAN = (byte) 26;
    public static final byte CODIGO_CHAR = (byte) 27;
    public static final byte CODIGO_FLOAT = (byte) 28;
    public static final byte CODIGO_INTEGER = (byte) 29;
    public static final byte CODIGO_NATURAL = (byte) 30;
    //los pongo aqui abajo porque no quiero descuadrar los numeros
    //pero sino, los pongo en orden alfabetico, ya me decis
    public static final byte CODIGO_ABS = (byte) 31;
    public static final byte CODIGO_ENTRADA = (byte) 32;
    public static final byte CODIGO_SALIDA = (byte) 33;


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
    public abstract boolean ejecutate(Interprete interprete) throws InstruccionExc;

    /**
     * @return the dato
     */
    public DatoPila getDato() {
        return dato;
    }

    /**
     * @return el byte que identifica el tipo de instrucción
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

    @Override
    public String toString() {
        return "Abstracta";
    }

}
