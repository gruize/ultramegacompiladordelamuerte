package compilador.tablaSimbolos;

import java.util.ArrayList;
import java.util.Hashtable;


/**
 * Tabla de simbolos para cada ámbito
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class TablaSimbolos {
	
	
	Hashtable<String,InfoTs> ht;
        static ArrayList<Object[]> visitados;
	
	public TablaSimbolos(){
		ht=new Hashtable<String,InfoTs>();
                visitados = new ArrayList<Object[]>();
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
    	InfoTs actual = ts.getHashtable().get(id);
        //if (!existe(ts,id) || (actual != null && actual.getClase().equals("proc") && props.getClase().equals("proc"))){
    	if (existe(ts,id)) ts.getHashtable().remove(id);
    	Hashtable<String,InfoTs> ht= ts.getHashtable();
    	ht.put(id, props);
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

    public static boolean refErronea(TablaSimbolos ts, TipoTs tipo){
        return tipo.getT().equals("ref") && !TablaSimbolos.existe(ts, tipo.getId());
    }
    public static boolean compatibles(TipoTs tipo1, TipoTs tipo2, TablaSimbolos ts){
        visitados = null;
        visitados = new ArrayList<Object[]>();
        Object[] par = new Object[]{tipo1,tipo2};
        if (visitados.contains(par))
            return true;
        else visitados.add(par);

        if (tipo1.getT().equals("natural")){
        	if (tipo2.getT().equals("natural")) return true;
        }
        else if  (tipo1.getT().equals("integer")){
        	if (tipo2.getT().equals("natural") ||
        		tipo2.getT().equals("integer")	) 
        		return true; 
        }
        else if  (tipo1.getT().equals("float")){
        	if (tipo2.getT().equals("natural") ||
        		tipo2.getT().equals("integer") ||
        		tipo2.getT().equals("float")) 
        		return true; 
        }
        else if ((tipo1.getT().equals("boolean") && tipo2.getT().equals("boolean")) ||
                (tipo1.getT().equals("character") && tipo2.getT().equals("character")))
            return true;
        else if (tipo1.getT().equals("ref"))
                return compatibles(TablaSimbolos.ref(tipo1, ts),tipo2,ts);
        else if (tipo2.getT().equals("ref"))
                return compatibles(tipo1,TablaSimbolos.ref(tipo2, ts),ts);
        else if (tipo1.getT().equals("puntero"))
                return compatibles(TablaSimbolos.getProps(ts,tipo1.getId()).getTipo(),tipo2,ts);
        else if (tipo2.getT().equals("puntero"))
                return compatibles(tipo1,TablaSimbolos.getProps(ts,tipo2.getId()).getTipo(),ts);
        else if (tipo1.getT().equals("array") && tipo2.getT().equals("array"))
                return compatibles(tipo1.getBase(),tipo2.getBase(),ts);
        else if ((tipo1.getT().equals("record") && tipo2.getT().equals("record")) &&
                 tipo1.getCampos().size() == tipo2.getCampos().size()){
                 for (int i=0; i<tipo1.getCampos().size(); i++){
                     if (!compatibles(tipo1.getCampo(i).getTipo(),tipo2.getCampo(i).getTipo(), ts))
                         return false;
                 }
                 return true;
        }
        else if (tipo1.getT().equals("puntero") && tipo2.getT().equals("puntero"))
                return compatibles(tipo1.getBase(),tipo2.getBase(),ts);
        return false;
    }
    public static TipoTs ref(TipoTs tipo, TablaSimbolos ts){
        TipoTs res = null;
        if (tipo.getT().equals("ref"))
            if (TablaSimbolos.existe(ts, tipo.getId()))
                return ref(TablaSimbolos.getProps(ts, tipo.getId()).getTipo(), ts);
            else res = new TipoTs("error");
        else return tipo;
        return res;
    }
    public static boolean esCompatibleConTipoBasico(TipoTs tipo, TablaSimbolos ts){
        return compatibles(tipo, TipoTs.getTipoTsBoolean(), ts) ||
                compatibles(tipo, TipoTs.getTipoTsCharacter(), ts) ||
                compatibles(tipo, TipoTs.getTipoTsFloat(), ts) ||
                compatibles(tipo, TipoTs.getTipoTsInteger(), ts) ||
                compatibles(tipo, TipoTs.getTipoTsNatural(), ts);
    }
}
