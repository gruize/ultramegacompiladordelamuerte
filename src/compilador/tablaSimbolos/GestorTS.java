/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador.tablaSimbolos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

/**
 * Clase gestora de la Tabla de Símbolos. Contiene una Tabla de Símbolos por cada ámbito
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class GestorTS {

    /**
     * Indica el ámbito actual
     */
    private int ambitoActual;
    /**
     * Pila que contiene una Tabla de Símbolos por cada ámbito creado
     */
    private Stack<TS> ts;

    /**
     * Constructor que genera una Tabla de Símbolos vacía por defecto
     */
    public GestorTS() {
        this.ambitoActual = 1;
        this.ts = new Stack<TS>();
        this.ts.push(new TS());
    }

    /**
     * Crea una nueva Tabla de Símbolos para un nuevo ámbito
     */
    public void nuevoAmbito() {
        this.ts.push(new TS());
        this.ambitoActual++;
    }

    /**
     * Cierra un ámbito dentro del gestor
     */
    public void cerrarAmbitoActual() {
        this.ts.pop();
        this.ambitoActual--;
    }

    /**
     * Busca una entrada en todos los ámbitos de la Tabla de Símbolos
     * @param lexema Identificador del lexema a buscar
     * @return La entrada buscada
     */
    public EntradaTS buscaGlobal(String lexema) {

        Iterator<TS> iterator = ts.iterator();
        TS actual = null;
        EntradaTS resultado = null;

        while (iterator.hasNext() && resultado == null) {
            actual = iterator.next();
            resultado = actual.busca(lexema);
        }

        return resultado;
    }

    /**
     * Busca una entrada en el ámbito actual
     * @param lexema Identificador del lexema a buscar
     * @return La entrada del lexema buscado
     */
    public EntradaTS buscaLocal(String lexema) {
        return ts.lastElement().busca(lexema);
    }

    /**
     * Inserta una nueva entrada en el ámbito actual
     * @param lexema Identificador del lexema que queremos introducir
     * @return La entrada introducida
     */
    public EntradaTS insertaEntrada(String lexema) {

        TS actual = ts.lastElement();
        EntradaTS entrada = buscaLocal(lexema);

        if (entrada == null) {
            return actual.inserta(lexema, ambitoActual);
        } else {
            return entrada;
        }
    }

    /**
     * Modifica los atributos de la entrada que contiene el identificador especificado
     * @param lexema Identificador del lexema de la entrada que deseamos modificar
     * @param atributos Nuevos atributos que queremos modificar
     * @return La entrada modificada
     */
    public EntradaTS modificaEntrada(String lexema, ArrayList<String> atributos) {
        EntradaTS entrada = buscaGlobal(lexema);
        if (entrada != null) {
            entrada.setAtributos(atributos);
        }
        return entrada;
    }

    @Override
    public String toString() {
        String resultado = "";
        Iterator<TS> iterator = ts.iterator();
        int i = 1;

        while (iterator.hasNext()) {
            resultado += "Tabla " + i + " : " + iterator.next().toString();
            i++;
        }

        return resultado;
    }
}
