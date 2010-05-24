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

    public Stack<TablaSimbolos> getTs(){
        return ts;
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
    //creo que esta mal
    public void insertaEntrada(String id) {

        TablaSimbolos actual = ts.lastElement();
        InfoTs entrada = buscaLocal(id);

        if (entrada == null) {
            TablaSimbolos.inserta(actual, id, entrada);
        } else {
            //excepcion
        }
    }


   //------------------IMPLEMENTACION RUBEN------------------------------



    public static void inserta(GestorTs gestor, String id, InfoTs props){
        TablaSimbolos actual = gestor.getTs().lastElement();
        TablaSimbolos.inserta(actual, id, props);
    }
    
    public static boolean existe(GestorTs gestor, String id) {
        Iterator<TablaSimbolos> iterator = gestor.getTs().iterator();
        TablaSimbolos actual = null;
        boolean resultado = false;

        while (iterator.hasNext() && resultado == false) {
            actual = iterator.next();
            resultado = TablaSimbolos.existe(actual, id);
        }
        return resultado;
    }

    public static InfoTs getProps(GestorTs gestor,String id){
        Iterator<TablaSimbolos> iterator = gestor.getTs().iterator();
        TablaSimbolos actual = null;
        InfoTs resultado = null;
        boolean sigue = true;

        while (iterator.hasNext() && sigue == true) {
            actual = iterator.next();
            if (TablaSimbolos.existe(actual, id)){
                resultado = TablaSimbolos.getProps(actual, id);
                sigue=false;
            }
        }
        return resultado;
    }

    public static boolean refErronea(GestorTs gestor, TipoTs tipo){
        Iterator<TablaSimbolos> iterator = gestor.getTs().iterator();
        TablaSimbolos actual = null;
        boolean resultado = false;
        boolean sigue = true;

        while (iterator.hasNext() && sigue == false) {
            actual = iterator.next();
            if (TablaSimbolos.existe(actual, tipo.getId())){
                resultado = TablaSimbolos.refErronea(actual, tipo);
                sigue = false;
            }
        }
        return resultado;
    }
    public static boolean compatibles(TipoTs tipo1, TipoTs tipo2, GestorTs gestor){
        TablaSimbolos actual = gestor.getTs().lastElement();
        return TablaSimbolos.compatibles(tipo1, tipo2, actual);
    }
    public static TipoTs ref(TipoTs tipo, GestorTs gestor){
        Iterator<TablaSimbolos> iterator = gestor.getTs().iterator();
        TablaSimbolos actual = null;
        TipoTs resultado = null;
        boolean sigue = true;
        if (!tipo.getT().equals("ref")) return tipo;
        while (iterator.hasNext() && sigue == true) {
            actual = iterator.next();
            if (TablaSimbolos.existe(actual, tipo.getId())){
                resultado = TablaSimbolos.ref(tipo, actual);
                sigue = false;
            }
        }
        return resultado;
    }
    public static boolean esCompatibleConTipoBasico(TipoTs tipo, GestorTs gestor){
        TablaSimbolos actual = gestor.getTs().lastElement();
        return TablaSimbolos.esCompatibleConTipoBasico(tipo, actual);
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
