package compilador.tablaSimbolos;

import java.util.Hashtable;

/**
 * Tabla de simbolos para cada ámbito
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class TablaSimbolos {
	
	Hashtable<String,InfoTs> ht;
	
	public TablaSimbolos(){
		ht=new Hashtable<String,InfoTs>();
	}
	
    /**
     * Devuelve una tabla de símbolos vacía
     */
    public static TablaSimbolos creaTS() {
        TablaSimbolos t = new TablaSimbolos();
        return t;
    }

    /**
     * Inserta una nueva entrada en la tabla
     * @param ts Tabla donde se va a insertar la entrada
     * @param id Identificador del lexema
     * @param tipo Tipo del identificador
     * @return La tabla de símbolos resultante de añadir la entrada con id y tipo a ts
     */
    public static TablaSimbolos inserta(TablaSimbolos ts, String id, String tipo, int dir) {
    	if (!existe(ts,id)){
    		Hashtable<String,InfoTs> ht= ts.getHashtable();
    		ht.put(id, new InfoTs(tipo,dir));
    	}
        return ts;
    }

    /**
     * Indica si existe una entrada en la tabla de símbolos con el identificador especificado
     * @param ts Tabla donde se va a comprobar la entrada
     * @param id Identificador de la entrada a comprobar
     * @return True si existe la entrada. False en caso contrario.
     */
    public static boolean existe(TablaSimbolos ts, String id) {
    	Hashtable<String,InfoTs> ht= ts.getHashtable();
        return ht.contains(id);
    }

    /**
     * Devuelve el tipo de la entrada especificada
     * @param ts Tabla donde se va a buscar la entrada
     * @param id Identificador de la entrada a modificar
     * @return El tipo de la entrada especificada si dicha entrada existe. Null en caso contrario.
     */
    public static String gettipo(TablaSimbolos ts, String id) {
    	Hashtable<String,InfoTs> ht= ts.getHashtable();
        if (ht.contains(id)) {
        	InfoTs i = ht.get(id);
        	return i.getTipo();
        }
        return null;
    }
    
    public Hashtable<String,InfoTs> getHashtable(){
    	return ht;
    }
}
