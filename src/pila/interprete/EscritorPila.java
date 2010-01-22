package pila.interprete;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import pila.EscritorBytecode;
import pila.Instruccion;
import pila.interprete.instrucciones.InstruccionInterprete;

/**
 *
 */
public class EscritorPila implements EscritorBytecode {

    public void escribirPrograma(ArrayList<Instruccion> programa, File f) throws Exception{
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
        for(Iterator<Instruccion> it = programa.iterator(); it.hasNext();) {
            ((InstruccionInterprete)it.next()).escribete(dos);
        }
    }

}
