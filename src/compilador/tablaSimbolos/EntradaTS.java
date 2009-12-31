package compilador.tablaSimbolos;

import java.util.ArrayList;

/**
 * Clase que representa una entrada de una Tabla de Simbolos
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class EntradaTS {

    /**
     * Identificador del lexema
     */
    private String lexema;

    /**
     * Ámbito de la entrada
     */
    private int ambito;

    /**
     * Atributos de la entrada
     */
    private ArrayList<String> atributos;

    /**
     * Constructor de la entrada
     * @param lexema Identificador del lexema
     * @param ambito Ámbito al que pertenece
     */
    public EntradaTS(String lexema, int ambito) {
        this.lexema = lexema;
        this.ambito = ambito;
    }

    /**
     * Acceso al identificador del lexema
     * @return lexema
     */
    public String getLexema() {
        return this.lexema;
    }

    /**
     * Acceso al ámbito
     * @return ambito
     */
    public int getAmbito() {
        return this.ambito;
    }

    /**
     * Acceso a los atributos
     * @return atributos
     */
    public ArrayList<String> getAtributos() {
        return this.atributos;
    }

    /**
     * Modifica los atributos de la entrada
     * @param atributos Nuevos atributos
     */
    public void setAtributos(ArrayList<String> atributos) {
        this.atributos = atributos;
    }

    @Override
    public String toString() {
        return lexema;
    }

}
