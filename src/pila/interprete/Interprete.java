package pila.interprete;

import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.instrucciones.InstruccionInterprete;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import pila.interprete.excepiones.InterpreteExc;
/**
 *
 * @author ruben
 */
public class Interprete {
    private ArrayList<InstruccionInterprete> programa;
    private ArrayDeque<DatoPila> pila;
    private DatoPila[] memoria;
    private boolean parar; //true si ha acabado
    private int cp; //el contador de programa

    public Interprete(int longMem) {
        programa = null;
        pila = null;
        memoria = new DatoPila[longMem];
    }

    public Interprete() {
        this(100);
    }

    public void leerPrograma(File f) throws FileNotFoundException, IOException {
        LectorPila lector = new LectorPila();
        programa = lector.leerFuente(f);
        setPila(new ArrayDeque<DatoPila>());
    }

    private void ejecutarPrograma() throws InterpreteExc, InstruccionExc {
        if(programa != null)
            throw new InterpreteExc("No hay programa a ejecutar");
        setCp(0);
        setParar(false);
        while(!isParar()) {
            programa.get(getCp()).ejecutate(this);
        }
    }

    /**
     * @return the pila
     */
    public ArrayDeque<DatoPila> getPila() {
        return pila;
    }

    /**
     * @param pila the pila to set
     */
    public void setPila(ArrayDeque<DatoPila> pila) {
        this.pila = pila;
    }

    /**
     * @return the parar
     */
    public boolean isParar() {
        return parar;
    }

    /**
     * @param parar the parar to set
     */
    public void setParar(boolean parar) {
        this.parar = parar;
    }

    /**
     * @return the cp
     */
    public int getCp() {
        return cp;
    }

    /**
     * @param cp the cp to set
     */
    public void setCp(int cp) {
        this.cp = cp;
    }

    /**
     * @return the memoria
     */
    public DatoPila[] getMemoria() {
        return memoria;
    }
}
