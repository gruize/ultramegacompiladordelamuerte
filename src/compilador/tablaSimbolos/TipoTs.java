/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.tablaSimbolos;

import java.util.ArrayList;

public class TipoTs {
    private final static TipoTs tipoNatural;
    private final static TipoTs tipoInteger;
    private final static TipoTs tipoFloat;
    private final static TipoTs tipoBoolean;
    private final static TipoTs tipoNull;
    private final static TipoTs tipoCharacter;
    

    private String t;
    private int tam;
    private ArrayList<Campo> campos;
    private ArrayList<Parametro> parametros;
    private String id;
    private int nelems;
    private TipoTs tbase;

    public TipoTs(){
        campos = new ArrayList<Campo>();
        parametros = new ArrayList<Parametro>();
    }
    public TipoTs(String t){
        this.t=t;
    }
    public TipoTs(String t, ArrayList<Parametro> param){
        this.t=t;
        parametros=param;
    }
    public TipoTs(String t, String id, int tam){
        this.t=t;
        this.id=id;
        this.tam=tam;
    }
    public TipoTs(String t, int nelems, TipoTs tbase, int tam){
        this.t=t;
        this.nelems=nelems;
        this.tbase=tbase;
        this.tam=tam;
    }
    public TipoTs(String t, TipoTs tbase, int tam){
        this.t=t;
        this.tbase=tbase;
        this.tam=tam;
    }
    public TipoTs(String t, int tam){
        this.t=t;
        this.tam=tam;
    }
    public TipoTs(String t, ArrayList<Campo> campos, int tam){
        this.t=t;
        this.campos=campos;
        this.tam=tam;
    }

    public String getT(){
        return t;
    }
    public void setT(String t){
        this.t=t;
    }
    public int getTam(){
        return tam;
    }
    public ArrayList<Campo> getCampos(){
        return campos;
    }
    public Campo getCampo(String lex){
        if (Campo.existeCampo(campos,lex)){
            for (int i=0; i<campos.size();i++){
                Campo c = campos.get(i);
                if (c.getId().equals(lex)){
                    return c;
                }
            }
        }
        return null;
    }

    public Campo getCampo(int i){
        return campos.get(i);
    }

    public ArrayList<Parametro> getParametros(){
        return parametros;
    }
    public String getId(){
        return id;
    }
    public TipoTs getBase(){
        return tbase;
    }

    public static TipoTs getTipoTsNatural() {
        return tipoNatural;
    }

    public static TipoTs getTipoTsInteger() {
        return tipoInteger;
    }

    public static TipoTs getTipoTsFloat() {
        return tipoFloat;
    }

    public static TipoTs getTipoTsBoolean() {
        return tipoBoolean;
    }

    public static TipoTs getTipoTsCharacter() {
        return tipoCharacter;
    }
    public static TipoTs getTipoTsNull(){
    	return tipoNull;
    }

    static {
        tipoInteger = new TipoTs("integer");
        tipoFloat = new TipoTs("float");
        tipoBoolean = new TipoTs("boolean");
        tipoCharacter = new TipoTs("character");
        tipoNatural = new TipoTs("natural");
        tipoNull = new TipoTs("null");
    }
}