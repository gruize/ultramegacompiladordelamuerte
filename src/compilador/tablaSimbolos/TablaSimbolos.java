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
    public static TablaSimbolos inserta(TablaSimbolos ts, String id, InfoTs props) {
    	if (!existe(ts,id)){
    		Hashtable<String,InfoTs> ht= ts.getHashtable();
    		ht.put(id, props);
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
        return ht.containsKey(id);
    }

    public static InfoTs getProps(TablaSimbolos ts,String id){
        Hashtable<String,InfoTs> ht= ts.getHashtable();
        if (ht.containsKey(id)) {
        	InfoTs i = ht.get(id);
        	return i;
        }
        return null;
    }
    
    public Hashtable<String,InfoTs> getHashtable(){
    	return ht;
    }
    
    public void setHashTable(Hashtable<String,InfoTs> ht){
    	this.ht=ht;
    }

    public static boolean existeRef(TablaSimbolos ts, TipoTs tipo){
        return false;
        //sin implementar
    }
    public static boolean compatibles(TipoTs tipo1, TipoTs tipo2, TablaSimbolos ts){
        return false;
        //sin implementar
    }
    public static TipoTs ref(TipoTs tipo, TablaSimbolos ts){
        return null;
        //sin implementar
    }
}
