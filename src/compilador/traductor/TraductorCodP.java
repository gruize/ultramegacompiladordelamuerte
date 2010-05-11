package compilador.traductor;

import java.util.ArrayList;

import pila.interprete.datos.Bool;
import pila.interprete.datos.Caracter;
import pila.interprete.datos.Nat;
import pila.interprete.instrucciones.*;
import compilador.lexico.Tokens.Token;
import compilador.tablaSimbolos.TablaSimbolos;
import compilador.tablaSimbolos.InfoTs.Tipos;
import pila.interprete.datos.Real;


    // Literal(out: tipo1, cod11) → litFlo
public class TraductorCodP extends Traductor {

    public TraductorCodP(ArrayList<Token> tokens) {
        super(tokens);
    }

    // InsLectura(out:error1,cod) →
    protected Object[] InsLectura() throws Exception {
        if (!in()) {
            return new Object[]{true, new Codigo()};
        }
        if (!abrePar()) {
            throw new Exception("FATAL: Se esperaba abrir paréntesis"
                    + textoError());
        }

        Codigo cod1 = null;
        String id = Identificador();
        if (id == null) {
            errores.add(new ErrorTraductor("Info: Omitiendo. Se esperaba un Id"
                    + textoError()));
        } else if (!TablaSimbolos.existe(ts, id)) {
            errores.add(new ErrorTraductor("Info: variable " + id
                    + "no declarada" + textoError()));
        } else {
            int dir = TablaSimbolos.getDir(ts, id);
            Nat n = new Nat(dir);
            Tipos t = TablaSimbolos.getTipo(ts, id);
            switch (t) {
                case BOOL:
                    cod1 = new Codigo(new EntradaBool(n));
                    break;
                case ENTERO:
                    cod1 = new Codigo(new EntradaInt(n));
                    break;
                case CHAR:
                    cod1 = new Codigo(new EntradaChar(n));
                    break;
                case NATURAL:
                    cod1 = new Codigo(new EntradaNat(n));
                    break;
                case REAL:
                    cod1 = new Codigo(new EntradaFloat(n));
                    break;
            }
        }

        if (!cierraPar()) {
            throw new Exception("FATAL: Se esperaba cierra paréntesis"
                    + textoError());
        }
        return new Object[]{false, cod1};
    }

    protected Object[] InsEscritura() throws Exception {
        Codigo cod1 = null;
        if (!out()) {
            return new Object[]{true, new Codigo()};
        }
        if (!abrePar()) {
            throw new Exception("FATAL: Se esperaba abrir paréntesis"
                    + textoError());
        }

        Object[] resExpr = Expresion();
        Tipos tipo2 = (Tipos) resExpr[0];
        Codigo cod2 = (Codigo) resExpr[1];
        
        if (!cierraPar()) {
            throw new Exception("FATAL: Se esperaba cierra paréntesis"
                    + textoError());
        }

        if (tipo2 == Tipos.ERROR) {
            errores.add(new ErrorTraductor(
                    "Info: Se omite la salida: error en la expresión"
                    + textoError()));
        } else {
            cod1 = cod2;
            cod1.appendIns(new Salida());

        }
        return new Object[]{false, cod1};
    }

    // InsAsignación(out: error1,cod1) →
    protected Object[] InsAsignacion() throws Exception {
        boolean error1;
        Codigo cod1 = null;
        String id = Identificador();
        if (id == null) {
            return new Object[]{true, new Codigo()};
        }

        if (!dosPuntosIgual()) {
            throw new Exception("FATAL: Se esperaba :=" + textoError());
        }

        Object[] resExpr = Expresion(); // Expresión(out: tipo3,cod3,codJ3)
        Tipos tipo3 = (Tipos) resExpr[0];
        Codigo cod3 = (Codigo) resExpr[1];
        cod1 = cod3;
        
        if (!TablaSimbolos.existe(ts, id)) {// no fatal
            errores.add(new ErrorTraductor("Info: id " + id + " no declarado"
                    + textoError()));
            error1 = true;
        } else {
            Tipos tipoTs = TablaSimbolos.getTipo(ts, id);
            int dir = TablaSimbolos.getDir(ts, id);

            error1 = (tipo3 == Tipos.ERROR)
                    || (tipoTs == Tipos.REAL && (tipo3 == Tipos.CHAR || tipo3 == Tipos.BOOL))
                    || (tipoTs == Tipos.ENTERO && (tipo3 == Tipos.REAL
                    || tipo3 == Tipos.CHAR || tipo3 == Tipos.BOOL))
                    || (tipoTs == Tipos.NATURAL && tipo3 != Tipos.NATURAL)
                    || (tipoTs == Tipos.CHAR && tipo3 != Tipos.CHAR)
                    || (tipoTs == Tipos.BOOL && tipo3 != Tipos.BOOL);

            cod1.appendIns(new DesapilarDir(new Nat(dir)));
        }
        return new Object[]{error1, cod1};
    }

    protected Object[] ExpresionFact(Tipos tipo1h, Codigo codh1)
            throws Exception {
        Operaciones op2 = OpNiv0();
        Tipos tipo1 = null;
        Codigo cod1 = null;
        if (op2 == null)// lambda
        {
            return new Object[]{tipo1h, codh1};
        }

        // no lambda → OpNiv0(out: op2) ExpresiónNiv1(out: tipo3,cod3)
        Object[] resExprN1 = ExpresionNiv1();
        Tipos tipo3 = (Tipos) resExprN1[0];
        Codigo cod3 = (Codigo) resExprN1[1];

        if ((tipo1h == Tipos.ERROR || tipo3 == Tipos.ERROR)
                || (tipo1h == Tipos.CHAR && tipo3 != Tipos.CHAR)
                || (tipo1h != Tipos.CHAR && tipo3 == Tipos.CHAR)
                || (tipo1h == Tipos.BOOL && tipo3 != Tipos.BOOL)
                || (tipo1h != Tipos.BOOL && tipo3 == Tipos.BOOL)) {
            tipo1 = Tipos.ERROR;
        } else {
            tipo1 = Tipos.BOOL;
        }

        cod1 = codh1;
        
        switch (op2) {
            case MENOR:
                switch (tipo1h) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Menor());

                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastFloat());
                            cod1.appendIns(new Menor());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Menor());

                        } else if (tipo3 == Tipos.NATURAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastInt());
                            cod1.appendIns(new Menor());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Menor());
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Menor());
                        } else {
                            if (tipo3 == Tipos.ENTERO) {
                                cod1.appendIns(new CastInt());
                                cod1.appendCod(cod3);
                                cod1.appendIns(new Menor());
                            } else {
                                cod1.appendCod(cod3);
                                cod1.appendIns(new Menor());
                            }
                        }
                        break;
                    default:
                        cod1.appendCod(cod3);
                        cod1.appendIns(new Menor());
                }
                break;
            case MAYOR:
                switch (tipo1h) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Mayor());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastFloat());
                            cod1.appendIns(new Mayor());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Mayor());
                        } else {
                            if (tipo3 == Tipos.NATURAL) {
                                cod1.appendCod(cod3);
                                cod1.appendIns(new CastInt());
                                cod1.appendIns(new Mayor());

                            } else {
                                cod1.appendCod(cod3);
                                cod1.appendIns(new Mayor());
                            }
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Mayor());
                        } else {
                            if (tipo3 == Tipos.ENTERO) {
                                cod1.appendIns(new CastInt());
                                cod1.appendCod(cod3);
                                cod1.appendIns(new Mayor());
                            } else {
                                cod1.appendCod(cod3);
                                cod1.appendIns(new Mayor());
                            }
                        }
                        break;
                    default:
                        cod1.appendCod(cod3);
                        cod1.appendIns(new Mayor());
                }
                break;
            case MENORIG:
                switch (tipo1h) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MenorIg());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastFloat());
                            cod1.appendIns(new MenorIg());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MenorIg());
                        } else if (tipo3 == Tipos.NATURAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastInt());
                            cod1.appendIns(new MenorIg());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MenorIg());
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MenorIg());
                        } else if (tipo3 == Tipos.ENTERO) {
                            cod1.appendIns(new CastInt());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MenorIg());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MenorIg());
                        }
                        break;
                    default:
                        cod1.appendCod(cod3);
                        cod1.appendIns(new MenorIg());
                }
                break;
            case MAYORIG:
                switch (tipo1h) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MayorIg());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastFloat());
                            cod1.appendIns(new MayorIg());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MayorIg());
                        } else if (tipo3 == Tipos.NATURAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastInt());
                            cod1.appendIns(new MayorIg());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MayorIg());
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MayorIg());
                        } else if (tipo3 == Tipos.ENTERO) {
                            cod1.appendIns(new CastInt());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MayorIg());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new MayorIg());
                        }
                        break;
                    default:
                        cod1.appendCod(cod3);
                        cod1.appendIns(new MayorIg());
                }
                break;
            case IGUAL:
                switch (tipo1h) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Igual());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastFloat());
                            cod1.appendIns(new Igual());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Igual());
                        } else if (tipo3 == Tipos.NATURAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastInt());
                            cod1.appendIns(new Igual());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Igual());
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Igual());
                        } else if (tipo3 == Tipos.ENTERO) {
                            cod1.appendIns(new CastInt());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Igual());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new Igual());
                        }
                        break;
                    default:
                        cod1.appendCod(cod3);
                        cod1.appendIns(new Igual());
                }
                break;
            case DISTINTO:
                switch (tipo1h) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new NoIgual());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastFloat());
                            cod1.appendIns(new NoIgual());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new NoIgual());
                        } else if (tipo3 == Tipos.NATURAL) {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new CastInt());
                            cod1.appendIns(new NoIgual());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new NoIgual());
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            cod1.appendIns(new CastFloat());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new NoIgual());
                        } else if (tipo3 == Tipos.ENTERO) {
                            cod1.appendIns(new CastInt());
                            cod1.appendCod(cod3);
                            cod1.appendIns(new NoIgual());
                        } else {
                            cod1.appendCod(cod3);
                            cod1.appendIns(new NoIgual());
                        }
                        break;
                    default:
                        cod1.appendCod(cod3);
                        cod1.appendIns(new NoIgual());
                }
                break;
        }// fin switch principal
        return new Object[]{tipo1, cod1};
    }

    // ExpresiónNiv1Rec(in: tipoh1, codh1; out: tipo1, cod1)
    protected Object[] ExpresionNiv1Rec(Tipos tipoh1, Codigo codh1)
            throws Exception {
        Operaciones op2 = OpNiv1();
        Tipos tipo1 = null;
        Codigo cod1 = null;
        if (op2 == null) // lambda
        {
            return new Object[]{tipoh1, codh1};
        }

        // no lambda
        Object[] resExprN2 = ExpresionNiv2();
        Tipos tipo3 = (Tipos) resExprN2[0];
        Codigo cod3 = (Codigo) resExprN2[1];
        
        Tipos tipoh4 = null;
        Codigo codh4 = null;
        // tipoh4-----------------------
        if (tipoh1 == Tipos.ERROR || tipo3 == Tipos.ERROR
                || tipoh1 == Tipos.CHAR || tipo3 == Tipos.CHAR
                || (tipoh1 == Tipos.BOOL && tipo3 != Tipos.BOOL)
                || (tipoh1 != Tipos.BOOL && tipo3 == Tipos.BOOL)) {
            tipoh4 = Tipos.ERROR;
        } else {
            switch (op2) {
                case SUMA:
                case RESTA:
                    if (tipoh1 == Tipos.REAL || tipo3 == Tipos.REAL) {
                        tipoh4 = Tipos.REAL;
                    } else if (tipoh1 == Tipos.ENTERO || tipo3 == Tipos.ENTERO) {
                        tipoh4 = Tipos.ENTERO;
                    } else if (tipoh1 == Tipos.NATURAL && tipo3 == Tipos.NATURAL) {
                        tipoh4 = Tipos.NATURAL;
                    } else {
                        tipoh4 = Tipos.ERROR;
                    }
                    break;
                case OR:
                    if (tipoh1 == Tipos.BOOL && tipo3 == Tipos.BOOL) {
                        tipoh4 = Tipos.BOOL;
                    } else {
                        tipoh4 = Tipos.ERROR;
                    }
                    break;
            }
        }
        // tipoh4 fin ------------------------------------
        codh4 = codh1;
        
        switch (op2) {
            case SUMA:
                switch (tipoh1) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Suma());
                        } else {
                            codh4.appendCod(cod3);
                            codh4.appendIns(new CastFloat());
                            codh4.appendIns(new Suma());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendIns(new CastFloat());
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Suma());
                        } else {
                            if (tipo3 == Tipos.NATURAL) {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new CastInt());
                                codh4.appendIns(new Suma());
                            } else {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Suma());
                            }
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendIns(new CastFloat());
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Suma());
                        } else {
                            if (tipo3 == Tipos.ENTERO) {
                                codh4.appendIns(new CastInt());
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Suma());
                            } else {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Suma());
                            }
                        }
                        break;
                }
                break;
            case RESTA:
                switch (tipoh1) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Resta());
                        } else {
                            codh4.appendCod(cod3);
                            codh4.appendIns(new CastFloat());
                            codh4.appendIns(new Resta());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendIns(new CastFloat());
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Resta());
                        } else {
                            if (tipo3 == Tipos.NATURAL) {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new CastInt());
                                codh4.appendIns(new Resta());
                            } else {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Resta());
                            }
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendIns(new CastFloat());
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Resta());
                        } else {
                            if (tipo3 == Tipos.ENTERO) {
                                codh4.appendIns(new CastInt());
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Resta());
                            } else {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Resta());
                            }
                        }
                        break;
                }
                break;
            case OR:
                codh4.appendCod(cod3);
                codh4.appendIns(new O());
                break;
        }

        Object[] resExprNiv1Rec = ExpresionNiv1Rec(tipoh4, codh4);
        tipo1 = (Tipos) resExprNiv1Rec[0];
        cod1 = (Codigo) resExprNiv1Rec[1];
        return new Object[]{tipo1, cod1};

    }

    // ExpresiónNiv2Rec(in: tipoh1, codh1; out: tipo1, cod1)
    public Object[] ExpresionNiv2Rec(Tipos tipoh1, Codigo codh1)
            throws Exception {
        Operaciones op2 = OpNiv2();
        Tipos tipo1 = null;
        Codigo cod1 = null;
        if (op2 == null) {
            return new Object[]{tipoh1, codh1};
        }

        Object[] resExprN3 = ExpresionNiv3();
        Tipos tipo3 = (Tipos) resExprN3[0];
        Codigo cod3 = (Codigo) resExprN3[1];
        Tipos tipoh4 = null;
        // tipoh4----------------------
        if (tipoh1 == Tipos.ERROR || tipo3 == Tipos.ERROR
                || tipoh1 == Tipos.CHAR || tipo3 == Tipos.CHAR
                || (tipoh1 == Tipos.BOOL && tipo3 != Tipos.BOOL)
                || (tipoh1 != Tipos.BOOL && tipo3 == Tipos.BOOL)) {
            tipoh4 = Tipos.ERROR;
        } else {
            switch (op2) {
                case MULT:
                case DIV:
                    if (tipoh1 == Tipos.REAL || tipo3 == Tipos.REAL) {
                        tipoh4 = Tipos.REAL;
                    } else if (tipoh1 == Tipos.ENTERO || tipo3 == Tipos.ENTERO) {
                        tipoh4 = Tipos.ENTERO;
                    } else if (tipoh1 == Tipos.NATURAL && tipo3 == Tipos.NATURAL) {
                        tipoh4 = Tipos.NATURAL;
                    } else {
                        tipoh4 = Tipos.ERROR;
                    }
                    break;
                case MOD:
                    if (tipo3 == Tipos.NATURAL
                            && (tipoh1 == Tipos.NATURAL || tipoh1 == Tipos.ENTERO)) {
                        tipoh4 = tipoh1;
                    } else {
                        tipoh4 = Tipos.ERROR;
                    }
                case AND:
                    if (tipoh1 == Tipos.BOOL && tipo3 == Tipos.BOOL) {
                        tipoh4 = Tipos.BOOL;
                    } else {
                        tipoh4 = Tipos.ERROR;
                    }
                    break;
            }
        }
        // tiposh4 fin-------------------------
        Codigo codh4 = codh1;

        switch (op2) {
            case MULT:
                switch (tipoh1) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Multiplica());
                        } else {
                            codh4.appendCod(cod3);
                            codh4.appendIns(new CastFloat());
                            codh4.appendIns(new Multiplica());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendIns(new CastFloat());
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Multiplica());
                        } else {
                            if (tipo3 == Tipos.NATURAL) {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new CastInt());
                                codh4.appendIns(new Multiplica());
                            } else {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Multiplica());
                            }
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendIns(new CastFloat());
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Multiplica());
                        } else {
                            if (tipo3 == Tipos.ENTERO) {
                                codh4.appendIns(new CastInt());
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Multiplica());
                            } else {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Multiplica());
                            }
                        }
                        break;
                }
                break;
            case DIV:
                switch (tipoh1) {
                    case REAL:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Divide());
                        } else {
                            codh4.appendCod(cod3);
                            codh4.appendIns(new CastFloat());
                            codh4.appendIns(new Divide());
                        }
                        break;
                    case ENTERO:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendIns(new CastFloat());
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Divide());
                        } else {
                            if (tipo3 == Tipos.NATURAL) {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new CastInt());
                                codh4.appendIns(new Divide());
                            } else {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Divide());
                            }
                        }
                        break;
                    case NATURAL:
                        if (tipo3 == Tipos.REAL) {
                            codh4.appendIns(new CastFloat());
                            codh4.appendCod(cod3);
                            codh4.appendIns(new Divide());
                        } else {
                            if (tipo3 == Tipos.ENTERO) {
                                codh4.appendIns(new CastInt());
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Divide());
                            } else {
                                codh4.appendCod(cod3);
                                codh4.appendIns(new Divide());
                            }
                        }
                        break;
                }
                break;
            case MOD:
                codh4.appendCod(cod3);
                codh4.appendIns(new Modulo());
                break;
            case AND:
                codh4.appendCod(cod3);
                codh4.appendIns(new Y());
                break;
        }// fin switch principal

        Object[] resExprNiv2Rec = ExpresionNiv2Rec(tipoh4, codh4);
        tipo1 = (Tipos) resExprNiv2Rec[0];
        cod1 = (Codigo) resExprNiv2Rec[1];
        return new Object[]{tipo1, cod1};
    }

    // ExpresiónNiv3Fact(in: tipoh1, codh1; out: tipo1, cod1)
    public Object[] ExpresionNiv3Fact(Tipos tipoh1, Codigo codh1)
            throws Exception {
        Operaciones op2 = OpNiv3();
        Tipos tipo1 = null;
        Codigo cod1 = null;

        if (op2 == null)// lambda
        {
            return new Object[]{tipoh1, codh1};
        }

        Object[] resExprN3 = ExpresionNiv3();
        Tipos tipo3 = (Tipos) resExprN3[0];
        Codigo cod3 = (Codigo) resExprN3[1];

        if (tipoh1 == Tipos.ERROR || tipo3 == Tipos.ERROR
                || tipoh1 != Tipos.NATURAL || tipo3 != Tipos.NATURAL) {
            tipo1 = Tipos.ERROR;
        } else {
            tipo1 = Tipos.NATURAL;
        }

        cod1 = codh1;

        switch (op2) {
            case SHL:
                cod1.appendCod(cod3);
                cod1.appendIns(new Shl());
                break;
            case SHR:
                cod1.appendCod(cod3);
                cod1.appendIns(new Shr());
                break;
        }

        return new Object[]{tipo1, cod1};

    }

    protected Object[] ExpresionNiv4_conOp(Operaciones op2) throws Exception {
        Tipos tipo1 = null;
        Codigo cod1 = null;

        Object[] resExprNiv4 = ExpresionNiv4();
        Tipos tipo3 = (Tipos) resExprNiv4[0];
        Codigo cod3 = (Codigo) resExprNiv4[1];

        if (tipo3 == Tipos.ERROR) {
            tipo1 = Tipos.ERROR;
        } else {
            switch (op2) {
                case NOT:
                    if (tipo3 == Tipos.BOOL) {
                        tipo1 = Tipos.BOOL;
                    } else {
                        tipo1 = Tipos.ERROR;
                    }
                    break;
                case NEG:
                    if (tipo3 == Tipos.REAL) {
                        tipo1 = Tipos.REAL;
                    } else if (tipo3 == Tipos.ENTERO || tipo3 == Tipos.NATURAL) {
                        tipo1 = Tipos.ENTERO;
                    } else {
                        tipo1 = Tipos.ERROR;
                    }
                    break;
                case CASTREAL:
                    tipo1 = (tipo3 != Tipos.BOOL ? Tipos.REAL : Tipos.ERROR);
                    break;
                case CASTENT:
                    tipo1 = (tipo3 != Tipos.BOOL ? Tipos.ENTERO : Tipos.ERROR);
                    break;
                case CASTNAT:
                    tipo1 = (tipo3 == Tipos.NATURAL || tipo3 == Tipos.CHAR ? Tipos.NATURAL
                            : Tipos.ERROR);
                    break;
                case CASTCHAR:
                    tipo1 = (tipo3 == Tipos.NATURAL || tipo3 == Tipos.CHAR ? Tipos.CHAR
                            : Tipos.ERROR);
                    break;
            }
        }

        cod1 = cod3;

        switch (op2) {
            case NOT:
                cod1.appendIns(new No());

                break;
            case NEG:
                cod1.appendIns(new Menos());
                break;
            case CASTREAL:
                cod1.appendIns(new CastFloat());
                break;
            case CASTENT:
                cod1.appendIns(new CastInt());
                break;
            case CASTNAT:
                cod1.appendIns(new CastNat());
                break;
            case CASTCHAR:
                cod1.appendIns(new CastChar());
                break;
        }

        return new Object[]{tipo1, cod1};
    }

    // ExpresiónNiv4(out: tipo1, cod1)
    protected Object[] ExpresionNiv4_valorAbs() throws Exception {
        Tipos tipo1 = null;
        Codigo cod1 = null;

        Object[] resExprNiv4 = ExpresionNiv4();
        Tipos tipo2 = (Tipos) resExprNiv4[0];
        Codigo cod2 = (Codigo) resExprNiv4[1];

        if (!valorAbs()) {
            throw new Exception("Fatal: se esperaba la barra de valor absoluto"
                    + textoError());
        }

        if (tipo2 == Tipos.ERROR || tipo2 == Tipos.BOOL || tipo2 == Tipos.CHAR) {
            tipo1 = Tipos.ERROR;
        } else if (tipo2 == Tipos.REAL) {
            tipo1 = Tipos.REAL;
        } else if (tipo2 == Tipos.NATURAL || tipo2 == Tipos.ENTERO) {
            tipo1 = Tipos.NATURAL;
        } else {
            tipo1 = Tipos.ERROR;
        }

        cod1 = cod2;
        cod1.appendIns(new Abs());

        return new Object[]{tipo1, cod1};
    }

    // Literal(out: tipo1, cod1) → id
    protected Object[] Literal_Id(Token t) throws Exception {
        String id = t.getLex();
        if (!TablaSimbolos.existe(ts, id)) {
            errores.add(new ErrorTraductor("Info: identificador no declarado: "
                    + id + textoError()));
            return new Object[]{Tipos.ERROR, new Codigo()};
        }

        Tipos tipo1 = TablaSimbolos.getTipo(ts, t.getLex());
        int dir = TablaSimbolos.getDir(ts, t.getLex());
        Codigo cod1 = new Codigo(new ApilarDir(new Nat(dir)));
        return new Object[]{tipo1, cod1};
    }

    // Literal(out: tipo1, cod1) → LitTrue
    protected Object[] Literal_LitTrue() throws Exception {
        boolean valor = true;
        Apilar i = null;
        i = new Apilar(new Bool(valor));
        return new Object[]{Tipos.BOOL, new Codigo(i)};
    }

    // Literal(out: tipo1, cod1) → LitFalse
    protected Object[] Literal_LitFalse() throws Exception {
        boolean valor = false;
        Apilar i = null;
        i = new Apilar(new Bool(valor));
        return new Object[]{Tipos.BOOL, new Codigo(i)};
    }

    protected Object[] Literal_LitCha(Token t) throws Exception {
        char c = t.getLex().charAt(0);
        Apilar i = null;
        i = new Apilar(new Caracter(c));
        return new Object[]{Tipos.CHAR, new Codigo(i)};
    }

    // Literal(out: tipo1, cod1) → litNat
    protected Object[] Literal_LitNat(Token t) throws Exception {
        int valor = Integer.parseInt(t.getLex());
        Apilar i = null;
        i = new Apilar(new Nat(valor));
        return new Object[]{Tipos.NATURAL, new Codigo(i)};
    }
    protected Object[] Literal_LitFlo(Token t) throws Exception {
        float valor = Float.parseFloat(t.getLex());
        Apilar i = null;
        i = new Apilar(new Real(valor));
        return new Object[]{Tipos.REAL, new Codigo(i)};
    }
}
