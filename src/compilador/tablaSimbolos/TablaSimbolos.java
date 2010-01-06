package compilador.tablaSimbolos;

import java.util.Hashtable;

/**
 * Tabla de simbolos para cada ámbito
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class TablaSimbolos {

    /**
     * Devuelve una tabla de símbolos vacía
     */
    public Hashtable<String, String> creaTS() {
        return new Hashtable<String, String>();
    }

    /**
     * Inserta una nueva entrada en la tabla
     * @param ts Tabla donde se va a insertar la entrada
     * @param id Identificador del lexema
     * @param tipo Tipo del identificador
     * @return La tabla de símbolos resultante de añadir la entrada con id y tipo a ts
     */
    public Hashtable<String, String> inserta(Hashtable<String, String> ts, String id, String tipo) {
        if (!existe(ts, id)) {
            ts.put(id, tipo);
        }
        return ts;
    }

    /**
     * Indica si existe una entrada en la tabla de símbolos con el identificador especificado
     * @param ts Tabla donde se va a comprobar la entrada
     * @param id Identificador de la entrada a comprobar
     * @return True si existe la entrada. False en caso contrario.
     */
    public boolean existe(Hashtable<String, String> ts, String id) {
        return ts.contains(id);
    }

    /**
     * Devuelve el tipo de la entrada especificada
     * @param ts Tabla donde se va a buscar la entrada
     * @param id Identificador de la entrada a modificar
     * @return El tipo de la entrada especificada si dicha entrada existe. Null en caso contrario.
     */
    public String gettipo(Hashtable<String, String> ts, String id) {
        if (ts.contains(id)) {
            return ts.get(id);
        } else {
            return null;
        }
    }
}
