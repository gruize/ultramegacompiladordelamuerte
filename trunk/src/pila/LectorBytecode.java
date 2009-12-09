package pila;

import pila.interprete.instrucciones.InstruccionInterprete;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * Esta interfaz deberia ser implementada por la clase que lea el codigo
 * compilado del flujo de bytes.
 */
public interface LectorBytecode {
    public ArrayList<InstruccionInterprete> leerFuente(File f) throws Exception;
}
