package pila.interprete.instrucciones;

import pila.interprete.*;
import pila.*;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 * De esta clase deberian heredar todas las instrucciones del lenguaje a pila
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

    private Dato dato;
    private byte tipoIns;

    public InstruccionInterprete(byte tipoIns) throws LectorExc{
        this.tipoIns = tipoIns;
        this.dato = null;
    }

    public InstruccionInterprete(byte tipoIns, Dato dato) throws LectorExc{
        this.tipoIns = tipoIns;
        this.dato = dato;
    }

    /**
     * Ha de enmascararse para darle una implementación a la instrucción
     */
    public abstract void ejecutate(Interprete interprete) throws InstruccionExc;

    /**
     * @return the dato
     */
    public Dato getDato() {
        return dato;
    }

    /**
     * @return the tipoIns
     */
    public byte getTipoIns() {
        return tipoIns;
    }

    /**
     * @param tipoIns the tipoIns to set
     */
    public void setTipoIns(byte tipoIns) {
        this.tipoIns = tipoIns;
    }

}
