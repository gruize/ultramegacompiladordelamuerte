package pila;

/**
 *
 * Clase de la que heredan todos los tipos de dato de la pila. Tambien podria
 * usarse de manera generica un Object
 */
public class DatoPila {
    private Byte tipoDato;
    private Number dato;

    public DatoPila(byte tipo, Number dato) {
        this.tipoDato = tipo;
        this.dato = dato;
    }

    /**
     * @return the tipoDato
     */
    public Byte getTipoDato() {
        return tipoDato;
    }

    /**
     * @return the dato
     */
    public Number getDato() {
        return dato;
    }
    
}
