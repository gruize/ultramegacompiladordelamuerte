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
        cod = new Codigo();
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
        if (!(t instanceof Token_In)) {
            error = true;
            atrasToken(); //tal vez hemos leído un token que no había que leer
        }
        return !error;
    }
    protected boolean out() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Token_Out)) {
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
        if (!(t instanceof Token_Absoluto)) {
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
    protected boolean If() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof If)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean then() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Then)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean Else() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Else)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean While() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof While)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean Do() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Do)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean For() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof For)){
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
    protected Object[] FParametro() throws Exception{
        boolean error1 = false;
        String id1 = "";
        InfoTs props1 = null;
        int tam1 = 0;

        Object[] tipoRes= Tipo();
        boolean error2= (Boolean) tipoRes[0];
        Tipos tipo2 = (Tipos) tipoRes[1];

        String lex = identificador();

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
    protected Object[] Tipo() throws Exception{
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

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Boolean(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:boolean,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Character(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:character,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Float(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:float,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Natural(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:natural,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Integer(){
        boolean error1 =false;
        Tipos tipo1 = null;

        //tipo1 = <t:integer,tam:1>
	//error1 = false

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Array() throws Exception{
        boolean error1 =false;
        Tipos tipo1 = null;
        
        if (abreCorchete()){
            String lex = numero();
            if (cierraCorchete())
                if (of()){
                    Object[] tipoRes = Tipo();
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
        }

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Puntero() throws Exception{
        boolean error1 =false;
        Tipos tipo1 = null;

        Object[] tipoRes=Tipo();
        boolean error2 = (Boolean) tipoRes[0];
        Tipos tipo2 = (Tipos) tipoRes[1];

        /*tipo1 =
	        <
	            t:puntero,
	            tbase:tipo2,
	            tam:1
	        >
	error1 = error2*/
        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Record() throws Exception{
        boolean error1 =false;
        Tipos tipo1 = null;

        if (abreCorchete()){
            Object[] tipoRes=Campos();
            boolean error2 = (Boolean) tipoRes[0];
            Campos campos2 = (Tipos) tipoRes[1];
            int tam2 = (Integer) tipoRes[2];

            if (cierraCorchete()){
                /*Tipo.tipo =
                    <
                        t:array,
                        campos: campos2,
                        tam: tam2
                    >
                error1 = error2*/
            }
        }
        return new Object[]{error1, tipo1};
    }
    protected Object[] Campos(){
        boolean error1 = false;
        Campos campos1 = null;
        int tam1 = 0;
        int desh2 = 0;
        boolean errorh3 = false;
        Campos camposh3 = null;
        int desh3 = 0;

        //desh2=0;

        Object[] campoRes = Campo(desh2);
        boolean error2 = (Boolean) campoRes[0];
        String id2 = (String) campoRes[1];
        Campos campo2 = (Campos) campoRes[2];
        int tam2 = (Integer) camposRes[3];

        //camposh3 = [campo2]
	//errorh3 = error2
	//desh3 = tam2

        Object[] camposRecRes = CamposRec(errorh3, camposh3, desh3);
        boolean error3= (Boolean) camposRecRes[0];
        Campos campos3 = (Campos) camposRecRes[1];
        int tam3 = (Integer) camposRecRes[2];

        //error1 = error3
	//campos1 = campos3
	//tam1 = tam3

        return new Object[]{error1,campos1,tam1};
    }
    protected Object[] CamposRec(boolean errorh1, Campos camposh1, int desh1){
        boolean error1 = false;
        Campos campos1 = null;
        int tam1 = 0;
        int desh2=0;
        boolean errorh3=false;
        Campo camposh3 = null;
        int desh3 = 0;
        
        if (puntoYComa()){
            //desh2 = desh1
            Object[] campoRes = Campo(desh2);
            boolean error2= (Boolean) campoRes[0];
            String id2 = (String) campoRes[1];
            Campos campo2 = (Campos) campoRes[2];
            int tam2 = (Integer) campoRes[3];
            
            //camposh3 = camposh1 ++ campo2
            //errorh3 = errorh1 v error2 v existeCampo(camposh1, id2)
            //desh3 = tam2 + desh1
            
            Object[] camposRecRes = CamposRec(errorh3, camposh3, desh3);
            boolean error3 = (Boolean) camposRecRes[0];
            Campos campos3 = (Campos) camposRecRes[1];
            int tam3 = (Integer) camposRecRes[2];
            
            //error1 = error3
            //campos1 = campos3
            //tam1 = tam3
        }
        else{
            //error1= errorh1;
            //campos1 = camposh1
            //tam1 = desh1
        }
        return new Object[]{error1, campos1, tam1};
    }
    protected Object[] Campo(int desh1) throws Exception{
        boolean error1 = false;
        String id1 = "";
        Campos campo1 = null;
        int tam1 = 0;

        String lex = identificador();
        if (dosPuntos()){
            Object[] tipoRes= Tipo();
            boolean error2= (Boolean) tipoRes[0];
            Tipos tipo2 = (Tipos) tipoRes[1];

            /*campo1 =
                <
                    id:lex,
                    tipo:tipo2,
                    desp:desh1
                >
            tam1   = tipo2.tam
            error1 = error2 v ¬existeRef(ts,tipo2)*/
        }
        return new Object[]{error1, id1, campo1, tam1};
    }


    protected boolean Instrucciones(){
        boolean error1 = false;
        boolean errorh3 = false;

        boolean error2 = Instruccion()

        errorh3 = error2;

        boolean error3 = InstrucionesRec(errorh3);

        error1 = error3;

        return error1;
    }
    protected boolean InstruccionesRec(boolean errorh1){
        boolean error1 =false;
        boolean errorh3 = false;

        if (puntoYComa()){
            boolean error2 = Instruccion();

            errorh3 = error1 || errorh1;

            boolean error3 = InstruccionesRec(errorh3);

            error1 = error3;

            return error1;
        }
        else {
            error1 = error1h;
        }
    }
    protected boolean Instruccion(){
        boolean error1 = false;
        boolean error2Proc = InsProcedimiento();
        boolean error2Lect = InsLectura();
        boolean error2Escr = InsEscritura();
        boolean error2Comp = InsCompuesta();
        boolean error2If= InsIf();
        boolean error2While = InsWhile();
        boolean error2For = InsFor();
        boolean error2New = InsNew();
        boolean error2Dis = InsDis();

        if(! error2Proc){

        }else if (!error2Lect){
            error1 = error2Lect;
        }else if (!error2Escr){
            error1 = error2Escr;
        }else if (!error2Comp){
            error1 = error2Comp;
        }else if (!error2If){
            error1 = error2If;
        }else if (!error2While){
            error1 = error2While;
        }else if (!error2For){
            error1 = error2For;
        }else if (!error2New){
            error1 = error2New;
        }else if (!error2Dis){
            error1 = error2Dis;
        }else {
            error1 = true;
            errores.add(new ErrorTraductor("Hay errores en la(s) Instrucciones(es) " + textoError()));
        }
        return error1;
    }
    protected boolean InsProcedimiento(){
        boolean error1 = false;

        String lex = identificador();

        //params = ts[lex].tipo.parametros
	//cod + = apilar-ret
	//etq += longApilaRet

        boolean error2 = AParametros();

        //error1 = error2 v ¬existeID(lex) v ts[lex].clase != proc
	//cod += ir-a(ts[lex].inicio)
	//etq += 1

        return error1;
    }
    protected boolean AParametros(){
        boolean error1 =false;
        
        if (abrePar()){
            //etq += longInicioPaso
            //cod += inicio-paso

            boolean error2 = LAParametros();
            if (cierraPar()){
                //error1 = error2
                //cod += fin-paso
                //etq += longFinPase
            }
        }else{
            //error1 = |params| >0;
        }
        return error1;
    }
    protected boolean LAParametros(){
        boolean error1 =false;
        int parh2 = 0;
        int nparamh3 = 0;
        boolean errorh3 = false;

        //etq += 1
	//cod += copia
	//parh2 = params[0].modo == var

        Object[] expRes = Expresion(parh2);
        Tipos tipo2 = (Tipos) expRes[0];
        String modo2 = (String) expRes[1];

        /*nparam_h3 = 1
	errorh3 = (tipo2 == <t:error>) v (|params|) < 1 v (params[0].modo = var ٨ modo2 = val) v
			NOT compatibles(params[0].tipo, tipo, ts)
        cod += pasoParametro(modo2,params[0])
	etq += longPaseoParametro*/

        boolean error3 = LAParametrosRec(nparamh3,errorh3);

        //error1 = error3

        return error1;
    }
    protected boolean LAParametrosRec(int nparamh1, boolean errorh1){
        boolean error1 = false;
        int parh2 = 0;
        int nparamh3 = 0;
        boolean errorh3 = false;

        if (coma()){
            //etq += longDireccionParFormal + 1
            //cod += copia
            //cod += direccionParFormal(fparamsh1[nparamh1])
            //parh2 = params[nparamh1].modo == var

            Object[] expRes = Expresion(parh2);
            Tipos tipo2 = (Tipos) expRes[0];
            String modo2 = (String) expRes[1];

            /*nparamh3 = nparamh1 + 1
            errorh3 = errorh1 v tipo2==<t:error> v |params| < nparamh1 + 1 v
				(params[nparamh1].modo ==var  ٨ modo2 == val) v
				NOT compatibles(params[nparamh1].tipo, tipo2, ts)
            etq += longPasoParametro
            cod += pasoParametro(modo2,params[nparamsh1])*/

            boolean error3 = LAParametrosRec(nparamh3,errorh3);

            //error1 = error3

        }else{
            //error1 = errorh1;
        }
        return error1;

    }
    protected boolean InsLectura(){
        boolean error1 = false;
        
        if (in())
            if (abrePar()){
                String lex = identificador();
                if (cierraPar()){
                    //error1 = NOT existeID(ts,lex)
                    //cod += in ts[lex].dir
                    //etq += 1
                }
            }
        return error1;
    }
    protected boolean InsEscritura(){
        boolean error1 = false;
        boolean parh2 = false;
        if (out())
            if (abrePar()){
                //parh2= false

                Object[] expRes= Expresion(parh2);
                Tipos tipo2 = (Tipos) expRes[0];
                String modo2 = (String) expRes[1];

                //error1 = (tipo2 = <t:error>)
                //etq += 1
                //cod += out

                if (cierraPar()){

                }

            }
        return error1;
    }
    protected boolean InsAsignacion(){
        boolean error1 = false;
        boolean parh3 = false;

        boolean error2 = Mem();

        if (dosPuntosIgual()){
            //parh3 = false;
            Object[] expRes= Expresion(parh3);
            Tipos tipo3 = (Tipos) expRes[0];
            String modo3 = (String) expRes[1];

            /*etq += 1
            error1 = ¬ esCompatible(tipo2, tipo3,ts)
            si esCompatibleConTipoBasico(tipo2, ts)
                    cod += desapila-ind
            si no
                    cod += mueve(tipo2.tam)*/

        }
        return error1;
    }
    protected boolean InsCompuesta(){
        boolean error1 = false;

        if (abreCorchete()){
            boolean error2 = Instrucciones();
            if (cierraCorchete()){
                //error1 = error2;
            }
        }
        return false;
    }
    protected boolean InsIf(){
        boolean error1 = false;
        boolean parh2 = false;
        if (If()){
            parh2 = false;

            Object[] expRes = Expresion(parh2);
            Tipos tipo2 = (Tipos) expRes[0];
            String modo2 = (String) expRes[1];

            if (then()){
                /*etq += 1
                PARCHE: no puedo hacer el ir-f!!
                cod += noop
                aux = etq*/

                boolean error3 = Instruccion();

                /*insertar(cod, ir-f(etq+1), aux) //reemplaza el noop con el ir-f. en la posicion del código aux
		etq += 1
		aux=etq
		cod + = noop*/

                boolean error4 = PElse();

                /*insertar(cod, ir-a(etq),aux) //mismo problema
                error1 = tipo2 != <t:bool> v error3 v error4
                InsIf.error = Expresion.tipo != <t:bool> v Instrucción.error v Pelse.error*/
            }
        }
        return error1;
    }
    protected boolean PElse(){
        boolean error1 = false;
        if (Else()){
            boolean error2 = Instruccion();
            //error1 = error2;
        }
        else{
            //error1 =false;
        }
        return error1;
    }
    protected boolean InsWhile(){
        boolean error1 = false;
        boolean parh2 = false;
        if( While()){
            //etq_while = etq;
            
            Object[] expRes = Expresion(parh2);
            Tipos tipo2 = (Tipos) expRes[0];
            String modo2 = (String) expRes[1];
            
            if (Do()){
                //etq += 1
                //aux = etq
                //cod += noop
                
                boolean error3 = Instruccion();
                
                //inserta(cod, ir-f(etq + 1), aux) (parche)
                //etq += 1
                //cod+= ir-a(etq_while)
                //error1 = tipo2 != <t:bool> v error3
            }
        }
    }
    
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
        if (t instanceof compilador.lexico.Tokens.Token_Menor) {
            return Operaciones.MENOR;
        }
        if (t instanceof compilador.lexico.Tokens.Token_Mayor) {
            return Operaciones.MAYOR;
        }
        if (t instanceof Token_Menor_ig) {
            return Operaciones.MENORIG;
        }
        if (t instanceof Token_Mayor_ig) {
            return Operaciones.MAYORIG;
        }
        if (t instanceof compilador.lexico.Tokens.Token_Igual) {
            return Operaciones.IGUAL;
        }
        if (t instanceof Token_Distinto) {
            return Operaciones.DISTINTO;
        }
        atrasToken();
        return null;
    }

    protected Operaciones OpNiv1() {
        Token t = sigToken();
        if (t instanceof compilador.lexico.Tokens.Token_Suma) {
            return Operaciones.SUMA;
        }
        if (t instanceof Signo_menos) {
            return Operaciones.RESTA;
        }
        if (t instanceof Token_Or) {
            return Operaciones.OR;
        }
        atrasToken();
        return null;
    }

    protected Operaciones OpNiv2() {
        Token t = sigToken();
        if (t instanceof Token_Multiplicacion) {
            return Operaciones.MULT;
        }
        if (t instanceof Token_Division) {
            return Operaciones.DIV;
        }
        if (t instanceof compilador.lexico.Tokens.Token_Modulo) {
            return Operaciones.MOD;
        }
        if (t instanceof Token_And) {
            return Operaciones.AND;
        }
        atrasToken();
        return null;
    }

    protected Operaciones OpNiv3() {
        Token t = sigToken();
        if (t instanceof compilador.lexico.Tokens.Token_Shl) {
            return Operaciones.SHL;
        }
        if (t instanceof compilador.lexico.Tokens.Token_Shr) {
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
