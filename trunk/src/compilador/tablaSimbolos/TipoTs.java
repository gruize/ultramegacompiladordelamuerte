/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.tablaSimbolos;

import java.util.ArrayList;

public class TipoTs {
    private String t;
    private int tam;
    private ArrayList<Campo> campos;
    private ArrayList<Parametro> parametros;
    private String id;
    private int nelems;
    private TipoTs tbase;


    public TipoTs(){

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
    public int getTam(){
        return tam;
    }
    public ArrayList<Campo> getCampos(){
        return campos;
    }
    public ArrayList<Parametro> getParametros(){
        return parametros;
    }
    public String getId(){
        return id;
    }
    public TipoTs clone(TipoTs tipo){
        TipoTs res=new TipoTs();
        res.t = tipo.t;
        res.tam = tipo.tam;
        res.id = tipo.id;
        res.campos = (ArrayList<Campo>) tipo.campos.clone();
        res.parametros = (ArrayList<Parametro>) tipo.parametros.clone();
        return res;
    }
}