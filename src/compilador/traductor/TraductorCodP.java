package compilador.traductor;

import java.util.ArrayList;

import pila.interprete.datos.Booleano;
import pila.interprete.datos.Caracter;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Natural;
import pila.interprete.datos.Real;
import pila.interprete.instrucciones.*;

import compilador.lexico.Tokens.Token;
import compilador.tablaSimbolos.TablaSimbolos;
import compilador.tablaSimbolos.InfoTs.Tipos;

public class TraductorCodP extends Traductor{



	public TraductorCodP(ArrayList<Token> tokens){
		super(tokens);
	}

	//InsLectura(out:error1,codP1,codJ1) →  
	protected Object[] InsLectura() throws Exception{
		Codigo codP1=new Codigo();
		if (!in()) return new Object[]{true,codP1};
		if (!abrePar()) throw new Exception("FATAL: Se esperaba abrir paréntesis"+textoError());

		String id=Identificador();
		if (id==null) {
			errores.add(new ErrorTraductor("Info: Omitiendo. Se esperaba un Id"+textoError()));
		}
		else if (!TablaSimbolos.existe(ts, id)){
			errores.add(new ErrorTraductor("Info: variable "+id+"no declarada"+textoError()));
		}
		else {
			Natural n=new Natural(TablaSimbolos.getDir(ts, id));
			Tipos t=TablaSimbolos.getTipo(ts, id);
			switch (t){
			case BOOL: codP1=new Codigo(new EntradaBool(n));
				break;
			case ENTERO: codP1=new Codigo(new EntradaInt(n));
				break;
			case CHAR: codP1=new Codigo(new EntradaChar(n));
				break;
			case NATURAL: codP1=new Codigo(new EntradaNat(n));
				break;
			case REAL: codP1=new Codigo(new EntradaFloat(n));
				break;
			}
		}
		
		if (!cierraPar()) throw new Exception("FATAL: Se esperaba cierra paréntesis"+textoError());
		return new Object[]{false,codP1};
		
	}
	
	protected Object[] InsEscritura() throws Exception{
		Codigo codP1=new Codigo();
		if (!out()) return new Object[]{true,codP1};
		if (!abrePar()) throw new Exception("FATAL: Se esperaba abrir paréntesis"+textoError());
		
		Object[] resExpr= Expresion();
		Tipos tipo2=(Tipos)resExpr[0];
		Codigo codP2=(Codigo)resExpr[1];
		
		if (!cierraPar()) throw new Exception("FATAL: Se esperaba cierra paréntesis"+textoError());
		
		if (tipo2!=Tipos.ERROR)
			errores.add(new ErrorTraductor("Info: Se omite la salida: error en la expresión"+textoError()));
		else {
			codP1 = codP2;
			codP1.appendIns(new Salida());
		}
		return new Object[]{false,codP1};
	}
	
	//InsAsignación(out: error1,codP1) → 
	protected Object[] InsAsignacion() throws Exception{
		boolean error1;
		Codigo codP1=new Codigo();
		String id=Identificador();
		if (id==null) return new Object[]{true,codP1};

		if (!dosPuntosIgual()) throw new Exception("FATAL: Se esperaba :="+textoError());
		
		Object[] resExpr=Expresion(); //Expresión(out: tipo3,codP3)
		Tipos tipo3=(Tipos)resExpr[0];
		Codigo codP3=(Codigo)resExpr[1];
		codP1 = codP3;
		
		if (!TablaSimbolos.existe(ts, id)){//no fatal
			errores.add(new ErrorTraductor("Info: id "+id+" no declarado"+textoError()));
			error1=true;
		}
		else {
			Tipos tipoTs = TablaSimbolos.getTipo(ts, id);
			int dir= TablaSimbolos.getDir(ts, id);
			
			error1 = (tipo3 == Tipos.ERROR) ||
	   	   		(tipoTs == Tipos.REAL && (tipo3 == Tipos.CHAR || tipo3 == Tipos.BOOL)) ||
	   	   		(tipoTs == Tipos.ENTERO && (tipo3== Tipos.REAL || tipo3 == Tipos.CHAR ||  tipo3 == Tipos.BOOL))  ||
	   	   		(tipoTs == Tipos.NATURAL && tipo3 != Tipos.NATURAL)  ||
	   	   		(tipoTs ==Tipos.CHAR && tipo3 != Tipos.BOOL)  ||
	   	   		(tipoTs == Tipos.BOOL && tipo3 != Tipos.BOOL);

			codP1.appendIns(new DesapilarDir(new Natural(dir)));
		}
		return new Object[]{error1,codP1};
	}

	protected Object[] ExpresionFact(Tipos tipo1h,Codigo codPh1) throws Exception{
		Operaciones op2=OpNiv0();
		Tipos tipo1=null;
		Codigo codP1=null;
		if (op2==null)//lambda
			return new Object[]{tipo1h,codPh1};

		//no lambda → OpNiv0(out: op2) ExpresiónNiv1(out: tipo3,codP3)
		Object[] resExprN1=ExpresionNiv1();
		Tipos tipo3=(Tipos)resExprN1[0];
		Codigo codP3=(Codigo)resExprN1[1];

		if ( (tipo1h == Tipos.ERROR || tipo3 == Tipos.ERROR) ||
				( tipo1h == Tipos.CHAR && tipo3 != Tipos.CHAR) ||
				( tipo1h != Tipos.CHAR && tipo3 == Tipos.CHAR) ||
				(tipo1h == Tipos.BOOL && tipo3 != Tipos.BOOL) ||
				( tipo1h != Tipos.BOOL && tipo3 == Tipos.BOOL))
			tipo1= Tipos.ERROR;
		else tipo1=Tipos.BOOL;

		codP1=codPh1;

		switch (op2){
		case MENOR:
			switch (tipo1h){
			case REAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new Menor());
				}
				else{
					codP1.appendCod(codP3);
					codP1.appendIns(new CastFloat());
					codP1.appendIns(new Menor());
				}
				break;
			case ENTERO:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new Menor());
				}
				else if (tipo3 == Tipos.NATURAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new CastInt());
					codP1.appendIns(new Menor());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new Menor());
				}
				break;
			case NATURAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new Menor());
				}
				else if (tipo3 == Tipos.ENTERO){
					codP1.appendIns(new CastInt());
					codP1.appendCod(codP3);	
					codP1.appendIns(new Menor());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new Menor());
				}
				break;
			default:
				codP1.appendCod(codP3);
				codP1.appendIns(new Menor());
			}
			break;
		case MAYOR:
			switch (tipo1h){
			case REAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new Mayor());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new CastFloat());
					codP1.appendIns(new Mayor());
				}
				break;
			case ENTERO:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new Mayor());
				}
				else if (tipo3 == Tipos.NATURAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new CastInt());
					codP1.appendIns(new Mayor());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new Mayor());
				}
				break;
			case NATURAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new Mayor());
				}
				else if (tipo3 == Tipos.ENTERO){
					codP1.appendIns(new CastInt());
					codP1.appendCod(codP3);	
					codP1.appendIns(new Mayor());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new Mayor());
				}
				break;
			default:
				codP1.appendCod(codP3);
				codP1.appendIns(new Mayor());
			}
			break;
		case MENORIG:
			switch (tipo1h){
			case REAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new MenorIg());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new CastFloat());
					codP1.appendIns(new MenorIg());
				}
				break;
			case ENTERO:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new MenorIg());
				}
				else if (tipo3 == Tipos.NATURAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new CastInt());
					codP1.appendIns(new MenorIg());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new MenorIg());
				}
				break;
			case NATURAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new MenorIg());
				}
				else if (tipo3 == Tipos.ENTERO){
					codP1.appendIns(new CastInt());
					codP1.appendCod(codP3);	
					codP1.appendIns(new MenorIg());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new MenorIg());
				}
				break;
			default:
				codP1.appendCod(codP3);
				codP1.appendIns(new MenorIg());
			}
			break;
		case MAYORIG:
			switch (tipo1h){
			case REAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new MayorIg());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new CastFloat());
					codP1.appendIns(new MayorIg());
				}
				break;
			case ENTERO:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new MayorIg());
				}
				else if (tipo3 == Tipos.NATURAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new CastInt());
					codP1.appendIns(new MayorIg());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new MayorIg());
				}
				break;
			case NATURAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new MayorIg());
				}
				else if (tipo3 == Tipos.ENTERO){
					codP1.appendIns(new CastInt());
					codP1.appendCod(codP3);	
					codP1.appendIns(new MayorIg());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new MayorIg());
				}
				break;
			default:
				codP1.appendCod(codP3);
				codP1.appendIns(new MayorIg());
			}
			break;
		case IGUAL:
			switch (tipo1h){
			case REAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new Igual());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new CastFloat());
					codP1.appendIns(new Igual());
				}
				break;
			case ENTERO:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new Igual());
				}
				else if (tipo3 == Tipos.NATURAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new CastInt());
					codP1.appendIns(new Igual());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new Igual());
				}
				break;
			case NATURAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new Igual());
				}
				else if (tipo3 == Tipos.ENTERO){
					codP1.appendIns(new CastInt());
					codP1.appendCod(codP3);	
					codP1.appendIns(new Igual());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new Igual());
				}
				break;
			default:
				codP1.appendCod(codP3);
				codP1.appendIns(new Igual());
			}
			break;
		case DISTINTO:
			switch (tipo1h){
			case REAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new NoIgual());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new CastFloat());
					codP1.appendIns(new NoIgual());
				}
				break;
			case ENTERO:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new NoIgual());
				}
				else if (tipo3 == Tipos.NATURAL){
					codP1.appendCod(codP3);
					codP1.appendIns(new CastInt());
					codP1.appendIns(new NoIgual());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new NoIgual());
				}
				break;
			case NATURAL:
				if (tipo3 == Tipos.REAL){
					codP1.appendIns(new CastFloat());
					codP1.appendCod(codP3);
					codP1.appendIns(new NoIgual());
				}
				else if (tipo3 == Tipos.ENTERO){
					codP1.appendIns(new CastInt());
					codP1.appendCod(codP3);	
					codP1.appendIns(new NoIgual());
				}
				else {
					codP1.appendCod(codP3);
					codP1.appendIns(new NoIgual());
				}
				break;
			default:
				codP1.appendCod(codP3);
				codP1.appendIns(new NoIgual());
			}
			break;
		}//fin switch principal
		return new Object[]{tipo1,codP1};
	}
	
	
	//ExpresiónNiv1Rec(in: tipoh1, codh1; out: tipo1, cod1)
	protected Object[] ExpresionNiv1Rec(Tipos tipoh1,Codigo codPh1) throws Exception{
		Operaciones op2=OpNiv1();
		Tipos tipo1=null;
		Codigo codP1=null;
		Tipos tipoh4=null;
		Codigo codPh4=null;
		if (op2==null) //lambda
			return new Object[]{tipoh1,codPh1};
		
		//no lambda
		Object[] resExprN2=ExpresionNiv2();
		Tipos tipo3 = (Tipos)resExprN2[0];
		Codigo codP3 = (Codigo)resExprN2[1];

		//tipoh4-----------------------
		if (tipoh1 == Tipos.ERROR || tipo3 == Tipos.ERROR  
				|| tipoh1 == Tipos.CHAR || tipo3 == Tipos.CHAR
				|| (tipoh1 == Tipos.BOOL && tipo3 != Tipos.BOOL)
				|| (tipoh1 != Tipos.BOOL && tipo3 ==Tipos.BOOL))
			tipoh4=Tipos.ERROR;
		else {
			switch (op2){
			case SUMA:
			case RESTA:
				if (tipoh1==Tipos.REAL || tipo3==Tipos.REAL)
					tipoh4=Tipos.REAL;
				else if (tipoh1==Tipos.ENTERO || tipo3==Tipos.ENTERO)
					tipoh4=Tipos.ENTERO;
				else if (tipoh1==Tipos.NATURAL && tipo3==Tipos.NATURAL)
					tipoh4=Tipos.NATURAL;
				else tipoh4=Tipos.ERROR;
				break;
			case OR:
				if (tipoh1 == Tipos.BOOL && tipo3==Tipos.BOOL)
					tipoh4=Tipos.BOOL;
				else tipoh4=Tipos.ERROR;
				break;
			}
		}
		//tipoh4 fin ------------------------------------
		codPh4=codPh1;

		switch (op2){
		case SUMA:
			switch (tipoh1){
			case REAL:
				if (tipo3==Tipos.REAL){
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Suma());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new CastFloat());
					codPh4.appendIns(new Suma());
				}
				break;
			case ENTERO:
				if (tipo3==Tipos.REAL){
					codPh4.appendIns(new CastFloat());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Suma());
				}
				else if (tipo3 == Tipos.NATURAL){
					codPh4.appendCod(codP3);
					codPh4.appendIns(new CastInt());
					codPh4.appendIns(new Suma());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Suma());
				}
				break;
			case NATURAL:
				if (tipo3== Tipos.REAL){
					codPh4.appendIns(new CastFloat());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Suma());
				}
				else if (tipo3 == Tipos.ENTERO){
					codPh4.appendIns(new CastInt());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Suma());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Suma());
				}
				break;
			}
			break;
		case RESTA:
			switch (tipoh1){
			case REAL:
				if (tipo3==Tipos.REAL){
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Resta());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new CastFloat());
					codPh4.appendIns(new Resta());
				}
				break;
			case ENTERO:
				if (tipo3==Tipos.REAL){
					codPh4.appendIns(new CastFloat());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Resta());
				}
				else if (tipo3 == Tipos.NATURAL){
					codPh4.appendCod(codP3);
					codPh4.appendIns(new CastInt());
					codPh4.appendIns(new Resta());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Resta());
				}
				break;
			case NATURAL:
				if (tipo3== Tipos.REAL){
					codPh4.appendIns(new CastFloat());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Resta());
				}
				else if (tipo3 == Tipos.ENTERO){
					codPh4.appendIns(new CastInt());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Resta());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Resta());
				}
				break;
			}
			break;
		case OR:
			codPh4.appendCod(codP3);
			codPh4.appendIns(new O());
			break;
		}

		Object[] resExprNiv1Rec=ExpresionNiv1Rec(tipoh4, codPh4);
		tipo1=(Tipos)resExprNiv1Rec[0];
		codP1=(Codigo)resExprNiv1Rec[1];
		return new Object[]{tipo1,codP1};

	}
	
	//ExpresiónNiv2Rec(in: tipoh1, codh1; out: tipo1, codP1)
	public Object[] ExpresionNiv2Rec(Tipos tipoh1, Codigo codPh1) throws Exception{
		Operaciones op2=OpNiv2();
		Tipos tipo1=null;
		Codigo codP1=null;
		if (op2==null)
			return new Object[]{tipoh1,codPh1};
		
		Object[] resExprN3=ExpresionNiv3();
		Tipos tipo3=(Tipos) resExprN3[0];
		Codigo codP3=(Codigo)resExprN3[1];
		Tipos tipoh4=null;
		//tipoh4----------------------
		if (tipoh1 == Tipos.ERROR || tipo3 == Tipos.ERROR  
				|| tipoh1 == Tipos.CHAR || tipo3 == Tipos.CHAR
				|| (tipoh1 == Tipos.BOOL && tipo3 != Tipos.BOOL)
				|| (tipoh1 != Tipos.BOOL && tipo3 ==Tipos.BOOL))
			tipoh4=Tipos.ERROR;
		else {
			switch (op2){
			case MULT:
			case DIV:
				if (tipoh1==Tipos.REAL || tipo3==Tipos.REAL)
					tipoh4=Tipos.REAL;
				else if (tipoh1==Tipos.ENTERO || tipo3==Tipos.ENTERO)
					tipoh4=Tipos.ENTERO;
				else if (tipoh1==Tipos.NATURAL && tipo3==Tipos.NATURAL)
					tipoh4=Tipos.NATURAL;
				else tipoh4=Tipos.ERROR;
				break;
			case MOD:
				if (tipo3 == Tipos.NATURAL && 
						(tipoh1==Tipos.NATURAL || tipoh1==Tipos.ENTERO))
					tipoh4=tipoh1;
				else tipoh4=Tipos.ERROR;
			case AND:
				if (tipoh1 == Tipos.BOOL && tipo3==Tipos.BOOL)
					tipoh4=Tipos.BOOL;
				else tipoh4=Tipos.ERROR;
				break;
			}
		}
		//tiposh4 fin-------------------------
		Codigo codPh4=codPh1;

		switch (op2){
		case MULT:
			switch (tipoh1){
			case REAL:
				if (tipo3==Tipos.REAL){
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Multiplica());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new CastFloat());
					codPh4.appendIns(new Multiplica());
				}
				break;
			case ENTERO:
				if (tipo3==Tipos.REAL){
					codPh4.appendIns(new CastFloat());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Multiplica());
				}
				else if (tipo3 == Tipos.NATURAL){
					codPh4.appendCod(codP3);
					codPh4.appendIns(new CastInt());
					codPh4.appendIns(new Multiplica());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Multiplica());
				}
				break;
			case NATURAL:
				if (tipo3== Tipos.REAL){
					codPh4.appendIns(new CastFloat());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Multiplica());
				}
				else if (tipo3 == Tipos.ENTERO){
					codPh4.appendIns(new CastInt());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Multiplica());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Multiplica());
				}
				break;
			}
			break;
		case DIV:
			switch (tipoh1){
			case REAL:
				if (tipo3==Tipos.REAL){
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Divide());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new CastFloat());
					codPh4.appendIns(new Divide());
				}
				break;
			case ENTERO:
				if (tipo3==Tipos.REAL){
					codPh4.appendIns(new CastFloat());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Divide());
				}
				else if (tipo3 == Tipos.NATURAL){
					codPh4.appendCod(codP3);
					codPh4.appendIns(new CastInt());
					codPh4.appendIns(new Divide());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Divide());
				}
				break;
			case NATURAL:
				if (tipo3== Tipos.REAL){
					codPh4.appendIns(new CastFloat());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Divide());
				}
				else if (tipo3 == Tipos.ENTERO){
					codPh4.appendIns(new CastInt());
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Divide());
				}
				else {
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Divide());
				}
				break;
			}
			break;
		case MOD:
			codPh4.appendCod(codP3);
			codPh4.appendIns(new Modulo());
			break;
		case AND:
			codPh4.appendCod(codP3);
			codPh4.appendIns(new Y());
			break;
		}//fin switch principal

		Object[] resExprNiv2Rec=ExpresionNiv2Rec(tipoh4, codPh4);
		tipo1=(Tipos)resExprNiv2Rec[0];
		codP1=(Codigo)resExprNiv2Rec[1];
		return new Object[]{tipo1,codP1};
	}

	//ExpresiónNiv3Fact(in: tipoh1, codPh1; out: tipo1, codP1)
	public Object[] ExpresionNiv3Fact(Tipos tipoh1, Codigo codPh1) throws Exception{
		Operaciones op2=OpNiv3();
		Tipos tipo1=null;
		Codigo codP1=null;

		if (op2==null)//lambda
			return new Object[]{tipoh1,codPh1};

		Object[] resExprN3=ExpresionNiv3();
		Tipos tipo3= (Tipos)resExprN3[0];
		Codigo codP3= (Codigo)resExprN3[1];

		if (tipoh1 == Tipos.ERROR || tipo3==Tipos.ERROR
				|| tipoh1 != Tipos.NATURAL || tipo3 != Tipos.NATURAL)
			tipo1=Tipos.ERROR;
		else tipo1=Tipos.NATURAL;


		codP1 = codPh1;

		switch (op2){
		case SHL:
			codP1.appendCod(codP3);
			codP1.appendIns(new Shl());
			break;
		case SHR:
			codP1.appendCod(codP3);
			codP1.appendIns(new Shr());
			break;
		}

		return new Object[]{tipo1,codP1};


	}
	
	protected Object[] ExpresionNiv4_conOp(Operaciones op2) throws Exception{
		Tipos tipo1=null;
		Codigo codP1=null;
		Object[] resExprNiv4=ExpresionNiv4();
		Tipos tipo3 = (Tipos)resExprNiv4[0];
		Codigo codP3 = (Codigo)resExprNiv4[1];
		
		if (tipo3 == Tipos.ERROR)
			tipo1=Tipos.ERROR;
		else{
			switch (op2){
			case NOT:
				if (tipo3 == Tipos.BOOL) tipo1=Tipos.BOOL;
				else tipo1=Tipos.ERROR;
				break;
			case NEG:
				if (tipo3==Tipos.REAL)
					tipo1=Tipos.REAL;
				else if (tipo3==Tipos.ENTERO || tipo3==Tipos.NATURAL)
					tipo1=Tipos.ENTERO;
				else tipo1=Tipos.ERROR;
				break;
			case CASTREAL:
				tipo1=(tipo3!=Tipos.BOOL?Tipos.REAL:Tipos.ERROR);
				break;
			case CASTENT:
				tipo1=(tipo3!=Tipos.BOOL?Tipos.ENTERO:Tipos.ERROR);
				break;
			case CASTNAT:
				tipo1=(tipo3==Tipos.NATURAL || tipo3==Tipos.CHAR?Tipos.NATURAL:Tipos.ERROR);
				break;
			case CASTCHAR:
				tipo1=(tipo3==Tipos.NATURAL || tipo3==Tipos.CHAR?Tipos.CHAR:Tipos.ERROR);
				break;
			}
		}
		
		codP1=codP3;
		
		switch (op2){
		case NOT:
			codP1.appendIns(new No());
			break;
		case NEG:
			codP1.appendIns(new Menos());
			break;
		case CASTREAL:
			codP1.appendIns(new CastFloat());
			break;
		case CASTENT:
			codP1.appendIns(new CastInt());
			break;
		case CASTNAT:
			codP1.appendIns(new CastNat());
			break;
		case CASTCHAR:
			codP1.appendIns(new CastChar());
			break;
		}

		return new Object[]{tipo1,codP1};
	}

	
	//ExpresiónNiv4(out: tipo1, codP1)
	protected Object[] ExpresionNiv4_valorAbs() throws Exception{
		Tipos tipo1=null;
		Codigo codP1=null;
		Object[] resExprNiv4=ExpresionNiv4();
		Tipos tipo2 = (Tipos)resExprNiv4[0];
		Codigo codP2 = (Codigo)resExprNiv4[1];
		
		if (!valorAbs()) throw new Exception("Fatal: se esperaba la barra de valor absoluto"+textoError());
		
		if (tipo2 == Tipos.ERROR || tipo2 == Tipos.BOOL || tipo2==Tipos.CHAR)
			tipo1=Tipos.ERROR;
		else if (tipo2 == Tipos.REAL)
			tipo1=Tipos.REAL;
		else if (tipo2 == Tipos.NATURAL || tipo2 == Tipos.ENTERO)
			tipo1= Tipos.NATURAL;
		else tipo1=Tipos.ERROR;		
		
		codP1=codP2;
		codP1.appendIns(new Abs());
		return new Object[]{tipo1,codP1};
	}


	//Literal(out: tipo1, codP1) → id
	protected Object[] Literal_Id(Token t)throws Exception {
		String id=t.getLex();	
		if (!TablaSimbolos.existe(ts,id)){
			errores.add(new ErrorTraductor("Info: identificador no declarado: "+id+textoError()));
			return new Object[]{Tipos.ERROR,new Codigo()};
		}
		
		Tipos tipo1 = TablaSimbolos.getTipo(ts, t.getLex());
		int dir= TablaSimbolos.getDir(ts, t.getLex());
		Codigo codP1 = new Codigo(new ApilarDir(new Natural(dir)));
		return new Object[]{tipo1,codP1};
	}
	//Literal(out: tipo1, codP1, codJ1) → LitTrue
	protected Object[] Literal_LitTrue() throws Exception{
		boolean valor=true;
		Apilar i=null;
		i = new Apilar(new Booleano(valor));
		return new Object[]{Tipos.BOOL,new Codigo(i)};
	}

	//Literal(out: tipo1, codP1, codJ1) → LitFalse
	protected Object[] Literal_LitFalse() throws Exception {
		boolean valor=false;
		Apilar i=null;
		i = new Apilar(new Booleano(valor));
		return new Object[]{Tipos.BOOL,new Codigo(i)};
	}
	
	protected Object[] Literal_LitCha(Token t) throws Exception {
		Apilar i=null;
		i=new Apilar(new Caracter(t.getLex().charAt(1)));
		return new Object[]{Tipos.CHAR,new Codigo(i)};
	}

	//Literal(out: tipo1, codP1) → litNat
	protected Object[] Literal_LitNat(Token t) throws Exception {
		Apilar i=null;
		i=new Apilar(new Natural(Integer.parseInt(t.getLex())));
		return new Object[]{Tipos.NATURAL,new Codigo(i)};
	}

	//Literal(out: tipo1, codP1, codJ1) → litFlo
	protected Object[] Literal_LitFlo(Token t) throws Exception{
		Apilar i=null;
		i=new Apilar(new Real(Float.parseFloat(t.getLex())));
		return new Object[]{Tipos.REAL,new Codigo(i)};
	}

}