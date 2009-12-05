package interprete;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * Esta interfaz deberia ser implementada por la clase que lea el codigo
 * compilado del flujo de bytes.
 */
public interface LectorBytecode {
    public ArrayList<InstruccionPila> leerFuente(File f);
}
