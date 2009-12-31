
package compilador.tablaSimbolos;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Tabla de simbolos para cada ámbito
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class TS {

    /**
     * Estructura para representar la Tabla de Símbolos
     */
    private Hashtable<String, EntradaTS> tabla;

    /**
     * Constructor de la tabla
     */
    public TS() {
        tabla = new Hashtable<String, EntradaTS>();
    }

    /**
     * Inserta una nueva entrada en la tabla
     * @param lexema Identificador del lexema
     * @param ambito Ámbito del lexema
     * @return Entrada insertada
     */
    public EntradaTS inserta(String lexema, int ambito) {
        EntradaTS entrada = new EntradaTS(lexema, ambito);
        tabla.put(lexema, entrada);
        return entrada;
    }

    /**
     * Modifica los atributos de la entrada especificada
     * @param lexema Identificador de la entrada a modificar
     * @param atributos Nuevos atributos que deseamos modificar
     * @return La entrada modificada
     * @return null Si no existe una entrada con ese identificador
     */
    public EntradaTS modifica(String lexema, ArrayList<String> atributos) {
        EntradaTS entrada = busca(lexema);
        if (entrada != null) {
            entrada.setAtributos(atributos);
        }
        return entrada;
    }

    /**
     * Busca una entrada cuyo identificador coincida con el lexema de entrada
     * @param lexema Identificador a buscar
     * @return La entrada buscada
     * @return null Si no existe una entrada con el identificador introducido
     */
    public EntradaTS busca(String lexema) {
        if (tabla.contains(lexema)) {
            return tabla.get(lexema);
        }
        else {
            return null;
        }
    }

    @Override
    public String toString() {
        String resultado = "";
        for (Enumeration<EntradaTS> e = tabla.elements();e.hasMoreElements();) {
            resultado += e.nextElement().toString() + "\n";
        }
        return resultado;
    }


}
