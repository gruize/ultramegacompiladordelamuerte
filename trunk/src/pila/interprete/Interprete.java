package pila.interprete;

import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;
import pila.interprete.instrucciones.InstruccionInterprete;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import pila.Instruccion;

/**
 * El interprete es la "maquina virtual" que carga un
 * programa y luego ejecuta una tras otra sus
 * instrucciones hasta llegar a la condición de parada.
 *
 * Tiene un programa (donde guarda las instruccionInteprete
 * en orden), una pila (donde se apilan/desapilan DatoPila),
 * un contador de programa (cp) que indica que instrucción
 * está ejecutando, una memoria (donde guardar los DatoPila
 * que en el lenguaje de alto nivel son variables) y un
 * booleano "parar", que al ponerse a true detendra la
 * ejecución del programa
 */
public class Interprete {
    /**
     * Un ArrayDeque es una estructura que puede funcionar
     * como pila o como cola con costes amortizados
     * constantes
     */
    private ArrayDeque<DatoPila> pila;
    private ArrayList<Instruccion> programa;
    private DatoPila[] memoria;
    private boolean parar; //true si ha acabado
    private int cp; //el contador de programa

    /**
     * Crea un interprete con tantas posiciones de memoria
     * como se le indique
     * @param longMem el tamaño de la memoria
     */
    public Interprete(int longMem) {
        programa = null;
        pila = null;
        memoria = new DatoPila[longMem];
    }

    /**
     * Crea un interprete con un tamaño de memoria por
     * defecto (100)
     */
    public Interprete() {
        this(100);
    }

    /**
     * Dado un fichero, crea un LectorPila del que obtiene
     * un programa válido
     * @param f el fichero binario fuente
     * @throws FileNotFoundException Si el fichero pasado es
     * inválido
     * @throws IOException Si ocurren errores de entrada y
     * salida (por ejemplo, leer cuando se esta al final
     * del fichero)
     * @throws LectorExc Si el programa era invalido (por ejemplo,
     * tenía un dato de tipo desconocido o un apiladir sin argumento)
     */
    public void leerPrograma(File f) throws FileNotFoundException, IOException, LectorExc {
        LectorPila lector = new LectorPila();
        programa = lector.leerPrograma(f);
        pila = new ArrayDeque<DatoPila>();
    }

    /**
     * Ejecuta el programa que se haya leído con anterioridad
     * @throws InstruccionExc si ocurre un error en ejecución
     * @throws NullPointerException si no se ha cargado ningún programa
     */
    public void ejecutarPrograma() throws InstruccionExc {
        if(programa != null)
            throw new NullPointerException("Programa no iniciado");
        setCp(0);
        setParar(false);
        while(!isParar()) {
            if(((InstruccionInterprete)programa.get(getCp())).ejecutate(this))
                cp++;
        }
    }

    /**
     * @return la pila
     */
    public ArrayDeque<DatoPila> getPila() {
        return pila;
    }

    /**
     * @return si se esta o no con la máquina parada
     */
    public boolean isParar() {
        return parar;
    }

    /**
     * @param true si la máquina debe pararse
     */
    public void setParar(boolean parar) {
        this.parar = parar;
    }

    /**
     * @return el contador de programa (la posición de la
     * instrucción que se esta ejecutando en este momento)
     */
    public int getCp() {
        return cp;
    }

    /**
     * Fija el contador de programa a un valor dado
     * @param el nuevo contador de programa
     */
    public void setCp(int cp) {
        this.cp = cp;
    }

    /**
     * @return la memoria de la máquina
     */
    public DatoPila[] getMemoria() {
        return memoria;
    }
}
