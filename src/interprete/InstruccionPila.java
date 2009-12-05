package interprete;

/**
 * De esta clase deberian heredar todas las instrucciones del lenguaje a pila
 */
public abstract class InstruccionPila {

    private DatoPila dato;

    public InstruccionPila(DatoPila dato) {
        this.dato = dato;
    }

    /**
     * Ha de enmascararse para darle una implementación a la instrucción
     */
    public abstract void ejecutate();

    
    /**
     * Se usa toString para obtener una descripción imprimible de la clase. Esto
     * puede usarse luego, por ejemplo, al mostrar errores por pantalla.
     * @return una descripción corta de la clase
     */
    @Override
    public String toString() {
        return "Abstracta";
    }

    /**
     * @return the dato
     */
    public DatoPila getDato() {
        return dato;
    }

    /**
     * @param dato the dato to set
     */
    public void setDato(DatoPila dato) {
        this.dato = dato;
    }
}
