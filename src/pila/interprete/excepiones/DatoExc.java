package pila.interprete.excepiones;

import pila.interprete.datos.DatoPila;

/**
 * Esta excepcion puede ser lanzada cuando exista algun
 * problema con un dato. Por ejemplo, al castearlo a un
 * tipo al que no puede convertirse
 */
public class DatoExc extends Exception {
    
    private DatoPila dato;

    public DatoExc(DatoPila dato) {
        super();
        this.dato = dato;
    }

    public DatoExc(DatoPila dato, String str) {
        super(str);
        this.dato = dato;
    }

    /**
     * @return the dato
     */
    public DatoPila getDato() {
        return dato;
    }

}
