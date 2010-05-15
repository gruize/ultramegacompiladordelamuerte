package compilador.traductor;

import java.util.ArrayList;
import java.util.Iterator;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.LectorExc;

import pila.interprete.instrucciones.*;
import pila.interprete.datos.*;
import compilador.lexico.Tokens.*;
import compilador.tablaSimbolos.InfoTs;
import compilador.tablaSimbolos.TablaSimbolos;
import compilador.tablaSimbolos.InfoTs.Tipos;

/**
 * Esta clase contiene las funciones de traducción que sólo dependen de la
 * gramática y no del código  que se va a generar.
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 *
 */
public abstract class Traductor {
    //*****ATRIBUTOS****
    public static final int longInicio = 4;
    public static final int longApilaRet = 5;
    public static final int longPrologo = 13;
    public static final int longEpilogo = 13;
    public static final int longInicioPaso = 3;
    public static final int longFinPaso = 1;
    public static final int longDireccionPalFormal = 2;
    public static final int longPasoParametro = 1;

    protected ArrayList<Token> arrayTokens;
    protected Codigo cod;
    protected TablaSimbolos ts;
    protected int etq;
    protected int dir;
    protected int n;
    protected int i_token;
    protected ArrayList<String> pend;
    protected ArrayList<ErrorTraductor> errores;

    protected enum Operaciones {

        SUMA, RESTA, MULT, DIV, MENOR, MAYOR, MENORIG, MAYORIG, IGUAL,
        DISTINTO, OR, AND, NOT, MOD, VALORABS, SHL, SHR, NEG, CASTENT,
        CASTREAL, CASTCHAR, CASTNAT
    }
    protected enum Fallo {

        NO, FALTAL, NO_FATAL
    }

    public Traductor(ArrayList<Token> tokens) {
        arrayTokens = tokens;
        cod = new Codigo();
        etq = 0;
        i_token = 0;
        pend = new ArrayList<String>();
        errores = new ArrayList<ErrorTraductor>();
        ts = new TablaSimbolos();
    }

    //****FUNCIONES PRINCIPALES
    public void traducir(String nombreClase) throws Exception {
        Codigo cod = new Codigo();
        try {
            Programa();
        } catch (Exception e) {
            System.out.println("Traducción no terminada: Error Fatal:");
            //System.out.println(e.getMessage());
            e.printStackTrace();
            throw new Exception("Traducción acabada con errores fatales");
        }
        if (!errores.isEmpty()) {
            System.out.println("Traducción acabada con errores no fatales:");
            imprimirErrores();
            throw new Exception("Traducción acabada con errores no fatales:\n"+dameErrores());
        }
        /*
         * Calcular la altura maxima de la pila es algo complejo, pero al no
         * usar en la traduccion ninguna instruccion que apile dos elementos en
         * la pila, su altura nunca podra ser mayor que el numero de instrucciones
         * del programa
         */
    }

    private String dameErrores() {
        StringBuilder sb = new StringBuilder();
        Iterator<ErrorTraductor> it = errores.iterator();
        while (it.hasNext()) {
            ErrorTraductor e = it.next();
            sb.append(e.getDesc());
        }
        return new String(sb);
    }
    private void imprimirErrores() {
        System.out.println(dameErrores());
    }
    protected Token sigToken() {
        Token t;
        if (i_token < arrayTokens.size()) {
            t = arrayTokens.get(i_token);
            i_token++;
        } else {
            t = new Token(-1);
            i_token++;
        }
        return t;
    }
    protected void atrasToken() {
        i_token--;
    }
    protected String textoError() {
        Token t;
        if (i_token >= arrayTokens.size()) {
            t = arrayTokens.get(arrayTokens.size() - 1);
        } else {
            t = arrayTokens.get(i_token);
        }
        int l = t.getLinea();
        return " en la línea " + l + ".";
    }

    //****FUNCIONES TRADUCCION****
    protected void inicio(int numNiveles, int tamDatos) throws LectorExc, DatoExc{
        cod.appendIns(new Apilar(new Nat(numNiveles +2)));
        cod.appendIns(new DesapilarDir(new Nat(1)));
        cod.appendIns(new Apilar(new Nat(1+tamDatos+numNiveles)));
        cod.appendIns(new DesapilarDir(new Nat(0)));
    }
    protected void apilaRet(int ret) throws DatoExc, LectorExc{
        cod.appendIns(new ApilarDir(new Nat(0)));
        cod.appendIns(new Apilar(new Nat(1)));
        cod.appendIns(new Suma());
        cod.appendIns(new Apilar(new Nat(ret)));
        cod.appendIns(new DesapilaInd());
    }
    protected void prologo(int nivel, int tamLocales) throws DatoExc, LectorExc{
        cod.appendIns(new ApilarDir(new Nat(0)));
        cod.appendIns(new Apilar(new Nat(2)));
        cod.appendIns(new ApilarDir(new Nat(1+nivel)));
        cod.appendIns(new DesapilaInd());
        cod.appendIns(new ApilarDir(new Nat(0)));
        cod.appendIns(new Apilar(new Nat(0)));
        cod.appendIns(new Suma());
        cod.appendIns(new DesapilarDir(new Nat(1+nivel)));
        cod.appendIns(new ApilarDir(new Nat(0)));
        cod.appendIns(new Apilar(new Nat(tamLocales+2)));
        cod.appendIns(new Suma());
        cod.appendIns(new DesapilarDir());
    }
    protected void epilogo(int nivel) throws LectorExc, DatoExc{
        cod.appendIns(new ApilarDir(new Nat(1+nivel)));
        cod.appendIns(new Apilar(new Nat(2)));
        cod.appendIns(new Resta());
        cod.appendIns(new ApilarInd());
        cod.appendIns(new ApilarDir(new Nat(1+nivel)));
        cod.appendIns(new Apilar(new Nat(3)));
        cod.appendIns(new Resta());
        cod.appendIns(new Copia());
        cod.appendIns(new DesapilarDir(new Nat(0)));
        cod.appendIns(new Apilar(new Nat(2)));
        cod.appendIns(new ApilarInd());

    }
    protected void accesoVar(InfoTs props) throws LectorExc, DatoExc{
        cod.appendIns(new ApilarDir(new Nat(1+props.getNivel())));
        cod.appendIns(new Apilar(new Nat(props.getDir())));
        cod.appendIns(new Suma());
        if (props.getClase().equals("pvar"))
            cod.appendIns(new ApilarInd());
    }
    protected int longAccesoVar(InfoTs props){
        int resp;
        if (props.getClase().equals("pvar"))
            resp=4;
        else resp=3;
        return resp;
    }
    protected void inicioPaso() throws DatoExc, LectorExc{
        cod.appendIns(new ApilarDir(new Nat(0)));
        cod.appendIns(new Apilar(new Nat(3)));
        cod.appendIns(new Suma());
    }
    protected void finPaso() throws LectorExc{
        cod.appendIns(new Desapilar());
    }
    protected void direccionPalFormal(InfoTs props) throws LectorExc, DatoExc{
        cod.appendIns(new Apilar(new Nat(props.getDir())));
        cod.appendIns(new Suma());
    }
    protected void pasoParametro(String modoReal, InfoTs pFormal) throws LectorExc, DatoExc{
        if (pFormal.getModo().equals("valor") && modoReal.equals("var"))
            cod.appendIns(new Mueve(pFormal.getTipo().getTam()));
        else cod.appendIns(new DesapilaInd());
    }

    //****FUNCIONES AUXILIARES****
    //Devuelven true si encuentran el token esperado
    protected boolean ampersand() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Separador)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean dosPuntos() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Dos_puntos)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean puntoYComa() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Punto_coma)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean in() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof In)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean out() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Out)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean abrePar() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Parentesis_a)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean cierraPar() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Parentesis_c)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean dosPuntosIgual() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Dos_puntos_ig)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean valorAbs() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Absoluto)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected String identificador() {
        Token t = sigToken();
        if (!(t instanceof Identificador)) {
            atrasToken();
            return null;
        } else {
            return t.getLex();
        }
    }
    protected String numero() {
        Token t = sigToken();
        if (!(t instanceof LitNat)) {
            atrasToken();
            return null;
        } else {
            return t.getLex();
        }
    }
    protected boolean tipo() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Tipo)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean procedure() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Procedure)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean igual() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Igual)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean coma() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Coma)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean var() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Var)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean array() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof MiArray)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean circunflejo() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Circunflejo)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean record() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Record)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean abreCorchete() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Corchete_a)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean cierraCorchete() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Corchete_c)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean of() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Of)){
            atrasToken();
            error = true;
        }
        return !error;
    }

    //-----------------------------------------
    //-------implementación--------------------
    protected boolean Programa() throws Exception {
        boolean error1 = false;

        //etq = longInicio + 1;
        //dir = 0;
        //n = 0;
        //ts = new TablaSimbolos();
        //cod = new Codigo();

        boolean error2 = Declaraciones();
        //ERROR fatal si no hay ampersand
        if (!ampersand()) {
            throw new Exception("FATAL: & no encontrado" + textoError());
        }

        //inicio(n,dir);
        //cod.appendIns(new IrA(new Nat(etq)));

        boolean error3 = Instrucciones();

        //cod.append(new Stop());
        //error1 = error2 || error3;

        if (error1) {
            errores.add(
                    new ErrorTraductor("Info: Se han detectado errores en el programa. "
                    + "El código generado puede no ser válido."));
        }
        return error1;
    }
    private boolean Declaraciones() throws Exception {
        boolean errorh3 = false;

        Object[] decRes = Declaracion();
        boolean error2 = (Boolean) decRes[0];
        int tam2 = (Integer) decRes[1];
        String id2 = (String) decRes[2];
        InfoTs props2 = (InfoTs) decRes[3];

        //dir+=tam2;
        //errorh3= error2 || (TablaSimbolos.existe(ts, id2) && (TablaSimbolos.getProps(ts,id2).getNivel() == n));
        //TablaSimbolos.inserta(ts,id2,props2);
        //if (props2.getClase().equals("tipo"))
        //    pend.remove(id2);

        boolean error3 = DeclaracionesRec(errorh3);

        return error3;
    }
    protected boolean DeclaracionesRec(boolean errorh1) throws Exception {
        boolean error1 = false;
        if (puntoYComa()) {//no lambda

            Object[] decRes = Declaracion();
            boolean error2 = (Boolean) decRes[0];
            int tam2 = (Integer) decRes[1];
            String id2 = (String) decRes[2];
            InfoTs props2 = (InfoTs) decRes[3];

            //dir+=tam2;
            //boolean errorh3= errorh1 || error2 || (TablaSimbolos.existe(ts, id2) && (TablaSimbolos.getProps(ts,id2).getNivel() == n));

            boolean error3 = DeclaracionesRec(errorh3);

            //error1 = error3;
        } else {
            error1 = errorh1;
        return error1;
        }

        return error1;
    }
    private Object[] Declaracion() throws Exception {
        boolean error1 = false;
        int tam1 = 0;
        String id1 = "";
        InfoTs props1 = null;

        Object[] decVarRes = DeclaracionVariable();
        Object[] decProcRes = DeclaracionProcedimiento();
        Object[] decTipoRes = DeclaracionTipo();
        boolean error2V = (Boolean) decVarRes[0];
        boolean error2P = (Boolean) decProcRes[0];
        boolean error2T = (Boolean) decTipoRes[0];
        String id2;
        InfoTs props2;

        if (!error2V){
            id2 = (String) decVarRes[1];
            props2 = (InfoTs) decVarRes[2];

            //error1 = true;
            //id1= id2;
            //tam1 = props2.getTipo().getTam();
            //props1= props2 ++ <dir:dir>;

        }else if (! error2P){
            id2 = (String) decProcRes[1];
            props2 = (InfoTs) decProcRes[2];

            //error1 = true;
            //tam1= 0;
            //id1=id2;
            //props1=props2;

        }else if (! error2T){
            id2 = (String) decTipoRes[1];

            //tam1 = 0;
            //error1 = true;
            //id1=id2;
            //props1 = <>;
        }else {
            error1 = true;
            errores.add(new ErrorTraductor("Hay errores en la(s) declaraciones(es) " + textoError()));
        }
        
        return new Object[]{error1, tam1, id1, props1};

    }
    protected Object[] DeclaracionTipo(){
        boolean error1 = false;
        String id1 = "";
        InfoTs props1 = null;

        if( tipo()){
            String lex= identificador();
            if(igual()){
                Object[] tipoRes = Tipo();
                boolean error2 = (Boolean) tipoRes[0];
                Tipos tipo2 = (Tipos) tipoRes[1];

                //id1 = lex;
                //props1 = <clase: tipo, tipo: tipo2, nivel: n>
                //error1 = error2  || (TablaSimbolos.existe(ts,lex) && (not(existeRef(ts,tipo2))));
            }
        }
        return new Object[]{error1, id1, props1};
    }
    protected Object[] DeclaracionVariable(){
        boolean error1 = false;
        String id1 = "";
        InfoTs props1 = null;

        String lex= identificador();
        if(dosPuntos()){
            Object[] tipoRes = Tipo();
            boolean error2 = (Boolean) tipoRes[0];
            Tipos tipo2 = (Tipos) tipoRes[1];

            //id1 = lex;
            //props1 = <clase: var, tipo: tipo2, nivel: n>
            //error1 = error2  || (TablaSimbolos.existe(ts,lex) && (not(existeRef(ts,tipo2))));
        }
        return new Object[]{error1, id1, props1};
    }
    protected Object[] DeclaracionProcedimiento(){
        boolean error1 = false;
        String id1 = "";
        InfoTs props1 = null;

        if (procedure()){
            String lex= Tipo();
            //ts_aux = ts;
            //ts = creaTS(ts_aux)
            //n += 1;
        }

        boolean error2 = FParametros();

        //ts = inserta(ts, lex, <clase:proc, tipo: <t:proc, parametros: params>,  nivel: n>)
	//params = {}

        Object[] bloqueRes=Bloque();
        boolean error3= (Boolean) bloqueRes[0];
        int inicio3 = (Integer) bloqueRes[1];

        //error1 = error2 v error3 v (existeID(FParametros.ts, id.lex)  ٨ ts[lex].nivel = n)
	//id1 = lex
	//props1 = <clase:proc, tipo: <t:proc, parametros: params>, nivel: n, inicio: inicio3>
	//n - = 1;
	//ts = ts_aux

        return new Object[]{error1, id1, props1};

    }
    protected Object[] Bloque() throws Exception{
        boolean error1=false;
        int inicio1 = 0;

        boolean error2=Declaraciones();

        if (!error2){
            if (ampersand()){
                //inicio = etq
                //etq + = longPrologo
                //cod += prologo(n, dir)
            }
        }
        else{
            //inicio1 = etq
            //etq += longPrologo
            //cod += prologo(n,dir)
        }

        boolean error3 = Instrucciones();

        //error1 = error2 v error3
	//etq += longEpilogo + 1
	//cod += epilogo(n)
	//cod += ir-ind

        return new Object[]{error1, inicio1};
    }
    protected boolean FParametros(){
        boolean error1;
        if (abrePar()){
            boolean error2 = LFParametros();
            //error1=error2
            if (cierraPar()){
            }
        }
        else{
            //dir = 0
            //error1 = false
        }
        return error1;
    }
    protected boolean LFParametros(){
        boolean error1=false;
        boolean errorh3 = false;

        Object[] FParamRes=FParametro();
        boolean error2 = (Boolean) FParamRes[0];
        String id2 = (String) FParamRes[1];
        InfoTs props2 = (InfoTs) FParamRes[2];
        int tam2 = (Integer) FParamRes[3];

        //errorh3 = error2 v existeID(ts,id2)  ٨ ts[id2].nivel = n)
	//ts = inserta(ts, id2, FParametro.props ++ <dir:0>)
	//dir = tam2
        //return error1;

        boolean error3 = LFParametrosRec(errorh3);

        return error1;
    }
    protected boolean LFParametrosRec(boolean errorh1){
        boolean error1= false;
        boolean errorh3 = false;

        if (coma()){
            Object[] FParamRes=FParametro();
            boolean error2 = (Boolean) FParamRes[0];
            String id2 = (String) FParamRes[1];
            InfoTs props2 = (InfoTs) FParamRes[2];
            int tam2 = (Integer) FParamRes[3];

            //dir += tam2
            //errorh3 = errorh1 v error2 v (existeID(ts,id2)  ٨ ts[id2].nivel = n)
            //ts = inserta(ts, id2, props2)

            boolean error3 = LFParametrosRec(erro3h);

            //error1 = error3
        }
        else{
            //error1 = errorh1
        }
        return error1;
    }
    protected Object[] FParametro(){
        boolean error1 = false;
        String id1 = "";
        InfoTs props1 = null;
        int tam1 = 0;

        Object[] tipoRes= Tipo();
        boolean error2= (Boolean) tipoRes[0];
        Tipos tipo2 = (Tipos) tipoRes[1];

        String lex = id();

        if (var()){
            //tam1      = 1
            //params    += <modo: variable, tipo: tipo2, dir: dir>
            //id = lex
            //props1 = <clase: pvar, tipo: tipo2, nivel: n>
            //error1 = error2
        }
        else{
            //tam1 = tipo2.tam
            //params    += <modo: valor, tipo: tipo2, dir: dir>
            //id1 = lex
            //props1 = <clase: var, tipo: tipo2, nivel: n>
            //error1 = error2
        }

        return new Object[]{error1,id1,props1,tam1};
    }
    protected Object[] Tipo(){
        Token t= sigToken();
        if (t instanceof Identificador){
            return Tipo_id(t);
        }else if (t instanceof compilador.lexico.Tokens.Token_Boolean){
            return Tipo_Boolean();
        }else if (t instanceof compilador.lexico.Tokens.Token_Character){
            return Tipo_Character();
        }else if (t instanceof compilador.lexico.Tokens.Token_Float){
            return Tipo_Float();
        }else if (t instanceof compilador.lexico.Tokens.Token_Natural){
            return Tipo_Natural();
        }else if (t instanceof compilador.lexico.Tokens.Token_Integer){
            return Tipo_Integer();
        }else if (array()){
            return Tipo_Array();
        }else if (circunflejo()){
            return Tipo_Puntero();
        }else if (record()){
            return Tipo_Record();
        }else
            throw new Exception("Error: se esperaba un literal" + textoError());
    }
    protected Object[] Tipo_id(Token t){
        boolean error1 =false;
        Tipos tipo1 = null;
        String lex= t.getLex();

        /*tipo1 =
        <
            t:ref,
            id:lex,
            tam:ts[lex].tipo.tam
        >
	error1 =  si existeID(ts,lex)
			ts[lex].clase != tipo
		        sino
                            false
	pend +=  si (¬existeID(ts,lex))
			{lex}
		        sino
                        ⵁ*/

        return new Object[]{error1, tipo1}
    }
    protected Object[] Tipo_Boolean(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:boolean,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1}
    }
    protected Object[] Tipo_Character(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:character,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1}
    }
    protected Object[] Tipo_Float(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:float,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1}
    }
    protected Object[] Tipo_Natural(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:natural,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1}
    }
    protected Object[] Tipo_Integer(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:integer,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1}
    }
    protected Object[] Tipo_Array(){
        boolean error1 =false;
        Tipos tipo1 = null;
        
        if (abreCorchete())
            String lex = numero();
            if (cierraCorchete())
                if (of()){
                    Object[] TipoRes=Tipo();
                    boolean error2 = (Boolean) tipoRes[0];
                    Tipos tipo2 = (Tipos) tipoRes[1];
                    
                    /*tipo1 =
        		<
                            t:array,
                            nelems:valorDe(lex),
                            tbase:tipo2,
                            tam:valorDe(lex)*tipo2.tam
                        >
                    error1 = tipo2.error v ¬existeRef (ts ,tipo2*/
                }

        return new Object[]{error1, tipo1}
    }
    protected Object[] Tipo_Puntero(){
        boolean error1 =false;
        Tipos tipo1 = null;

        Object[] TipoRes=Tipo();
        boolean error2 = (Boolean) tipoRes[0];
        Tipos tipo2 = (Tipos) tipoRes[1];

        /*tipo1 =
	        <
	            t:puntero,
	            tbase:tipo2,
	            tam:1
	        >
	error1 = error2*/
        return new Object[]{error1, tipo1}
    }
    protected Object[] Tipo_Record(){
        boolean error1 =false;
        Tipos tipo1 = null;

        return new Object[]{error1, tipo1}
    }


//Literal(out: tipo1, cod1)
    protected Object[] Literal() throws Exception {
        Token t = sigToken();
        if (t instanceof Identificador) {
            return Literal_Id(t);
        } else if (t instanceof LitNat) {
            return Literal_LitNat(t);
        } else if (t instanceof LitFlo) {
            return Literal_LitFlo(t);
        } else if (t instanceof LitTrue) {
            return Literal_LitTrue();
        } else if (t instanceof LitFalse) {
            return Literal_LitFalse();
        } else if (t instanceof LitCha) {
            return Literal_LitCha(t);
        } else {
            throw new Exception("Error: se esperaba un literal" + textoError());
        }
    }
    //Instrucciones(out: error1,cod1) →
    protected Object[] Instrucciones() throws Exception {
        boolean error1;
        Codigo cod1;

        Object[] resIns = Instruccion();
        boolean error2 = (Boolean) resIns[0];
        Codigo cod2 = (Codigo) resIns[1];

        Object[] resInsFact = InstruccionesFact(error2, cod2);
        error1 = (Boolean) resInsFact[0];
        cod1 = (Codigo) resInsFact[1];
        return new Object[]{error1, cod1};
    }

    //InstruccionesFact(in: errorh1,codh1; out: error1,cod1) →
    protected Object[] InstruccionesFact(boolean errorh1, Codigo codh1) throws Exception {
        boolean error1;
        boolean error2 = false;
        Codigo cod1 = new Codigo();
        if (puntoYComa() && !(i_token >= arrayTokens.size())) { //no lambda
            //fin programa
            Object[] resInst = Instrucciones();
            error2 = (Boolean) resInst[0];
            Codigo cod2 = (Codigo) resInst[1];
            cod1 = codh1;
            cod1.appendCod(cod2);
        } else if (i_token >= arrayTokens.size()) {//lambda
            cod1 = codh1;
        } else {
            throw new Exception("FATAL: Instrucción incorrecta: Se esperaba ; o fin de programa" + textoError());
        }

        error1 = error2 || errorh1;
        return new Object[]{error1, cod1};
    }

    //Instrucción(out: error1,cod1) →
    private Object[] Instruccion() throws Exception {
        boolean error1 = false;
        Object[] resLect = InsLectura();
        Object[] resEscr = InsEscritura();
        Object[] resAsig = InsAsignacion();
        boolean error2L = (Boolean) resLect[0];
        boolean error2E = (Boolean) resEscr[0];
        boolean error2A = (Boolean) resAsig[0];
        Codigo cod2 = new Codigo();

        if (!error2L) {//ins Lectura
            cod2 = (Codigo) resLect[1];
        } else if (!error2E) {
            cod2 = (Codigo) resEscr[1];
        } else if (!error2A) {
            cod2 = (Codigo) resAsig[1];
        } else {
            error1 = true;
            errores.add(new ErrorTraductor("Hay errores en la(s) instrucción(es) " + textoError()));
        }
        return new Object[]{error1, cod2};
    }

    protected abstract Object[] InsLectura() throws Exception;

    protected abstract Object[] InsEscritura() throws Exception;

    protected abstract Object[] InsAsignacion() throws Exception;

    //Expresión(out: tipo1,cod1) →
    protected Object[] Expresion() throws Exception {
        Tipos tipo1 = null;
        Codigo cod1 = null;
        Object[] resExprN1 = ExpresionNiv1();
        Tipos tipo2 = (Tipos) resExprN1[0];
        Codigo cod2 = (Codigo) resExprN1[1];
        Object[] resExprFact = ExpresionFact(tipo2, cod2);
        tipo1 = (Tipos) resExprFact[0];
        cod1 = (Codigo) resExprFact[1];
        return new Object[]{tipo1, cod1};
    }

    //ExpresiónFact(in: tipo1h,codh1; out: tipo1,cod1) →
    protected abstract Object[] ExpresionFact(Tipos tipo1h, Codigo codh1) throws Exception;

    //ExpresiónNiv1(out: tipo1, cod1, codJ1) →
    protected Object[] ExpresionNiv1() throws Exception {
        Tipos tipo1 = null;
        Codigo cod1 = null;
        Object[] resExprN2 = ExpresionNiv2();
        Tipos tipo2 = (Tipos) resExprN2[0];
        Codigo cod2 = (Codigo) resExprN2[1];
        Object[] resExprFact = ExpresionNiv1Rec(tipo2, cod2);
        tipo1 = (Tipos) resExprFact[0];
        cod1 = (Codigo) resExprFact[1];
        return new Object[]{tipo1, cod1};
    }

    //ExpresiónNiv1Rec(in: tipoh1, codh1; out: tipo1, cod1)
    protected abstract Object[] ExpresionNiv1Rec(Tipos tipoh1, Codigo codh1) throws Exception;

    //ExpresiónNiv2(out: tipo1, cod1) →
    protected Object[] ExpresionNiv2() throws Exception {
        Tipos tipo1 = null;
        Codigo cod1 = null;
        Object[] resExprNiv3 = ExpresionNiv3();
        Tipos tipoh3 = (Tipos) resExprNiv3[0];
        Codigo codh3 = (Codigo) resExprNiv3[1];
        return new Object[]{tipo1, cod1};
    }

    //ExpresiónNiv2Rec(in: tipoh1, codh1; out: tipo1, cod1)
    protected abstract Object[] ExpresionNiv2Rec(Tipos tipoh1, Codigo codh1) throws Exception;

    //ExpresiónNiv3(out: tipo1, codJ1) →
    protected Object[] ExpresionNiv3() throws Exception {
        Tipos tipo1 = null;
        Codigo cod1 = null;
        Object[] resExprN4 = ExpresionNiv4();
        Tipos tipoh3 = (Tipos) resExprN4[0];
        Codigo codh3 = (Codigo) resExprN4[1];
        Object[] resExprN4Fact = ExpresionNiv3Fact(tipoh3, codh3);
        tipo1 = (Tipos) resExprN4Fact[0];
        cod1 = (Codigo) resExprN4Fact[1];
        return new Object[]{tipo1, cod1};
    }

    //ExpresiónNiv3Fact(in: tipoh1, codh1; out: tipo1, cod1)
    protected abstract Object[] ExpresionNiv3Fact(Tipos tipoh1, Codigo codh1) throws Exception;

    protected Object[] ExpresionNiv4() throws Exception {
        Operaciones op2 = OpNiv4();
        if (op2 != null) {
            return ExpresionNiv4_conOp(op2);
        }
        if (valorAbs()) {
            return ExpresionNiv4_valorAbs();
        }
        if (abrePar()) {
            return ExpresionNiv4_abrePar();
        }
        return ExpresionNiv4_Literal();
    }

    protected abstract Object[] ExpresionNiv4_conOp(Operaciones op2) throws Exception;

    protected abstract Object[] ExpresionNiv4_valorAbs() throws Exception;

    //ExpresiónNiv4(out: tipo1, cod1)
    protected Object[] ExpresionNiv4_abrePar() throws Exception {
        Object[] resExpr = Expresion();
        Tipos tipo1 = (Tipos) resExpr[0];
        Codigo cod1 = (Codigo) resExpr[1];
        if (!cierraPar()) {
            throw new Exception("FATAL: Se esperaba cerrar paréntesis" + textoError());
        }
        return new Object[]{tipo1, cod1};
    }

    protected Object[] ExpresionNiv4_Literal() throws Exception {
        return Literal();
    }

    //Literal(out: tipo1, cod1)
    protected Object[] Literal() throws Exception {
        Token t = sigToken();
        if (t instanceof Identificador) {
            return Literal_Id(t);
        } else if (t instanceof LitNat) {
            return Literal_LitNat(t);
        } else if (t instanceof LitFlo) {
            return Literal_LitFlo(t);
        } else if (t instanceof LitTrue) {
            return Literal_LitTrue();
        } else if (t instanceof LitFalse) {
            return Literal_LitFalse();
        } else if (t instanceof LitCha) {
            return Literal_LitCha(t);
        } else {
            throw new Exception("Error: se esperaba un literal" + textoError());
        }
        //estamos en el nivel más bajo, esta es la última comprobación de lo que algo puede ser
        //por tanto se puede lanzar la excepción porque no hay nada que hacer si esto no es un literal.

    }

    protected abstract Object[] Literal_Id(Token t) throws Exception;

    protected abstract Object[] Literal_LitNat(Token t) throws Exception;

    protected abstract Object[] Literal_LitTrue() throws Exception;

    protected abstract Object[] Literal_LitFalse() throws Exception;

    protected abstract Object[] Literal_LitCha(Token t) throws Exception;

    protected abstract Object[] Literal_LitFlo(Token t) throws Exception;

    protected Operaciones OpNiv0() {
        Token t = sigToken();
        if (t instanceof compilador.lexico.Tokens.Menor) {
            return Operaciones.MENOR;
        }
        if (t instanceof compilador.lexico.Tokens.Mayor) {
            return Operaciones.MAYOR;
        }
        if (t instanceof Menor_ig) {
            return Operaciones.MENORIG;
        }
        if (t instanceof Mayor_ig) {
            return Operaciones.MAYORIG;
        }
        if (t instanceof compilador.lexico.Tokens.Igual) {
            return Operaciones.IGUAL;
        }
        if (t instanceof Distinto) {
            return Operaciones.DISTINTO;
        }
        atrasToken();
        return null;
    }

    protected Operaciones OpNiv1() {
        Token t = sigToken();
        if (t instanceof compilador.lexico.Tokens.Suma) {
            return Operaciones.SUMA;
        }
        if (t instanceof Signo_menos) {
            return Operaciones.RESTA;
        }
        if (t instanceof Or) {
            return Operaciones.OR;
        }
        atrasToken();
        return null;
    }

    protected Operaciones OpNiv2() {
        Token t = sigToken();
        if (t instanceof Multiplicacion) {
            return Operaciones.MULT;
        }
        if (t instanceof Division) {
            return Operaciones.DIV;
        }
        if (t instanceof compilador.lexico.Tokens.Modulo) {
            return Operaciones.MOD;
        }
        if (t instanceof And) {
            return Operaciones.AND;
        }
        atrasToken();
        return null;
    }

    protected Operaciones OpNiv3() {
        Token t = sigToken();
        if (t instanceof compilador.lexico.Tokens.Shl) {
            return Operaciones.SHL;
        }
        if (t instanceof compilador.lexico.Tokens.Shr) {
            return Operaciones.SHR;
        }
        atrasToken();
        return null;
    }

    protected Operaciones OpNiv4() {
        Token t = sigToken();
        if (t instanceof Not) {
            return Operaciones.NOT;
        }
        if (t instanceof Signo_menos) {
            return Operaciones.NEG;
        }
        if (t instanceof Cast_float) {
            return Operaciones.CASTREAL;
        }
        if (t instanceof Cast_int) {
            return Operaciones.CASTENT;
        }
        if (t instanceof Cast_nat) {
            return Operaciones.CASTNAT;
        }
        if (t instanceof Cast_char) {
            return Operaciones.CASTCHAR;
        }
        atrasToken();
        return null;
    }
}
