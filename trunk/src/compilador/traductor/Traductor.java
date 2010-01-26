package compilador.traductor;

import java.util.ArrayList;
import java.util.Iterator;

import pila.interprete.instrucciones.InstruccionInterprete;

import compilador.lexico.Tokens.Absoluto;
import compilador.lexico.Tokens.And;
import compilador.lexico.Tokens.Cast_char;
import compilador.lexico.Tokens.Cast_float;
import compilador.lexico.Tokens.Cast_int;
import compilador.lexico.Tokens.Cast_nat;
import compilador.lexico.Tokens.Distinto;
import compilador.lexico.Tokens.Division;
import compilador.lexico.Tokens.Dos_puntos;
import compilador.lexico.Tokens.Dos_puntos_ig;
import compilador.lexico.Tokens.LitFalse;
import compilador.lexico.Tokens.FloatToken;
import compilador.lexico.Tokens.Identificador;
import compilador.lexico.Tokens.In;
import compilador.lexico.Tokens.LitCha;
import compilador.lexico.Tokens.LitFlo;
import compilador.lexico.Tokens.LitNat;
import compilador.lexico.Tokens.Mayor_ig;
import compilador.lexico.Tokens.Menor_ig;
import compilador.lexico.Tokens.Multiplicacion;
import compilador.lexico.Tokens.Natural;
import compilador.lexico.Tokens.Not;
import compilador.lexico.Tokens.Or;
import compilador.lexico.Tokens.Out;
import compilador.lexico.Tokens.Parentesis_a;
import compilador.lexico.Tokens.Parentesis_c;
import compilador.lexico.Tokens.Punto_coma;
import compilador.lexico.Tokens.Separador;
import compilador.lexico.Tokens.Signo_menos;
import compilador.lexico.Tokens.Token;
import compilador.lexico.Tokens.LitTrue;
import compilador.lexico.Tokens.character;
import compilador.tablaSimbolos.TablaSimbolos;
import compilador.tablaSimbolos.InfoTs.Tipos;
import org.apache.bcel.classfile.JavaClass;
import pila.jvm.ClassConstructor;
import pila.jvm.instrucciones.PseudoInstruccionJVM;

/**
 * Esta clase contiene las funciones de traducción que sólo dependen de la
 * gramática y no del código  que se va a generar.
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 *
 */
public abstract class Traductor {

    protected ArrayList<InstruccionInterprete> traduccionP;
    protected JavaClass traduccionJ;

    protected ArrayList<Token> arrayTokens;
    protected TablaSimbolos ts;
    protected int numVars;
    protected int i_token;
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
        numVars = 0;
        i_token = 0;
        errores = new ArrayList<ErrorTraductor>();
        ts = new TablaSimbolos();
    }

    public void traducir(String nombreClase) throws Exception {
        Codigo codP = new Codigo();
        CodigoJVM codJ = new CodigoJVM();
        try {
            Object[] resultado = Programa();
            codP = (Codigo) resultado[1];
            codJ = (CodigoJVM) resultado[2];
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
        traduccionP = codP.getCod();
        /*
         * Calcular la altura maxima de la pila es algo complejo, pero al no
         * usar en la traduccion ninguna instruccion que apile dos elementos en
         * la pila, su altura nunca podra ser mayor que el numero de instrucciones
         * del programa
         */
        traduccionJ = codJ.dameTraduccion(numVars, codJ.size(), nombreClase);
    }

    public ArrayList<InstruccionInterprete> getTraduccionP(String nombreClase) throws Exception {
        if(traduccionP == null)
            traducir(nombreClase);
        return traduccionP;
    }

    public ArrayList<InstruccionInterprete> getTraduccionP() throws Exception {
        return getTraduccionP("Clase");
    }

    public JavaClass getTraduccionJ(String nombreClase) throws Exception {
        if(traduccionJ == null)
            traducir(nombreClase);
        return traduccionJ;
    }

    public JavaClass getTraduccionJ() throws Exception {
        return getTraduccionJ("Clase");
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

    protected String Identificador() {
        Token t = sigToken();
        if (!(t instanceof Identificador)) {
            atrasToken();
            return null;
        } else {
            return t.getLex();
        }
    }

    //-----------------------------------------
    //-------implementación--------------------
    //Programa(out: error1, cod1)
    protected Object[] Programa() throws Exception {
        boolean error1 = false;
        Codigo codP1 = null;
        CodigoJVM codJ1;
        error1 |= Declaraciones();
        //ERROR fatal si no hay ampersand
        if (!ampersand()) {
            throw new Exception("FATAL: & no encontrado" + textoError());
        }
        Object[] resInst = Instrucciones();
        error1 |= (Boolean) resInst[0];
        codP1 = (Codigo) resInst[1];
        codJ1 = (CodigoJVM) resInst[2];
        if (error1) {
            errores.add(
                    new ErrorTraductor("Info: Se han detectado errores en el programa. "
                    + "El código generado puede no ser válido."));
        }
        return new Object[]{error1, codP1, codJ1};
    }

    //Declaraciones (out: error1)→
    private boolean Declaraciones() throws Exception {
        Object[] decRes = Declaracion();
        boolean errorh3 = (Boolean) decRes[0];
        String idh3 = (String) decRes[1];
        Tipos tipoh3 = (Tipos) decRes[2];
        boolean error3 = DeclaracionesFact(idh3, tipoh3, errorh3);
        return error3;
    }

    //Declaración(out: error1, id1, tipo1) → id : tipo
    private Object[] Declaracion() throws Exception {
        String id1 = null;
        Tipos tipo1 = null;
        boolean error1 = false;
        id1 = Identificador();
        if (id1 == null) {
            throw new Exception("FATAL: Se esperaba un identificador" + textoError());
        }
        if (!dosPuntos()) {
            throw new Exception("FATAL: Se esperaban dos puntos" + textoError());
        }
        tipo1 = Tipo();
        if (tipo1 == Tipos.ERROR) {
            errores.add(new ErrorTraductor("Info: Error identificando tipo en declaración" + textoError()));
            error1 = true;
        }
        return new Object[]{error1, id1, tipo1};
    }

    protected Tipos Tipo() {
        Token t = sigToken();
        if (t instanceof compilador.lexico.Tokens.Integer) {
            return Tipos.ENTERO;
        }
        if (t instanceof Natural) {
            return Tipos.NATURAL;
        }
        if (t instanceof compilador.lexico.Tokens.Boolean) {
            return Tipos.BOOL;
        }
        if (t instanceof FloatToken) {
            return Tipos.REAL;
        }
        if (t instanceof character) {
            return Tipos.CHAR;
        }
        return Tipos.ERROR;
    }

    //DeclaracionesFact(in: idh1, tipoh1, errorh1; out: error1) →
    protected boolean DeclaracionesFact(String idh1, Tipos tipoh1, boolean errorh1) throws Exception {
        boolean error1;
        boolean error2 = false;
        if (puntoYComa()) {//no lambda
            //atención! si todas las declaraciones acaban en PYC.
            if (ampersand()) {
                numVars = 0;
                atrasToken();
            } else {
                error2 = Declaraciones();
            }
        } else {
            numVars = 0;
        }

        if (!errorh1 && !TablaSimbolos.existe(ts, idh1)) {
            ts = TablaSimbolos.inserta(ts, idh1, tipoh1, numVars);
            numVars++;
        } else {
            errores.add(new ErrorTraductor("Error en declaracion o variable ya declarada. Omitiendo"));
        }

        error1 = errorh1 || error2;
        return error1;
    }

    //Instrucciones(out: error1,cod1) →
    protected Object[] Instrucciones() throws Exception {
        boolean error1;
        Codigo codP1;
        CodigoJVM codJ1;

        Object[] resIns = Instruccion();
        boolean error2 = (Boolean) resIns[0];
        Codigo codP2 = (Codigo) resIns[1];
        CodigoJVM codJ2 = (CodigoJVM) resIns[2];

        Object[] resInsFact = InstruccionesFact(error2, codP2, codJ2);
        error1 = (Boolean) resInsFact[0];
        codP1 = (Codigo) resInsFact[1];
        codJ1 = (CodigoJVM) resInsFact[2];
        return new Object[]{error1, codP1, codJ1};
    }

    //InstruccionesFact(in: errorh1,codh1; out: error1,cod1) →
    protected Object[] InstruccionesFact(boolean errorh1, Codigo codPh1, CodigoJVM codJh1) throws Exception {
        boolean error1;
        boolean error2 = false;
        Codigo codP1 = new Codigo();
        CodigoJVM codJ1 = new CodigoJVM();
        if (puntoYComa() && !(i_token >= arrayTokens.size())) { //no lambda
            //fin programa
            Object[] resInst = Instrucciones();
            error2 = (Boolean) resInst[0];
            Codigo codP2 = (Codigo) resInst[1];
            CodigoJVM codJ2 = (CodigoJVM) resInst[2];
            codP1 = codPh1;
            codP1.appendCod(codP2);

            codJ1 = codJh1;
            codJ1.append(codJ2);
        } else if (i_token >= arrayTokens.size()) {//lambda
            codP1 = codPh1;
            codJ1 = codJh1;
        } else {
            throw new Exception("FATAL: Instrucción incorrecta: Se esperaba ; o fin de programa" + textoError());
        }

        error1 = error2 || errorh1;
        return new Object[]{error1, codP1, codJ1};
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
        Codigo codP2 = new Codigo();
        CodigoJVM codJ2 = new CodigoJVM();

        if (!error2L) {//ins Lectura
            codP2 = (Codigo) resLect[1];
            codJ2 = (CodigoJVM) resLect[2];
        } else if (!error2E) {
            codP2 = (Codigo) resEscr[1];
            codJ2 = (CodigoJVM) resEscr[2];
        } else if (!error2A) {
            codP2 = (Codigo) resAsig[1];
            codJ2 = (CodigoJVM) resAsig[2];
        } else {
            error1 = true;
            errores.add(new ErrorTraductor("Hay errores en la(s) instrucción(es) " + textoError()));
        }
        return new Object[]{error1, codP2, codJ2};
    }

    protected abstract Object[] InsLectura() throws Exception;

    protected abstract Object[] InsEscritura() throws Exception;

    protected abstract Object[] InsAsignacion() throws Exception;

    //Expresión(out: tipo1,cod1) →
    protected Object[] Expresion() throws Exception {
        Tipos tipo1 = null;
        Codigo codP1 = null;
        CodigoJVM codJ1;
        Object[] resExprN1 = ExpresionNiv1();
        Tipos tipo2 = (Tipos) resExprN1[0];
        Codigo codP2 = (Codigo) resExprN1[1];
        CodigoJVM codJ2 = (CodigoJVM) resExprN1[2];
        Object[] resExprFact = ExpresionFact(tipo2, codP2, codJ2);
        tipo1 = (Tipos) resExprFact[0];
        codP1 = (Codigo) resExprFact[1];
        codJ1 = (CodigoJVM) resExprFact[2];
        return new Object[]{tipo1, codP1, codJ1};
    }

    //ExpresiónFact(in: tipo1h,codPh1; out: tipo1,codP1) →
    protected abstract Object[] ExpresionFact(Tipos tipo1h, Codigo codPh1, CodigoJVM codJh1) throws Exception;

    //ExpresiónNiv1(out: tipo1, codP1, codJ1) →
    protected Object[] ExpresionNiv1() throws Exception {
        Tipos tipo1 = null;
        Codigo codP1 = null;
        CodigoJVM codJ1 = null;
        Object[] resExprN2 = ExpresionNiv2();
        Tipos tipo2 = (Tipos) resExprN2[0];
        Codigo codP2 = (Codigo) resExprN2[1];
        CodigoJVM codJ2 = (CodigoJVM) resExprN2[2];
        Object[] resExprFact = ExpresionNiv1Rec(tipo2, codP2, codJ2);
        tipo1 = (Tipos) resExprFact[0];
        codP1 = (Codigo) resExprFact[1];
        codJ1 = (CodigoJVM) resExprFact[2];
        return new Object[]{tipo1, codP1, codJ1};
    }

    //ExpresiónNiv1Rec(in: tipoh1, codh1; out: tipo1, cod1)
    protected abstract Object[] ExpresionNiv1Rec(Tipos tipoh1, Codigo codPh1, CodigoJVM codJh1) throws Exception;

    //ExpresiónNiv2(out: tipo1, cod1) →
    protected Object[] ExpresionNiv2() throws Exception {
        Tipos tipo1 = null;
        Codigo codP1 = null;
        CodigoJVM codJ1;
        Object[] resExprNiv3 = ExpresionNiv3();
        Tipos tipoh3 = (Tipos) resExprNiv3[0];
        Codigo codPh3 = (Codigo) resExprNiv3[1];
        CodigoJVM codJh3 = (CodigoJVM) resExprNiv3[2];
        Object[] resExprNiv2Rec = ExpresionNiv2Rec(tipoh3, codPh3, codJh3);
        tipo1 = (Tipos) resExprNiv2Rec[0];
        codP1 = (Codigo) resExprNiv2Rec[1];
        codJ1 = (CodigoJVM) resExprNiv2Rec[2];
        return new Object[]{tipo1, codP1, codJ1};
    }

    //ExpresiónNiv2Rec(in: tipoh1, codh1; out: tipo1, codP1)
    protected abstract Object[] ExpresionNiv2Rec(Tipos tipoh1, Codigo codPh1, CodigoJVM codJh1) throws Exception;

    //ExpresiónNiv3(out: tipo1, codJ1) →
    protected Object[] ExpresionNiv3() throws Exception {
        Tipos tipo1 = null;
        Codigo codP1 = null;
        CodigoJVM codJ1;
        Object[] resExprN4 = ExpresionNiv4();
        Tipos tipoh3 = (Tipos) resExprN4[0];
        Codigo codPh3 = (Codigo) resExprN4[1];
        CodigoJVM codJh3 = (CodigoJVM) resExprN4[2];
        Object[] resExprN4Fact = ExpresionNiv3Fact(tipoh3, codPh3, codJh3);
        tipo1 = (Tipos) resExprN4Fact[0];
        codP1 = (Codigo) resExprN4Fact[1];
        codJ1 = (CodigoJVM) resExprN4Fact[2];
        return new Object[]{tipo1, codP1, codJ1};
    }

    //ExpresiónNiv3Fact(in: tipoh1, codh1; out: tipo1, cod1)
    protected abstract Object[] ExpresionNiv3Fact(Tipos tipoh1, Codigo codPh1, CodigoJVM codJh1) throws Exception;

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
        Codigo codP1 = (Codigo) resExpr[1];
        CodigoJVM codJ1 = (CodigoJVM) resExpr[2];
        if (!cierraPar()) {
            throw new Exception("FATAL: Se esperaba cerrar paréntesis" + textoError());
        }
        return new Object[]{tipo1, codP1, codJ1};
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
