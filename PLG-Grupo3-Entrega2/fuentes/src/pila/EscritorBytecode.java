
package pila;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * Esta interfaz deberia ser implementada por la clase que escriba instrucciones
 * de pila en un flujo de bytes
 */
public interface EscritorBytecode {
    public void escribirPrograma(ArrayList<Instruccion> programa, File f) throws Exception;
}
