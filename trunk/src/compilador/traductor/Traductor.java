package compilador.traductor;

import java.util.ArrayList;
import java.util.Iterator;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.LectorExc;

import pila.interprete.instrucciones.*;
import pila.interprete.datos.*;
import compilador.lexico.Tokens.*;
import compilador.tablaSimbolos.*;


/**
 * Esta clase contiene las funciones de traducción
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 *
 */
public class Traductor {
    //*****ATRIBUTOS****
    public static final int longInicio = 4;
    public static final int longApilaRet = 5;
    public static final int longPrologo = 13;
    public static final int longEpilogo = 13;
    public static final int longInicioPaso = 3;
    public static final int longFinPaso = 1;
    public static final int longDireccionParFormal = 2;
    public static final int longPasoParametro = 1;

    protected ArrayList<Token> arrayTokens;
    protected Codigo cod;
    protected GestorTs ts;
    protected int etq;
    protected int tam_datos;
    protected int n;
    protected int anidamiento;
    protected ArrayList<Parametro> parametros;
    protected int i_token;
    protected ArrayList<String> pend;
    protected ArrayList<ErrorTraductor> errores;
    protected int dir_n[];

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
        parametros = new ArrayList<Parametro>();
        ts = new GestorTs();
    }

    //****FUNCIONES PRINCIPALES
    public void traducir(String nombreClase) throws Exception {
        cod = new Codigo();
        try {
            Programa();
        } catch (Exception e) {
            System.out.println("Traducción no terminada: Error Fatal:");
            System.out.println(e.getMessage());
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
    public Codigo getCod(){
        return cod;
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
    protected Codigo inicio(int numNiveles, int tamDatos) throws LectorExc, DatoExc{
        Codigo res = new Codigo();
        res.appendIns(new Apilar(new Nat(numNiveles +2)));
        res.appendIns(new DesapilarDir(new Nat(1)));
        res.appendIns(new Apilar(new Nat(1+tamDatos+numNiveles)));
        res.appendIns(new DesapilarDir(new Nat(0)));
        return res;
    }
    protected Codigo apilaRet(int ret) throws DatoExc, LectorExc{
        Codigo res = new Codigo();
        res.appendIns(new ApilarDir(new Nat(0)));
        res.appendIns(new Apilar(new Nat(1)));
        res.appendIns(new Suma());
        res.appendIns(new Apilar(new Nat(ret)));
        res.appendIns(new DesapilaInd());
        return res;
    }
    protected Codigo prologo(int nivel, int tamLocales) throws DatoExc, LectorExc{
        Codigo res = new Codigo();
        res.appendIns(new ApilarDir(new Nat(0)));
        res.appendIns(new Apilar(new Nat(2)));
        res.appendIns(new Suma());
        res.appendIns(new ApilarDir(new Nat(1+nivel)));
        res.appendIns(new DesapilaInd());
        res.appendIns(new ApilarDir(new Nat(0)));
        res.appendIns(new Apilar(new Nat(3)));
        res.appendIns(new Suma());
        res.appendIns(new DesapilarDir(new Nat(1+nivel)));
        res.appendIns(new ApilarDir(new Nat(0)));
        res.appendIns(new Apilar(new Nat(tamLocales+2)));
        res.appendIns(new Suma());
        res.appendIns(new DesapilarDir(new Nat(0)));
        return res;
    }
    protected Codigo epilogo(int nivel) throws LectorExc, DatoExc{
        Codigo res = new Codigo();
        res.appendIns(new ApilarDir(new Nat(1+nivel)));
        res.appendIns(new Apilar(new Nat(2)));
        res.appendIns(new Resta());
        res.appendIns(new ApilarInd());
        res.appendIns(new ApilarDir(new Nat(1+nivel)));
        res.appendIns(new Apilar(new Nat(3)));
        res.appendIns(new Resta());
        res.appendIns(new Copia());
        res.appendIns(new DesapilarDir(new Nat(0)));
        res.appendIns(new Apilar(new Nat(2)));
        res.appendIns(new Suma());
        res.appendIns(new ApilarInd());
        res.appendIns(new DesapilarDir(new Nat(1+nivel)));
        return res;
    }
    protected Codigo accesoVar(InfoTs props) throws LectorExc, DatoExc{
        Codigo res = new Codigo();
        res.appendIns(new ApilarDir(new Nat(1+props.getNivel())));
        res.appendIns(new Apilar(new Nat(props.getDir())));
        res.appendIns(new Suma());
        if (props.getClase().equals("pvar"))
            cod.appendIns(new ApilarInd());
        return res;
    }
    protected int longAccesoVar(InfoTs props){
        int resp;
        if (props.getClase().equals("pvar"))
            resp=4;
        else resp=3;
        return resp;
    }
    protected Codigo inicioPaso() throws DatoExc, LectorExc{
        Codigo res = new Codigo();
        res.appendIns(new ApilarDir(new Nat(0)));
        res.appendIns(new Apilar(new Nat(3)));
        res.appendIns(new Suma());
        return res;
    }
    protected Codigo finPaso() throws LectorExc{
        Codigo res = new Codigo();
        res.appendIns(new Desapilar());
        return res;
    }
    protected Codigo direccionParFormal(Parametro pFormal) throws LectorExc, DatoExc{
        Codigo res = new Codigo();
        res.appendIns(new Apilar(new Nat(pFormal.getDir())));
        res.appendIns(new Suma());
        return res;
    }
    protected Codigo pasoParametro(String modoReal, Parametro pFormal) throws LectorExc, DatoExc{
        Codigo res = new Codigo();
        if (pFormal.getModo().equals("variable") && modoReal.equals("valor"))
            res.appendIns(new Mueve(new Nat(pFormal.getTipo().getTam())));
        else res.appendIns(new DesapilaInd());
        return res;
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
        if (!(t instanceof Token_Igual)){
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
    protected boolean abreLlave(){
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Llave_a)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean cierraLlave(){
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Llave_c)){
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
    protected boolean to() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof To)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean New() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Token_New)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean dispose() {
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Token_Dispose)){
            atrasToken();
            error = true;
        }
        return !error;
    }
    protected boolean punto(){
        Token t = sigToken();
        boolean error = false;
        if (!(t instanceof Punto)){
            atrasToken();
            error = true;
        }
        return !error;
    }



    //-----------------------------------------
    //-------implementación--------------------

    protected boolean Programa() throws Exception {
        boolean error1 = false;

        etq = longInicio + 1;
        tam_datos = 0;
        dir_n=new int[50]; //max nivel anid
        for (int i=0;i<dir_n.length;i++) dir_n[i]=0;
        n = 0; //nivel actual
        anidamiento = 0;
        ts = new GestorTs();
        cod = new Codigo();

        boolean error2 = Declaraciones();
        
        int tam_datos2=0;
        for (int i=0;i<dir_n.length;i++) tam_datos2+=dir_n[i];
        assert(tam_datos==tam_datos2);
        //añadimos por delante del código de declaraciones
        //la parte del inicio.
        Codigo ini=inicio(anidamiento,tam_datos);
        ini.appendIns(new IrA(new Nat(etq)));
        ini.appendCod(cod);
        cod=ini;

        if (!ampersand()) {
            throw new Exception("FATAL: & no encontrado" + textoError());
        }


        boolean error3 = Instrucciones();

        cod.appendIns(new Parar());
        error1 = error2 || error3;

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

        tam_datos+=tam2;
        dir_n[n]+=tam2;
        errorh3= error2 || (GestorTs.existe(ts, id2) && (GestorTs.getProps(ts,id2).getNivel() == n));
        GestorTs.inserta(ts,id2,props2);
        if (props2.getClase().equals("tipo"))
            pend.remove(id2);

        boolean error3 = DeclaracionesRec(errorh3);

        return error3;
    }
    protected boolean DeclaracionesRec(boolean errorh1) throws Exception {
        boolean error1 = false;
        boolean errorh3 = false;
        if (puntoYComa()) {//no lambda
        	//parche para poder acabar las declaraciones con ;
        	//no está en la gramática pero es útil
        	if (ampersand()){
        		atrasToken();
        		return errorh1;
        	}

            Object[] decRes = Declaracion();
            boolean error2 = (Boolean) decRes[0];
            int tam2 = (Integer) decRes[1];
            String id2 = (String) decRes[2];
            InfoTs props2 = (InfoTs) decRes[3];

            tam_datos+=tam2;
            dir_n[n]+=tam2;
            errorh3= errorh1 || error2 || (GestorTs.existe(ts, id2) && (GestorTs.getProps(ts,id2).getNivel() == n));
            if (!error2) GestorTs.inserta(ts,id2,props2);
            if (props2.getClase().equals("tipo"))
                pend.remove(id2);

            boolean error3 = DeclaracionesRec(errorh3);

            error1 = error3;
        }
        else {
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
        String id2;

        Token t = sigToken();
        atrasToken();

        if (t instanceof Procedure){
            Object[] decProcRes = DeclaracionProcedimiento();
            boolean error2P = (Boolean) decProcRes[0];
            id2 = (String) decProcRes[1];
            InfoTs props2P = (InfoTs) decProcRes[2];

            error1 = error2P;
            tam1= 0;
            id1=id2;
            props1 = props2P;
        }
        else if (t instanceof Tipo){
            Object[] decTipoRes = DeclaracionTipo();
            boolean error2T = (Boolean) decTipoRes[0];
            id2 = (String) decTipoRes[1];
            InfoTs props2T = (InfoTs) decTipoRes[2];

            tam1 = 0;
            error1 = error2T;
            id1=id2;
            props1 = props2T;
        }
        else if (t instanceof Identificador || t instanceof Token_Integer || t instanceof Token_Float
                || t instanceof Token_Boolean || t instanceof Token_Character || t instanceof Token_Natural){
            Object[] decVarRes = DeclaracionVariable();
            boolean error2V = (Boolean) decVarRes[0];
            id2 = (String) decVarRes[1];
            InfoTs props2V = (InfoTs) decVarRes[2];

            error1 = error2V;
            id1= id2;
            tam1 = props2V.getTipo().getTam();
            props2V.setDir(dir_n[n]);
            props1 = props2V;
        }
        else {
            error1 = true;
            errores.add(new ErrorTraductor("Hay errores en la(s) declaraciones(es) " + textoError()));
        }
        
        return new Object[]{error1, tam1, id1, props1};

    }
    protected Object[] DeclaracionTipo() throws Exception{
        boolean error1 = false;
        String id1 = "";
        InfoTs props1 = null;

        if( tipo()){
            String lex= identificador();
            if(! igual()){
                throw new Exception("FATAL: Se esperaba simbolo ="
                    + textoError());
            }
            Object[] tipoRes = Tipo();
            boolean error2 = (Boolean) tipoRes[0];
            TipoTs tipo2 = (TipoTs) tipoRes[1];

            id1 = lex;
            props1 = new InfoTs("tipo",tipo2,n);
            error1 = error2  || (GestorTs.existe(ts,lex) && (GestorTs.refErronea(ts,tipo2)));

        }
        return new Object[]{error1, id1, props1};
    }
    protected Object[] DeclaracionVariable() throws Exception{
        boolean error1 = false;
        String id1 = "";
        InfoTs props1 = null;

        Object[] tipoRes = Tipo();
        boolean error2 = (Boolean) tipoRes[0];
        TipoTs tipo2 = (TipoTs) tipoRes[1];

        String lex= identificador();
        
        id1 = lex;
        props1 = new InfoTs("var",tipo2,n);
        error1 = error2  || (GestorTs.existe(ts,lex) && (GestorTs.refErronea(ts,tipo2)));

        return new Object[]{error1, id1, props1};
    }
    protected Object[] DeclaracionProcedimiento() throws Exception{
        boolean error1 = false;
        String id1 = "";
        InfoTs props1 = null;

        if (procedure()){
            String lex= identificador();
            ts.abrirAmbito();
            n += 1;
            anidamiento=Math.max(anidamiento, n);
            boolean error2 = FParametros();
            ArrayList<Parametro> parametros_tabla=(ArrayList<Parametro>)parametros.clone();
            GestorTs.inserta(ts, lex ,new InfoTs("proc", new TipoTs("proc", parametros_tabla), n));
            parametros.clear();
            
            Object[] bloqueRes = Bloque();
            boolean error3= (Boolean) bloqueRes[0];
            int inicio3 = (Integer) bloqueRes[1];

            error1 = error2 || error3 || (GestorTs.existe(ts, lex) && (GestorTs.getProps(ts,lex).getNivel() == n+1));
            id1 = lex;
            props1 = new InfoTs("proc", new TipoTs("proc", parametros_tabla), n, inicio3);
            n -= 1;
            ts.cerrarAmbitoActual();
        }

        return new Object[]{error1, id1, props1};

    }
    protected Object[] Bloque() throws Exception{
        boolean error1=false;
        boolean error2=false;
        int inicio1 = 0;
        int tam_local=dir_n[n];

        if (!abreLlave()){
            throw new Exception("FATAL: Se esperaba abre llave"
                    + textoError());
        }
        if (!ampersand()) //hay declaraciones
        	error2=Declaraciones();
        else atrasToken();
        inicio1 = etq;
        etq += longPrologo;
        cod.appendCod(prologo(n,dir_n[n]));//ponia dir_n[n]-tam_local
        
        if (! ampersand()){
        	throw new Exception("FATAL: Se esperaba ampersand"
                    + textoError());
            }

        boolean error3 = Instrucciones();

        if (!cierraLlave()){
            throw new Exception("FATAL: Se esperaba cierra llave"
                    + textoError());
        }

        error1 = error2 || error3;
        etq += longEpilogo + 1;
        cod.appendCod(epilogo(n));
        cod.appendIns(new IrInd());

        return new Object[]{error1, inicio1};
    }
    protected boolean FParametros() throws Exception{
        boolean error1 = false;
        if (abrePar()){
            boolean error2 = LFParametros();
            
            error1=error2;
            if (! cierraPar()){
                throw new Exception("FATAL: Se esperaba cerrar paréntesis"
                    + textoError());
            }
        }

        return error1;
    }
    protected boolean LFParametros() throws Exception{
    
        boolean errorh3 = false;

        Object[] FParamRes = FParametro();
        boolean error2 = (Boolean) FParamRes[0];
        String id2 = (String) FParamRes[1];
        InfoTs props2 = (InfoTs) FParamRes[2];
        int tam2 = (Integer) FParamRes[3];

        errorh3 = error2 ||(GestorTs.existe(ts,id2) && (GestorTs.getProps(ts, id2).getNivel() == n));
        props2.setDir(0);
        GestorTs.inserta(ts, id2, props2);
        dir_n[n]+=tam2;
        tam_datos+=tam2;

        return LFParametrosRec(errorh3);

    }
    protected boolean LFParametrosRec(boolean errorh1) throws Exception{
        boolean error1= false;
        boolean errorh3 = false;

        if (coma()){
            Object[] FParamRes = FParametro();
            boolean error2 = (Boolean) FParamRes[0];
            String id2 = (String) FParamRes[1];
            InfoTs props2 = (InfoTs) FParamRes[2];
            int tam2 = (Integer) FParamRes[3];

            dir_n[n]+=tam2;
            tam_datos+=tam2;
            errorh3 = errorh1 || error2 || (GestorTs.existe(ts,id2)  && GestorTs.getProps(ts, id2).getNivel() == n);
            GestorTs.inserta(ts, id2, props2);

            boolean error3 = LFParametrosRec(errorh3);

            error1 = error3;
        }
        else{
            error1 = errorh1;
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
        TipoTs tipo2 = (TipoTs) tipoRes[1];

        String lex = identificador();

        if (var()){
            tam1 = 1;
            parametros.add(new Parametro("variable", tipo2, dir_n[n]));
            id1 = lex;
            props1 = new InfoTs("pvar", tipo2, n);
            error1 = error2;
        }
        else{
            tam1 = tipo2.getTam();
            parametros.add ( new Parametro("valor", tipo2, dir_n[n]));
            id1 = lex;
            props1 = new InfoTs("var", tipo2, n);
            error1 = error2;
        }

        return new Object[]{error1,id1,props1,tam1};
    }
    protected Object[] Tipo() throws Exception{
        Token t= sigToken();
        atrasToken();
        if (t instanceof Identificador){
            sigToken();
            return Tipo_id(t);
        }
        else if (t instanceof Token_Boolean){
            sigToken();
            return Tipo_Boolean();
        }
        else if (t instanceof Token_Character){
            sigToken();
            return Tipo_Character();
        }
        else if (t instanceof Token_Float){
            sigToken();
            return Tipo_Float();
        }
        else if (t instanceof Token_Natural){
            sigToken();
            return Tipo_Natural();
        }
        else if (t instanceof Token_Integer){
            sigToken();
            return Tipo_Integer();
        }
        else if (array()){
            return Tipo_Array();
        }
        else if (circunflejo()){
            return Tipo_Puntero();
        }
        else if (record()){
            return Tipo_Record();
        }
        else
            throw new Exception("Error: se esperaba un literal" + textoError());
    }
    protected Object[] Tipo_id(Token t){
        boolean error1 =false;
        TipoTs tipo1 = null;
        String lex= t.getLex();

        tipo1 = new TipoTs("ref", lex, GestorTs.getProps(ts, lex).getTipo().getTam());
	if (GestorTs.existe(ts,lex))
            error1 = ! GestorTs.getProps(ts, lex).getClase().equals("tipo");
        else
            error1= false;

	if ( ! GestorTs.existe(ts,lex))
            pend.add(lex);

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Boolean(){
        boolean error1 =false;
        TipoTs tipo1 = null;

        tipo1 = new TipoTs("boolean",1);
	error1 = false;

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Character(){
        boolean error1 =false;
        TipoTs tipo1 = null;

        tipo1 = new TipoTs("character",1);
	error1 = false;

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Float(){
        boolean error1 =false;
        TipoTs tipo1 = null;

        tipo1 = new TipoTs("float",1);
        error1 = false;

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Natural(){
        boolean error1 =false;
        TipoTs tipo1 = null;

        tipo1 = new TipoTs("natural",1);
	error1 = false;

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Integer(){
        boolean error1 =false;
        TipoTs tipo1 = null;

        tipo1 = new TipoTs("integer",1);
	error1 = false;

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Array() throws Exception{
        boolean error1 =false;
        TipoTs tipo1 = null;

            if (! abreCorchete()){
                throw new Exception("FATAL: Se esperaba abrir abrir corchete"
                        + textoError());
            }

            String lex = numero();

            if (!cierraCorchete()){
                throw new Exception("FATAL: Se esperaba cerrar corchete"
                        + textoError());
            }
            if (!of()){
                throw new Exception("FATAL: Se esperaba palabra reservada of"
                        + textoError());
            }

            Object[] tipoRes = Tipo();
            boolean error2 = (Boolean) tipoRes[0];
            TipoTs tipo2 = (TipoTs) tipoRes[1];

            tipo1 = new TipoTs("array",Integer.parseInt(lex), tipo2, Integer.parseInt(lex)*tipo2.getTam());
            error1 = tipo2.getT().equals("error") && GestorTs.refErronea(ts ,tipo2);

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Puntero() throws Exception{
        boolean error1 =false;
        TipoTs tipo1 = null;

            Object[] tipoRes=Tipo();
            boolean error2 = (Boolean) tipoRes[0];
            TipoTs tipo2 = (TipoTs) tipoRes[1];

            tipo1 = new TipoTs("puntero",tipo2,1);
            error1 = error2;

        return new Object[]{error1, tipo1};
    }
    protected Object[] Tipo_Record() throws Exception{
        boolean error1 =false;
        TipoTs tipo1 = null;

            if (! abreLlave()){
                throw new Exception("FATAL: Se esperaba abrir corchete"
                        + textoError());
            }
            Object[] tipoRes=Campos();
            boolean error2 = (Boolean) tipoRes[0];
            ArrayList<Campo> campos2 = (ArrayList<Campo>) tipoRes[1];
            int tam2 = (Integer) tipoRes[2];

            if (!cierraLlave()){
                throw new Exception("FATAL: Se esperaba cerrar corchete"
                        + textoError());
            }

            tipo1 = new TipoTs("record", campos2,tam2);
            error1 = error2;

        return new Object[]{error1, tipo1};
    }
    protected Object[] Campos() throws Exception{
        boolean error1 = false;
        ArrayList<Campo> campos1 = null;
        int tam1 = 0;
        int desh2 = 0;
        boolean errorh3 = false;
        ArrayList<Campo> camposh3 = null;
        int desh3 = 0;

        desh2=0;

        Object[] campoRes = Campo(desh2);
        boolean error2 = (Boolean) campoRes[0];
        String id2 = (String) campoRes[1];
        Campo campo2 = (Campo) campoRes[2];
        int tam2 = (Integer) campoRes[3];

        camposh3 = new ArrayList<Campo>();
        camposh3.add(campo2);
	errorh3 = error2;
	desh3 = tam2;

        Object[] camposRecRes = CamposRec(errorh3, camposh3, desh3);
        boolean error3= (Boolean) camposRecRes[0];
        ArrayList<Campo> campos3 = (ArrayList<Campo>) camposRecRes[1];
        int tam3 = (Integer) camposRecRes[2];

        error1 = error3;
	campos1 = campos3;
	tam1 = tam3;

        return new Object[]{error1,campos1,tam1};
    }
    protected Object[] CamposRec(boolean errorh1, ArrayList<Campo> camposh1, int desh1) throws Exception{
        boolean error1 = false;
        ArrayList<Campo> campos1 = null;
        int tam1 = 0;
        int desh2=0;
        boolean errorh3=false;
        ArrayList<Campo> camposh3 = null;
        int desh3 = 0;
        
        if (puntoYComa()){
            
            desh2 = desh1;

            Object[] campoRes = Campo(desh2);
            boolean error2= (Boolean) campoRes[0];
            String id2 = (String) campoRes[1];
            Campo campo2 = (Campo) campoRes[2];
            int tam2 = (Integer) campoRes[3];
            
            camposh1.add(campo2);
            camposh3 = camposh1;
            errorh3 = errorh1 || error2 || Campo.existeCampo(camposh1, id2);
            desh3 = tam2 + desh1;
            
            Object[] camposRecRes = CamposRec(errorh3, camposh3, desh3);
            boolean error3 = (Boolean) camposRecRes[0];
            ArrayList<Campo> campos3 = (ArrayList<Campo>) camposRecRes[1];
            int tam3 = (Integer) camposRecRes[2];
            
            error1 = error3;
            campos1 = campos3;
            tam1 = tam3;
        }
        else{
            error1= errorh1;
            campos1 = camposh1;
            tam1 = desh1;
        }
        return new Object[]{error1, campos1, tam1};
    }
    protected Object[] Campo(int desh1) throws Exception{
        boolean error1 = false;
        String id1 = "";
        Campo campo1 = null;
        int tam1 = 0;

        Object[] tipoRes= Tipo();
        boolean error2= (Boolean) tipoRes[0];
        TipoTs tipo2 = (TipoTs) tipoRes[1];

        String lex = identificador();

        campo1 = new Campo(lex,tipo2,desh1);
        tam1   = tipo2.getTam();
        error1 = error2 || GestorTs.refErronea(ts,tipo2);

        return new Object[]{error1, id1, campo1, tam1};
    }
    protected boolean Instrucciones() throws DatoExc, LectorExc, Exception{
        boolean error1 = false;
        boolean errorh3 = false;

        boolean error2 = Instruccion();

        errorh3 = error2;

        boolean error3 = InstruccionesRec(errorh3);

        error1 = error3;

        return error1;
    }
    protected boolean InstruccionesRec(boolean errorh1) throws DatoExc, LectorExc, Exception{
        boolean error1 =false;
        boolean errorh3 = false;

        if (puntoYComa()){
            boolean error2 = Instruccion();

            errorh3 = error2 || errorh1;

            boolean error3 = InstruccionesRec(errorh3);

            error1 = error3;

            return error1;
        }
        else { //lamda
            error1 = errorh1;
        }
        return error1;
    }
    protected boolean Instruccion() throws DatoExc, LectorExc, Exception{
        boolean error1 = false;
        Token t= sigToken();
        if (t instanceof Identificador){
            String id=t.getLex();
            atrasToken();
            if (GestorTs.existe(ts, id)){
            	if (GestorTs.getProps(ts, id).getTipo().getT().equals("proc"))
            		error1=InsProcedimiento();
            	else
            		error1=InsAsignacion();
            }
            else error1=true;
        }
        else if (t instanceof Token_In){
            atrasToken();
            error1 = InsLectura();
        }
        else if (t instanceof Token_Out){
            atrasToken();
            error1 = InsEscritura();
        }
        else if (t instanceof Llave_a){
            atrasToken();
            error1 = InsCompuesta();
        }
        else if (t instanceof If){
            atrasToken();
            error1 = InsIf();
        }
        else if (t instanceof While){
            atrasToken();
            error1 = InsWhile();
        }
        else if (t instanceof For){
            atrasToken();
            error1 = InsFor();
        }
        else if (t instanceof Token_New){
            atrasToken();
            error1 = InsNew();
        }
        else if (t instanceof Token_Dispose){
            atrasToken();
            error1 = InsDis();
        }
        else {
            error1 = true;
            errores.add(new ErrorTraductor("Hay errores en la(s) Instrucciones(es) " + textoError()));
        }
        return error1;
    }
    protected boolean InsProcedimiento() throws DatoExc, LectorExc, Exception{
        boolean error1 = false;

        String lex = identificador();

        parametros = GestorTs.getProps(ts,lex).getTipo().getParametros();
        etq += longApilaRet;
        cod.appendCod(apilaRet(0)); //se parchea más adelante
        int aux = etq-2;

        boolean error2 = AParametros();
        error1 = error2 || !GestorTs.existe(ts, lex) || !GestorTs.getProps(ts, lex).getClase().equals("proc");
        cod.appendIns(new IrA(new Nat(GestorTs.getProps(ts,lex).getDir())));
        etq += 1;
        cod.insertaCod(new Apilar(new Nat(etq)), aux);

        return error1;
    }
    protected boolean AParametros() throws DatoExc, LectorExc, Exception{
        boolean error1 =false;
        
        if (abrePar()){
            etq += longInicioPaso;
            cod.appendCod(inicioPaso());
            
            boolean error2 = LAParametros();
            if (!cierraPar()){
                throw new Exception("FATAL: Se esperaba cierra parentesis"
                    + textoError());
            }
            error1 = error2;
            cod.appendCod(finPaso());
            etq += longFinPaso;
        }else{
            error1 = parametros.size()>0;
        }
        return error1;
    }
    protected boolean LAParametros() throws LectorExc, DatoExc, Exception{
        boolean error1 =false;
        boolean parh2 = false;
        int nparamh3 = 0;
        boolean errorh3 = false;


        if (parametros.size()==0)
        	throw new Exception("Se esperaba algun parametro " + textoError());


        etq += longDireccionParFormal + 1;
        cod.appendIns(new Copia());
        cod.appendCod(direccionParFormal(parametros.get(0)));
        //etq += 1;
        //cod.appendIns(new Copia());
        
        parh2 = parametros.get(0).getModo().equals("variable");
        Object[] expRes = Expresion(parh2);
        TipoTs tipo2 = (TipoTs) expRes[0];
        String modo2 = (String) expRes[1];
        
        etq+=longPasoParametro;
        cod.appendCod(pasoParametro(modo2, parametros.get(0)));
        

        nparamh3 = 1;
        errorh3 = (tipo2.getT().equals("error")) ||
                    (parametros.size() < 1) ||
                    (parametros.get(0).getModo().equals("variable") && modo2.equals("valor")) ||
                    ! GestorTs.compatibles(parametros.get(0).getTipo(),tipo2,ts);
       
        

        return LAParametrosRec(nparamh3,errorh3);

    }
    protected boolean LAParametrosRec(int nparamh1, boolean errorh1) throws LectorExc, DatoExc, Exception{
        boolean error1 = false;
        boolean parh2 = false;
        int nparamh3=nparamh1+1;
        boolean errorh3 = false;

        if (coma()){
            etq += longDireccionParFormal + 1;
            cod.appendIns(new Copia());
            cod.appendCod(direccionParFormal(parametros.get(nparamh1)));
            
            parh2 = parametros.get(nparamh1).getModo().equals("variable");
            Object[] expRes = Expresion(parh2);
            TipoTs tipo2 = (TipoTs) expRes[0];
            String modo2 = (String) expRes[1];
            

            etq += longPasoParametro;
            cod.appendCod(pasoParametro(modo2,parametros.get(nparamh1)));
            
            errorh3 = errorh1 ||
            	tipo2.getT().equals("error") ||
            	parametros.size() < nparamh1 +1 ||
            	parametros.get(nparamh1).getModo().equals("variable") ||
            	! GestorTs.compatibles(parametros.get(nparamh1).getTipo(),tipo2, ts);

            return LAParametrosRec(nparamh3,errorh3);

            

        }else
            return errorh1;
        

    }
    protected boolean InsLectura() throws LectorExc, DatoExc, Exception{
        boolean error1 = false;
        
        if (in())
            if (!abrePar()){
                throw new Exception("FATAL: Se esperaba abre parentesis"
                    + textoError());
            }

            String lex = identificador();

            if (!cierraPar()){
                throw new Exception("FATAL: Se esperaba cierra parentesis"
                    + textoError());
            }

            error1 = !GestorTs.existe(ts,lex);
            if (error1) return error1;

            //int d = GestorTs.getProps(ts, lex).getDir();
            //Nat nd = new Nat(d);
            String t = GestorTs.getProps(ts, lex).getTipo().getT();
            cod.appendCod(accesoVar(GestorTs.getProps(ts, lex)));
            etq+=longAccesoVar(GestorTs.getProps(ts, lex));
            if (t.equals("boolean"))
                cod.appendIns(new EntradaBool());
            else if (t.equals("integer"))
                cod.appendIns(new EntradaInt());
            else if (t.equals("character"))
                cod.appendIns(new EntradaChar());
            else if (t.equals("natural"))
                cod.appendIns(new EntradaNat());
            else if (t.equals("float"))
                cod.appendIns(new EntradaFloat());
            cod.appendIns(new DesapilaInd());
            etq+=2;
        return error1;
    }
    protected boolean InsEscritura() throws Exception{
        boolean error1 = false;
        boolean parh2 = false;
        if (out())
            if (!abrePar()){
                throw new Exception("FATAL: Se esperaba abre parentesis"
                    + textoError());
            }
            parh2= false;

            Object[] expRes= Expresion(parh2);
            TipoTs tipo2 = (TipoTs) expRes[0];
            String modo2 = (String) expRes[1];

            error1 = (tipo2.getT().equals("error"));
            etq += 1;
            cod.appendIns(new Salida());

            if ( !cierraPar()){
                throw new Exception("FATAL: Se esperaba cierra parentesis"
                    + textoError());
            }

        return error1;
    }
    protected boolean InsAsignacion() throws Exception{
        boolean error1 = false;
        boolean parh3 = false;

        TipoTs tipo2 = Mem();

        if (!dosPuntosIgual()){
            throw new Exception("FATAL: Se esperaba simbolo :="
                    + textoError());
        }

        parh3 = false;

        Object[] expRes= Expresion(parh3);
        TipoTs tipo3 = (TipoTs) expRes[0];
        String modo3 = (String) expRes[1];

        etq += 1;
        error1 = ! GestorTs.compatibles(tipo2, tipo3,ts);
        if (GestorTs.esCompatibleConTipoBasico(tipo2, ts))
            cod.appendIns(new DesapilaInd());
        else
            cod.appendIns(new Mueve(new Nat(tipo2.getTam())));
        return error1;
    }
    protected boolean InsCompuesta() throws Exception{
        boolean error1 = false;

        if (!abreLlave()){
            throw new Exception("FATAL: Se esperaba símbolo abre llave"
                    + textoError());
        }
        boolean error2 = Instrucciones();

        if (!cierraLlave()){
            throw new Exception("FATAL: Se esperaba simbolo cierra llave"
                    + textoError());
        }

        error1 = error2;
        return false;
    }
    protected boolean InsIf() throws Exception{
        boolean error1 = false;
        boolean parh2 = false;
        int aux1;
        int aux2;
        
        if (If()){
            parh2 = false;

            Object[] expRes = Expresion(parh2);
            TipoTs tipo2 = (TipoTs) expRes[0];
            String modo2 = (String) expRes[1];

            if (!then()){
                throw new Exception("FATAL: Se esperaba simbolo la palabra then"
                    + textoError());
            }

            etq += 1;
            cod.appendIns(null);
            aux1 = etq;

            boolean error3 = Instruccion();

            cod.insertaCod(new IrFalse(new Nat(etq+1)), aux1 -1); //reemplaza el noop con el ir-f. en la posicion del código aux
            etq += 1;
            aux2=etq;
            cod.appendIns(null);

            boolean error4 = PElse();

            if (!error4){
                cod.insertaCod(new IrA(new Nat(etq)),aux2 -1);
                error1 = !tipo2.getT().equals("boolean") || error3 || error4;
            }
        }
        return error1;
    }
    protected boolean PElse() throws DatoExc, LectorExc, Exception{
        boolean error1 = false;
        if (Else()){
            boolean error2 = Instruccion();
            error1 = error2;
        }
        else{
            error1 =false;
        }
        return error1;
    }
    protected boolean InsWhile() throws DatoExc, LectorExc, Exception{
        boolean error1 = false;
        boolean parh2 = false;
        int aux = 0;
        int etq_while = 0;

        if( While()){
            etq_while = etq;
            
            Object[] expRes = Expresion(parh2);
            TipoTs tipo2 = (TipoTs) expRes[0];
            String modo2 = (String) expRes[1];
            
            if (!Do()){
                
            }
            etq += 1;
            aux = etq;
            cod.appendIns(null);

            boolean error3 = Instruccion();

            error1 = tipo2.getT().equals("boolean") || error3;
            
            etq += 1;
            cod.appendIns(new IrA(new Nat(etq_while)));
            cod.insertaCod(new IrFalse(new Nat(etq)), aux-1);
            
        }
        return error1;
    }
    protected boolean InsFor() throws Exception{
        boolean error1 = false;
        boolean parh2 = false;
        boolean parh3 = false;
        int etq_for = 0;
        int aux = 0;
        
        if(For()){
            parh2= false;
            parh3 = false;
            
            String lex = identificador();
            if (!GestorTs.existe(ts, lex)) {
            	throw new Exception("FATAL: Variable contador no declarada"
            			+textoError());
            }
            if (! igual() && !dosPuntosIgual()){
                throw new Exception("FATAL: Se esperaba simbolo = o := "
                    + textoError());
            }
            etq+= longAccesoVar(GestorTs.getProps(ts, lex));
            cod.appendCod(accesoVar(GestorTs.getProps(ts, lex)));    
            
            Object[] expRes = Expresion(parh2);
            TipoTs tipo2 = (TipoTs) expRes[0];
            String modo2 = (String) expRes[1];
            
            etq++;
            cod.appendIns(new DesapilaInd());
            //asigna a id el valor inicial
            
            if (! to()){
                throw new Exception("FATAL: Se esperaba simbolo la palabra to"
                    + textoError());
            }
                    
            Object[] expRes2 = Expresion(parh3);
            TipoTs tipo3 = (TipoTs) expRes2[0];
            String modo3 = (String) expRes2[1];
                    
            if ( !Do()){
                throw new Exception("FATAL: Se esperaba simbolo la palabra do"
                    + textoError());
            }

            etq_for = etq;
            
            //copia el resultado de Expresion2
            etq++;
            cod.appendIns(new Copia());
            
            //accedemos a id
            etq+= 1+ longAccesoVar(GestorTs.getProps(ts,lex));
            cod.appendCod(accesoVar(GestorTs.getProps(ts, lex)));
            cod.appendIns(new ApilarInd());
            
            //comparamos si son iguales y saltamos si no
            etq+=2;
            cod.appendIns(new Igual());
            cod.appendIns(null);
            aux = etq - 1;

            boolean error4 = Instruccion();

            error1 = error4 || (!tipo2.getT().equals("natural") && !tipo2.getT().equals("integer")) ||
                            (!tipo3.getT().equals("natural") && !tipo3.getT().equals("integer")) ||
                            (!GestorTs.getProps(ts, lex).getTipo().getT().equals("natural") && !GestorTs.getProps(ts, lex).getTipo().getT().equals("integer"));

            
            //actualizar id
            //accedemos a id
            etq += 1 + longAccesoVar(GestorTs.getProps(ts, lex));
            cod.appendCod(accesoVar(GestorTs.getProps(ts, lex)));
            cod.appendIns(new Copia()); //lo copiamos desapilar sobre él.
            //apilamos su valor
            etq++;
            cod.appendIns(new ApilarInd());
            //sumamos 1
            etq+=2;
            cod.appendIns(new Apilar(new Nat(1)));
            cod.appendIns(new Suma());
            //lo guardamos
            etq++;
            cod.appendIns(new DesapilaInd());
            //vamos a la cabeza del bucle
            etq++;
            cod.appendIns(new IrA(new Nat(etq_for)));
            //si se sale habrá que saltar justo a partir de aquí
            cod.insertaCod(new IrTrue(new Nat(etq)), aux);
            //ir
            etq++;
            cod.appendIns(new Desapilar());
            
        }
        return error1;
    }
    protected boolean InsNew() throws LectorExc, DatoExc, Exception{
        boolean error1 = false;
        if (New()){
            TipoTs tipo2 = Mem();

            error1 = !tipo2.getT().equals("puntero");
            if (error1) throw new Exception("FATAL: Se esperaba un puntero "+ textoError());
            etq += 2;
            int num;
            if (tipo2.getBase().getT().equals("ref"))
                num = GestorTs.getProps(ts,tipo2.getBase().getId()).getTipo().getTam(); //no se si esta bien
            else num = tipo2.getBase().getTam();
            cod.appendIns(new New(new Nat(num)));
            cod.appendIns(new DesapilaInd());
        }
        return error1;
    }
    protected boolean InsDis() throws LectorExc, DatoExc, Exception{
        boolean error1 = false;

        if (dispose()){
            TipoTs tipo2 = Mem();

            error1 = !tipo2.getT().equals("puntero");
            if (error1) throw new Exception("FATAL: Se esperaba un puntero "+ textoError());
            etq+=2;
          //saca la dirección apuntada
            cod.appendIns(new ApilarInd());
            //borramos
            if (tipo2.getBase().getT().equals("ref"))
                cod.appendIns(new Delete(new Nat(GestorTs.getProps(ts, tipo2.getBase().getId()).getTipo().getTam()))); //tam?? hay tamaño
            else
            	cod.appendIns(new Delete(new Nat(tipo2.getBase().getTam())));;
        }

        return error1;
    }
    protected TipoTs Mem() throws Exception{
        TipoTs tipo1 = null;
        TipoTs tipo2h = null;

        String lex = identificador();

        if (GestorTs.existe(ts, lex)){
            if (GestorTs.getProps(ts, lex).getClase().equals("var"))
                tipo2h = GestorTs.ref(GestorTs.getProps(ts, lex).getTipo(), ts);
            else {
            	tipo2h = new TipoTs();
                tipo2h.setT("error");
            }
        }
        else {
        	tipo2h = new TipoTs();
        	tipo2h.setT("error");
        }
        if (!tipo2h.getT().equals("error")){
        	etq+=longAccesoVar(GestorTs.getProps(ts, lex));
        	cod.appendCod(accesoVar(GestorTs.getProps(ts, lex)));
        }
        
        
        TipoTs tipo2=MemRec(tipo2h);

        tipo1 = tipo2;

        return tipo1;
    }
    protected TipoTs MemRec(TipoTs tipoh1) throws  Exception{
        TipoTs tipo1 = null;

        Token t = sigToken();
        atrasToken();
        if (t instanceof Circunflejo){
            tipo1 = MemRecPuntero(tipoh1);
        }
        else if (t instanceof Corchete_a){
            tipo1 = MemRecArray(tipoh1);
        }
        else if (t instanceof Punto){
            tipo1 = MemRecCampo(tipoh1);
        }
        else if (t instanceof Identificador){
            tipo1 = MemRecIdentificador();
        }
        else
            //->lamda
            tipo1 = tipoh1;

        return tipo1;
    }
    protected TipoTs MemRecPuntero(TipoTs tipoh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        TipoTs tipoh2 = null;

        if (circunflejo()){
            if (tipoh1.getT().equals("puntero"))
                tipoh2= GestorTs.ref(tipoh1.getBase(),ts);
            else tipoh2 = new TipoTs("error");

            etq += 1;
            cod.appendIns(new ApilarInd());

            TipoTs tipo2 = MemRec(tipoh2);

            tipo1 = tipo2;
        }
        return tipo1;
    }
    protected TipoTs MemRecArray(TipoTs tipoh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        boolean parh2 = false;
        TipoTs tipoh3 = null;

        if (abreCorchete()){
            parh2 = false; //(supongo? no estaba)

            Object[] expRes= Expresion(parh2);
            TipoTs tipo2 = (TipoTs) expRes[0];
            String modo2 = (String) expRes[1];

            if(!cierraCorchete()){
                throw new Exception("FATAL: Se esperaba cierra corchete"
                    + textoError());
            }

            etq += 3;
            cod.appendIns(new Apilar(new Nat(tipoh1.getBase().getTam())));
            cod.appendIns(new Multiplica());
            cod.appendIns(new Suma());
            if (tipoh1.getT().equals("array") && (tipo2.getT().equals ("natural") || tipo2.getT().equals("integer")))
                tipoh3 = GestorTs.ref(tipoh1.getBase(),ts);
            else tipoh3 = new TipoTs("error");

            TipoTs tipo3 = MemRec(tipoh3);

            tipo1=tipo3;
        }
        return tipo1;
    }
    protected TipoTs MemRecCampo(TipoTs tipoh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        TipoTs tipoh2 = null;

        if (punto()){
            String lex =identificador();
            etq += 2;
            cod.appendIns(new Apilar(new Nat(tipoh1.getCampo(lex).getDesp())));
            cod.appendIns(new Suma());

            if (tipoh1.getT().equals("record"))
                if (Campo.existeCampo(tipoh1.getCampos(),lex))
                        tipoh2 = GestorTs.ref(tipoh1.getCampo(lex).getTipo(),ts);
                else tipoh2 = new TipoTs("error");
            else tipoh2 = new TipoTs("error");

            TipoTs tipo2 = MemRec(tipoh2);

            tipo1 = tipo2;
        }
        return tipo1;
    }
    protected TipoTs MemRecIdentificador() throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        TipoTs tipo2h = null;

        String lex = identificador();

        if (GestorTs.existe(ts, lex)){
            if (GestorTs.getProps(ts, lex).getClase().equals("var"))
                tipo2h = GestorTs.ref(GestorTs.getProps(ts, lex).getTipo(),ts);
            else tipo2h = new TipoTs("error");
        }
        else tipo2h = new TipoTs("error");

        etq += longAccesoVar(GestorTs.getProps(ts, lex)); //mirar
        cod.appendCod(accesoVar(GestorTs.getProps(ts, lex)));


        TipoTs tipo2 = MemRec(tipo2h);

        tipo1 = tipo2;

        return tipo1;
    }
    protected Object[] Expresion(boolean parh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        boolean parh2 = parh1;

        Object[] resExpNiv1 = ExpresionNiv1(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv1[0];
        String modo2 = (String) resExpNiv1[1];

        TipoTs tipoh3 = tipo2;
        String modoh3 = modo2;

        Object[] resExpFact = ExpresionFact(tipoh3, modoh3);
        TipoTs tipo3 = (TipoTs) resExpFact[0];
        String modo3 = (String) resExpFact[1];

        tipo1 = tipo3;
        modo1 = modo3;

        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionFact(TipoTs tipoh1, String modoh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        Operaciones op=OpNiv0();

        if (op==null){//->lamda
            return new Object[]{tipoh1,modoh1};
        }
        boolean parh2 = false;

        Object[] resExpNiv1 = ExpresionNiv1(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv1[0];
        String modo2 = (String) resExpNiv1[1];

        if (tipoh1.getT().equals("error") ||
                tipo2.getT().equals("error") ||
                (tipoh1.getT().equals("character") && !tipo2.getT().equals("character")) ||
                (!tipoh1.getT().equals("character") && tipo2.getT().equals("character")) ||
                (tipoh1.getT().equals("boolean") && !tipo2.getT().equals("boolean")) ||
                (!tipoh1.getT().equals("boolean") && tipo2.getT().equals("boolean")))
            tipo1 = new TipoTs("error");
        else tipo1 = new TipoTs("boolean");

	modo1 = "valor";
	etq += 1;
        switch(op){
            case MENOR:
                cod.appendIns(new Menor());
                break;
            case MAYOR:
                cod.appendIns(new Mayor());
                break;
            case MENORIG:
                cod.appendIns(new MenorIg());
                break;
            case MAYORIG:
                cod.appendIns(new MayorIg());
                break;
            case IGUAL:
                cod.appendIns(new Igual());
                break;
            case DISTINTO:
                cod.appendIns(new NoIgual());
                break;
        }
        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv1(boolean parh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        boolean parh2 = parh1;

        Object[] resExpNiv2 = ExpresionNiv2(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv2[0];
        String modo2 = (String) resExpNiv2[1];

        TipoTs tipoh3 = tipo2;
        String modoh3 = modo2;

        Object[] resExpNiv1Rec = ExpresionNiv1Rec(tipoh3, modoh3);
        TipoTs tipo3 = (TipoTs) resExpNiv1Rec[0];
        String modo3 = (String) resExpNiv1Rec[1];

        tipo1 = tipo3;
        modo1 = modo3;

        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv1Rec(TipoTs tipoh1, String modoh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";
        TipoTs tipoh3 = null;
        int aux = 0;

        Operaciones op= OpNiv1();

        if (op == null){
            return new Object[]{tipoh1,modoh1};
        }

        boolean parh2 = false;
        if (op == op.OR){
            aux = etq +1;
            etq += 3;
            cod.appendIns(new Copia());
            cod.appendIns(null);
            cod.appendIns(new Desapilar());
        }

        Object[] resExpNiv2 = ExpresionNiv2(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv2[0];
        String modo2 = (String) resExpNiv2[1];

        String modoh3 = modo2;
        if (tipoh1.getT().equals("error") ||
                tipo2.getT().equals("error") ||
                (tipoh1.getT().equals("character") && !tipo2.getT().equals("character")) ||
                (!tipoh1.getT().equals("character") && tipo2.getT().equals("character")) ||
                (tipoh1.getT().equals("boolean") && !tipo2.getT().equals("boolean")) ||
                (!tipoh1.getT().equals("boolean") && tipo2.getT().equals("boolean")))
            tipoh3 = new TipoTs("error");
        else
            switch (op){
                case SUMA:
                case RESTA:
                    if (tipoh1.getT().equals("float") && tipo2.getT().equals("float"))
                        tipoh3 = new TipoTs("float");
                    else if (tipoh1.getT().equals("integer") && tipo2.getT().equals("integer"))
                        tipoh3 = new TipoTs("integer");
                    else if (tipoh1.getT().equals("natural") && tipo2.getT().equals("natural"))
                        tipoh3 = new TipoTs("natural");
                    else tipoh3 = new TipoTs("error");
                    break;
                case OR:
                     if (tipoh1.getT().equals("boolean") && tipo2.getT().equals("boolean"))
                        tipoh3 = new TipoTs("boolean");
                     else tipoh3 = new TipoTs ("error");
                     break;
            }
        modoh3 = "valor";
        if (op == op.OR)
            cod.insertaCod(new IrTrue(new Nat(etq)), aux);
        else{
            etq +=1;
            switch (op){
                case SUMA:
                    cod.appendIns(new Suma());
                    break;
                case RESTA:
                    cod.appendIns(new Resta());
                    break;
           }
        }

        Object[] resExpNiv1 = ExpresionNiv1Rec(tipoh3,modoh3);
        TipoTs tipo3 = (TipoTs) resExpNiv1[0];
        String modo3 = (String) resExpNiv1[1];

        tipo1 = tipo3;
        modo1 = modo3;

        return new Object[]{tipo1,modo1};

    }
    protected Object[] ExpresionNiv2(boolean parh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        boolean parh2 = parh1;

        Object[] resExpNiv2 = ExpresionNiv3(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv2[0];
        String modo2 = (String) resExpNiv2[1];

        TipoTs tipoh3 = tipo2;
        String modoh3 = modo2;

        Object[] resExpNiv1Rec = ExpresionNiv2Rec(tipoh3, modoh3);
        TipoTs tipo3 = (TipoTs) resExpNiv1Rec[0];
        String modo3 = (String) resExpNiv1Rec[1];

        tipo1 = tipo3;
        modo1 = modo3;

        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv2Rec(TipoTs tipoh1, String modoh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";
        TipoTs tipoh3 = null;
        int aux = 0;

        Operaciones op= OpNiv2();

        if (op == null){
            return new Object[]{tipoh1,modoh1};
        }

        boolean parh2 = false;
        if (op == op.AND){
            aux = etq;
            etq += 1;
            cod.appendIns(null);
        }

        Object[] resExpNiv2 = ExpresionNiv3(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv2[0];
        String modo2 = (String) resExpNiv2[1];

        if (tipoh1.getT().equals("error") ||
                tipo2.getT().equals("error") ||
                (tipoh1.getT().equals("character") && !tipo2.getT().equals("character")) ||
                (!tipoh1.getT().equals("character") && tipo2.getT().equals("character")) ||
                (tipoh1.getT().equals("boolean") && !tipo2.getT().equals("boolean")) ||
                (!tipoh1.getT().equals("boolean") && tipo2.getT().equals("boolean")))
            tipoh3 = new TipoTs("error");
        else
            switch (op){
                case MULT:
                case DIV:
                    if (tipoh1.getT().equals("float") && tipo2.getT().equals("float"))
                        tipoh3 = new TipoTs("float");
                    else if (tipoh1.getT().equals("integer") && tipo2.getT().equals("integer"))
                        tipoh3 = new TipoTs("integer");
                    else if (tipoh1.getT().equals("natural") && tipo2.getT().equals("natural"))
                        tipoh3 = new TipoTs("natural");
                    else tipoh3 = new TipoTs("error");
                    break;
                case MOD:
                    if(tipo2.getT().equals("natural") &&
                            (tipoh1.getT().equals("natural") || tipoh1.getT().equals("integer")))
                        tipoh3 = tipoh1;
                    else tipoh3 = new TipoTs("error");
                case AND:
                     if (tipoh1.getT().equals("boolean") && tipo2.getT().equals("boolean"))
                        tipoh3 = new TipoTs("boolean");
                     else tipoh3 = new TipoTs ("error");
                     break;
            }
        String modoh3 = "valor";
        if (op == op.AND){
             cod.insertaCod(new IrFalse(new Nat(etq+1)), aux);
             cod.appendIns(new IrA(new Nat(etq+2)));
             cod.appendIns(new Apilar(new Bool(false)));
             etq += 2;
        }
        else{
            etq +=1;
            switch (op){
                case MULT:
                    cod.appendIns(new Multiplica());
                    break;
                case DIV:
                    cod.appendIns(new Divide());
                    break;
                case MOD:
                    cod.appendIns(new Modulo());
                    break;
           }
        }

        Object[] resExpNiv1 = ExpresionNiv2Rec(tipoh3,modoh3);
        TipoTs tipo3 = (TipoTs) resExpNiv1[0];
        String modo3 = (String) resExpNiv1[1];

        tipo1 = tipo3;
        modo1 = modo3;

        return new Object[]{tipo1,modo1};

    }
    protected Object[] ExpresionNiv3(boolean parh1) throws LectorExc, Exception{
        TipoTs tipo1 = null;
        String modo1 ="";

        boolean parh2 = parh1;

        Object[] resExpNiv4= ExpresionNiv4(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv4[0];
        String modo2 = (String) resExpNiv4[1];

        TipoTs tipoh3 = tipo2;
        String modoh3= modo2;

        Object[] resExpNiv3Fact = ExpresionNiv3Fact(tipoh3,modoh3);
        TipoTs tipo3 = (TipoTs) resExpNiv3Fact[0];
        String modo3 = (String) resExpNiv3Fact[1];

        tipo1 = tipo3;
        modo1 = modo3;

        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv3Fact(TipoTs tipoh1, String modoh1) throws LectorExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        Operaciones op=OpNiv3();

        if(op == null){
            return new Object[]{tipoh1,modoh1};
        }

        boolean parh2 = false;

        Object[] resExpNiv3 = ExpresionNiv3(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv3[0];
        String modo2 = (String) resExpNiv3[1];

        modo1 = "valor";
        if (tipoh1.getT().equals("error") ||
                tipo2.getT().equals("error") ||
                !tipoh1.getT().equals("natural") ||
                !tipo2.getT().equals("natural"))
            tipo1 = new TipoTs("error");
        else  tipo1 = new TipoTs("natural");
	etq += 1;
        switch (op){
            case SHL:
                cod.appendIns(new Shl());
                break;
            case SHR:
                cod.appendIns(new Shr());
                break;
        }
        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv4(boolean parh1) throws LectorExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        Token t = sigToken();
        atrasToken();

        if (t instanceof Not || t instanceof Signo_menos || t instanceof Cast_float || t instanceof Cast_int || t instanceof Cast_nat || t instanceof Cast_char){
            return ExpresionNiv4_conOp(parh1);
        }
        else if ( t instanceof Token_Absoluto){
            return ExpresionNiv4_valorAbs(parh1);
        }
        else if (t instanceof Parentesis_a){
            return ExpresionNiv4_abrePar(parh1);
        }
        else if (t instanceof LitNat || t instanceof LitFlo || t instanceof LitTrue || t instanceof LitFalse || t instanceof LitCha) {
            return ExpresionNiv4_literal(parh1);
        }
        else if (t instanceof Identificador){
            return ExpresionNiv4_mem(parh1);
        }
        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv4_conOp(boolean parh1) throws LectorExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        Operaciones op = OpNiv4();

        boolean parh2 = false;

        Object[] resExpNiv4 = ExpresionNiv4(parh2);
        TipoTs tipo2 = (TipoTs) resExpNiv4[0];
        String modo2 = (String) resExpNiv4[1];

        modo1 = "valor";
        if (tipo2.getT().equals("error"))
            tipo1 = new TipoTs("error");
        else
            switch (op){
                case NOT:
                    if (tipo2.getT().equals ("boolean"))
                        tipo1 = new TipoTs("boolean");
                    else tipo1 = new TipoTs("error");
                    break;
                case NEG:
                    if (tipo2.getT().equals ("float"))
                        tipo1 = new TipoTs("float");
                    else if (tipo2.getT().equals("integer") && tipo2.getT().equals("natural"))
                        tipo1 = new TipoTs("integer");
                    else
                        tipo1 = new TipoTs("error");
                    break;
                case CASTREAL:
                    if (tipo2.getT().equals("boolean"))
                        tipo1 = new TipoTs("float");
                    else tipo1 = new TipoTs("error");
                    break;
                case CASTENT:
                    if (tipo2.getT().equals("boolean"))
                        tipo1 = new TipoTs("integer");
                    else tipo1 = new TipoTs("error");
                    break;
                case CASTNAT:
                    if  (tipo2.getT().equals("natural") && tipo2.getT().equals("character"))
                        tipo1 = new TipoTs("natural");
                    else tipo1 = new TipoTs("error");
                    break;
                case CASTCHAR:
                    if  (tipo2.getT().equals("natural") && tipo2.getT().equals("character"))
                        tipo1 = new TipoTs("character");
                    else tipo1 = new TipoTs("error");
                    break;
            }
        etq += 1;
        switch (op){
        case NOT:
        	cod.appendIns(new No());
        	break;
        case NEG:
        	cod.appendIns(new Menos());
        	break;
        case CASTREAL:
        	cod.appendIns(new CastFloat());
        	break;
        case CASTENT:
        	cod.appendIns(new CastInt());
        	break;
        case CASTNAT:
        	cod.appendIns(new CastNat());
        	break;
        case CASTCHAR:
        	cod.appendIns(new CastChar());
        	break;
        }
        
        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv4_valorAbs(boolean parh1) throws LectorExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        if(valorAbs()){
            boolean parh2 = false;

            Object[] resExp = Expresion(parh2);
            TipoTs tipo2 = (TipoTs) resExp[0];
            String modo2 = (String) resExp[1];

            if (!valorAbs()){
                throw new Exception("FATAL: Se esperaba valor Absoluto"
                    + textoError());
            }

            if (tipo2.getT().equals("error") || tipo2.getT().equals("boolean") || tipo2.getT().equals("character"))
                tipo1 = new TipoTs("error");
            else if (tipo2.getT().equals("float"))
                tipo1 = new TipoTs("float");
            else if (tipo2.getT().equals("natural") || tipo2.getT().equals("integer"))
                tipo1 = new TipoTs("natural");
            else tipo1 = new TipoTs("error");

            cod.appendIns(new Abs());
            modo1 = "variable";
        }

        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv4_abrePar(boolean parh1) throws Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        if(abrePar()){
            boolean parh2 = false;

            Object[] resExp = Expresion(parh2);
            TipoTs tipo2 = (TipoTs) resExp[0];
            String modo2 = (String) resExp[1];

            if (!cierraPar()){
                throw new Exception("FATAL: Se esperaba cerrar paréntesis"
                    + textoError());
            }

            tipo1 = tipo2;
            modo1 = modo2;
        }

        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv4_literal(boolean parh1) throws Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        TipoTs tipo2 = Literal();

        modo1 = "variable";
        tipo1 = tipo2;

        return new Object[]{tipo1,modo1};
    }
    protected Object[] ExpresionNiv4_mem(boolean parh1) throws LectorExc, DatoExc, Exception{
        TipoTs tipo1 = null;
        String modo1 = "";

        TipoTs tipo2 = Mem();
    
        if (GestorTs.esCompatibleConTipoBasico(tipo2,ts) && !parh1){
            cod.appendIns(new ApilarInd());
            etq += 1;
            modo1="valor";
        }
        else
        	modo1 = "variable";
        tipo1 = tipo2;

        return new Object[]{tipo1,modo1};
    }
    protected TipoTs Literal() throws Exception {
        Token t = sigToken();
        if (t instanceof LitNat) {
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
    protected TipoTs Literal_LitTrue() throws Exception {
        TipoTs tipo =null;

        cod.appendIns(new Apilar(new Bool(true)));
        etq +=1;
        tipo = new TipoTs("boolean");

        return tipo;
    }
    protected TipoTs Literal_LitFalse() throws Exception {
        TipoTs tipo =null;

        cod.appendIns(new Apilar(new Bool(false)));
        etq +=1;
        tipo = new TipoTs("boolean");

        return tipo;
    }
    protected TipoTs Literal_LitCha(Token t) throws Exception {
        TipoTs tipo =null;

        cod.appendIns(new Apilar(new Caracter(t.getLex().charAt(1))));
        etq +=1;
        tipo = new TipoTs("character");

        return tipo;
    }
    protected TipoTs Literal_LitNat(Token t) throws Exception {
        TipoTs tipo =null;

        cod.appendIns(new Apilar(new Nat(Integer.parseInt(t.getLex()))));
        etq +=1;
        tipo = new TipoTs("natural");

        return tipo;
    }
    protected TipoTs Literal_LitFlo(Token t) throws Exception {
        TipoTs tipo =null;

        cod.appendIns(new Apilar(new Real(Float.parseFloat(t.getLex()))));
        etq +=1;
        tipo = new TipoTs("float");

        return tipo;
    }
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
