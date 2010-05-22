/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador.tablaSimbolos;

import java.util.Iterator;
import java.util.Stack;

/**
 * Clase gestora de la Tabla de S�mbolos. Contiene una Tabla de S�mbolos por cada �mbito
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class GestorTs {

    /**
     * Indica el �mbito actual
     */
    private int ambitoActual;
    /**
     * Pila que contiene una Tabla de S�mbolos por cada �mbito creado
     */
    private Stack<TablaSimbolos> ts;

    /**
     * Constructor que genera una Tabla de S�mbolos vac�a por defecto
     */
    public GestorTs() {
        this.ambitoActual = 1;
        this.ts = new Stack<TablaSimbolos>();
        this.ts.push(new TablaSimbolos());
    }

    /**
     * Crea una nueva Tabla de S�mbolos para un nuevo �mbito
     */
    public void crearTS() {
        this.ts.push(new TablaSimbolos());
        this.ambitoActual++;
    }

    /**
     * Cierra un �mbito dentro del gestor
     */
    public void cerrarAmbitoActual() {
        this.ts.pop();
        this.ambitoActual--;
    }

    /**
     * Busca una entrada en todos los �mbitos de la Tabla de S�mbolos
     * @param id Identificador del lexema a buscar
     * @return La entrada buscada
     */
    public InfoTs buscaGlobal(String id) {

        Iterator<TablaSimbolos> iterator = ts.iterator();
        TablaSimbolos actual = null;
        InfoTs resultado = null;

        while (iterator.hasNext() && resultado == null) {
            actual = iterator.next();
            resultado = TablaSimbolos.getProps(actual, id);
        }

        return resultado;
    }

    /**
     * Busca una entrada en el �mbito actual
     * @param id Identificador del lexema a buscar
     * @return La entrada del lexema buscado
     */
    public InfoTs buscaLocal(String id) {
        return TablaSimbolos.getProps(ts.lastElement(), id);
    }

    /**
     * Inserta una nueva entrada en el �mbito actual
     * @param lexema Identificador del lexema que queremos introducir
     * @return La entrada introducida
     */
    public void insertaEntrada(String id) {

        TablaSimbolos actual = ts.lastElement();
        InfoTs entrada = buscaLocal(id);

        if (entrada == null) {
            TablaSimbolos.inserta(actual, id, entrada);
        } else {
            //excepcion
        }
    }

    /**
     * Modifica los atributos de la entrada que contiene el identificador especificado
     * @param lexema Identificador del lexema de la entrada que deseamos modificar
     * @param atributos Nuevos atributos que queremos modificar
     * @return La entrada modificada
     */
    /*public InfoTs modificaEntrada(String lexema, ArrayList<String> atributos) {
    	InfoTs entrada = buscaGlobal(lexema);
        if (entrada != null) {
            entrada.setAtributos(atributos);
        }
        return entrada;
    }*/

    @Override
    public String toString() {
        String resultado = "";
        Iterator<TablaSimbolos> iterator = ts.iterator();
        int i = 1;

        while (iterator.hasNext()) {
            resultado += "Tabla " + i + " : " + iterator.next().toString();
            i++;
        }

        return resultado;
    }
}
