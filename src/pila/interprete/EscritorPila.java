package pila.interprete;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import pila.Instruccion;
import pila.interprete.instrucciones.InstruccionInterprete;

/**
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class EscritorPila {

    public void escribirPrograma(ArrayList<InstruccionInterprete> programa, File f) throws Exception{
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
        for(Iterator<InstruccionInterprete> it = programa.iterator(); it.hasNext();) {
            (it.next()).escribete(dos);
        }
    }

}
