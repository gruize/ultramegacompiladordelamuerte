package interprete;

import interprete.instrucciones.InstruccionPila;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
/**
 *
 * @author ruben
 */
public class Interprete {
    public static Lector lector=new Lector();
    public static ArrayList<InstruccionPila> programa;
    public static Stack<DatoPila> pila=new Stack<DatoPila>();

    @SuppressWarnings({"empty-statement", "static-access"})
    public static void main(String[] args) throws IOException {
        programa = lector.leerFichero("/home/ruben/Documentos/prueba");
        ejecutarPrograma();
	}

    private static void ejecutarPrograma(){
        for(int i=0 ; i<programa.size() ; i++){
            InstruccionPila d=programa.get(i);
            d.ejecutate(pila);
        }
    }
}
