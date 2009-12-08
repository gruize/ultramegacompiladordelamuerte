package interprete.instrucciones;

import java.util.Stack;

/**
 *
 * Clase de la que heredan todos los tipos de dato de la pila. Tambien podria
 * usarse de manera generica un Object
 */
public abstract class InstruccionPila{
    public static final byte CODIGO_BOOLEAN= (byte) 1;
    public static final byte CODIGO_CHAR= (byte) 2;
    public static final byte CODIGO_NATURAL= (byte) 8;
    public static final byte CODIGO_INTEGER= (byte) 9;
    public static final byte CODIGO_FLOAT= (byte) 10;

    public byte operador;

    public InstruccionPila(byte o){
        operador=o;
    }

    public abstract void ejecutate(Stack pila);

    public byte getOperador(){
        return operador;
    }
}
