package pila.interprete.excepiones;

import pila.Dato;

/**
 * Esta excepcion puede ser lanzada cuando exista algun
 * problema con un dato. Por ejemplo, al castearlo a un
 * tipo al que no puede convertirse
 */
public class DatoExc extends Exception {
    
    private Dato dato;

    public DatoExc(Dato dato) {
        super();
        this.dato = dato;
    }

    public DatoExc(Dato dato, String str) {
        super(str);
        this.dato = dato;
    }

    /**
     * @return the dato
     */
    public Dato getDato() {
        return dato;
    }

}
