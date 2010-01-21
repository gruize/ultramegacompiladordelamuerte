package compilador.traductor;

import java.util.ArrayList;

import pila.interprete.datos.Natural;
import pila.interprete.instrucciones.*;
import pila.interprete.datos.*;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.LectorExc;

import compilador.lexico.Identificador;
import compilador.lexico.Token;
import compilador.tablaSimbolos.TablaSimbolos;
import compilador.tablaSimbolos.InfoTs.Tipos;

public class TraductorCodP extends Traductor{



	public TraductorCodP(ArrayList<Token> tokens){
		super(tokens);
		errores=new ArrayList<ErrorTraductor>();
		i_token=0;
	}


	protected Object[] InsLectura() {
		Codigo codP1=null;
		boolean errorNoFatal=false;
		if (!in()) return new Object[]{true,codP1};
		
		if (!abrePar()){
			errores.add(new ErrorTraductor("Error instrucción lectura: no hay abrir paréntesis"));
			return new Object[]{true,codP1};
		}
		Token t=sigToken();
		if (!(t instanceof Identificador) || !TablaSimbolos.existe(ts, t.getLex())){
			errores.add(new ErrorTraductor("Error instrucción lectura: identificador "
											+t.getLex()+" no reconocido"));
			errorNoFatal=true; // no fatal
		}
	
		if (!errorNoFatal){
			try{
				Natural n=new Natural(TablaSimbolos.getDir(ts,t.getLex()));
				codP1=new Codigo(new Entrada(n));
			} catch (Exception e){}
		}

		if (!cierraPar()){
			errores.add(new ErrorTraductor("Error instrucción lectura: cierra paréntesis"));
			return new Object[]{true,codP1};
		}
		return new Object[]{false,codP1};
		
	}
	
	protected Object[] InsEscritura(){
		Codigo codP1=null;
		if (!out()) return new Object[]{true,codP1};
		
		if (!abrePar()){
			errores.add(new ErrorTraductor("Error instrucción escritura: no hay abrir paréntesis"));
			return new Object[]{true,codP1};
		}
		Object[] resExpr= Expresion();
		Tipos tipo2=(Tipos)resExpr[0];
		Codigo codP2=(Codigo)resExpr[1];
		if (tipo2==Tipos.ERROR){
			errores.add(new ErrorTraductor("Error instrucción escritura: error en expresión"));
			//error no fatal
		}
		if (!cierraPar()){
			errores.add(new ErrorTraductor("Error instrucción escritura: cierra paréntesis"));
			return new Object[]{true,codP1};
		}
		
		codP1 = codP2;
		try{
			codP1.appendIns(new Salida());
		}catch(Exception e){}
		return new Object[]{false,codP1};
	}
	
	//InsAsignación(out: error1,codP1) → 
	protected Object[] InsAsignacion(){
		boolean error1;
		Codigo codP1=null;
		Token id=sigToken();
		if (!(id instanceof Identificador)){
			i_token--;
			return new Object[]{true,codP1};
		}
		if (!dosPuntosIgual()){
			errores.add(new ErrorTraductor("Instruccion asignacion: falta el :="));
			return new Object[]{true,codP1};
		}
		Object[] resExpr=Expresion(); //Expresión(out: tipo3,codP3)
		Tipos tipo3=(Tipos)resExpr[0];
		Codigo codP3=(Codigo)resExpr[1];
		
		if (!TablaSimbolos.existe(ts, id.getLex())){//no fatal
			errores.add(new ErrorTraductor("No existe el id +"+id.getLex()));
		}
		Tipos tipoTs = TablaSimbolos.getTipo(ts, id.getLex());
		error1 = (tipo3 == Tipos.ERROR) ||
	      (tipoTs == Tipos.REAL && (tipo3 == Tipos.CHAR || tipo3 == Tipos.BOOL)) ||
	        (tipoTs == Tipos.ENTERO && (tipo3== Tipos.REAL || tipo3 == Tipos.CHAR ||  tipo3 == Tipos.BOOL))  ||
	        (tipoTs == Tipos.NATURAL && tipo3 != Tipos.NATURAL)  ||
	        (tipoTs ==Tipos.CHAR && tipo3 != Tipos.BOOL)  ||
	        (tipoTs == Tipos.BOOL && tipo3 != Tipos.BOOL);
		
		int dir= TablaSimbolos.getDir(ts, id.getLex());
		try{
		codP1 = codP3;
		codP1.appendIns(new DesapilarDir(new Natural(dir)));
		}catch (Exception e) {}
		return new Object[]{error1,codP1};
	}

	protected Object[] ExpresionFact(Tipos tipo1h,Codigo codPh1){
		Operaciones op2=OpNiv0();
		Tipos tipo1=null;
		Codigo codP1=null;
		if (op2==null) {//lambda
			return new Object[]{tipo1h,codPh1};
		}
		else {//→ OpNiv0(out: op2) ExpresiónNiv1(out: tipo3,codP3)
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
			try{
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
			}
			}catch(Exception e){}
			return new Object[]{tipo1,codP1};
		}
		
	}
	
	
	//ExpresiónNiv1Rec(in: tipoh1, codh1; out: tipo1, cod1)
	protected Object[] ExpresionNiv1Rec(Tipos tipoh1,Codigo codPh1){
		Operaciones op2=OpNiv1();
		Tipos tipo1=null;
		Codigo codP1=null;
		Tipos tipoh4=null;
		Codigo codPh4=null;
		if (op2==null) {//lambda
			return new Object[]{tipoh1,codPh1};
		}
		else {//no lambda
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
			try{
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
			}catch (Exception e){}
			Object[] resExprNiv1Rec=ExpresionNiv1Rec(tipoh4, codPh4);
			tipo1=(Tipos)resExprNiv1Rec[0];
			codP1=(Codigo)resExprNiv1Rec[1];
			return new Object[]{tipo1,codP1};
		}
	}
	
	//ExpresiónNiv2Rec(in: tipoh1, codh1; out: tipo1, codP1)
	public Object[] ExpresionNiv2Rec(Tipos tipoh1, Codigo codPh1){
		Operaciones op2=OpNiv2();
		Tipos tipo1=null;
		Codigo codP1=null;
		if (op2==null){
			tipo1=tipoh1;
			codP1=codPh1;
			return new Object[]{tipo1,codP1};
		}
		else{
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
			
			try{
			switch (op2){
				case MULT:
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
				case DIV:
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
				case MOD:
					codPh4.appendCod(codP3);
					codPh4.appendIns(new Modulo());
					break;
				case AND:
					codPh4.appendCod(codP3);
					codPh4.appendIns(new O());
					break;
			}
			}catch (Exception e){}
			Object[] resExprNiv2Rec=ExpresionNiv2Rec(tipoh4, codPh4);
			tipo1=(Tipos)resExprNiv2Rec[0];
			codP1=(Codigo)resExprNiv2Rec[1];
			return new Object[]{tipo1,codP1};
		}
	}

	//ExpresiónNiv3Fact(in: tipoh1, codPh1; out: tipo1, codP1)
	public Object[] ExpresionNiv3Fact(Tipos tipoh1, Codigo codPh1){
		Operaciones op2=OpNiv3();
		Tipos tipo1=null;
		Codigo codP1=null;
		
		if (op2==null){//lambda
			tipo1=tipoh1;
			codP1=codPh1;
			return new Object[]{tipo1,codP1};
		}
		else {
			Object[] resExprN3=ExpresionNiv3();
			Tipos tipo3= (Tipos)resExprN3[0];
			Codigo codP3= (Codigo)resExprN3[1];
			if (tipoh1 == Tipos.ERROR || tipo3==Tipos.ERROR
					|| tipoh1 != Tipos.NATURAL || tipo3 != Tipos.NATURAL)
				tipo1=Tipos.ERROR;
			else tipo1=Tipos.NATURAL;
			
			
			codP1 = codPh1;
			try{
			switch (op2){
			case SHL:
				codP1.appendCod(codP3);
				codP1.appendIns(new Shl());
				break;
			case SHR:
				codP1.appendCod(codP3);
				codP1.appendIns(new Shr());
				break;
			}}
			catch(Exception e){}
			return new Object[]{tipo1,codP1};
		}
		
	}
	
	protected Object[] ExpresionNiv4_conOp(Operaciones op2){
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
		try{
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
		}catch (Exception e){}
		return new Object[]{tipo1,codP1};
	}

	
	//ExpresiónNiv4(out: tipo1, codP1)
	protected Object[] ExpresionNiv4_valorAbs(){
		Tipos tipo1=null;
		Codigo codP1=null;
		Object[] resExprNiv4=ExpresionNiv4();
		Tipos tipo2 = (Tipos)resExprNiv4[0];
		Codigo codP2 = (Codigo)resExprNiv4[1];
		
		if (valorAbs()){
			errores.add(new ErrorTraductor("FATAL: Expresion nivel4: no se cierra el valor abs"));
			tipo1=Tipos.ERROR;
			return new Object[]{tipo1,codP2};
		}
		
		if (tipo2 == Tipos.ERROR || tipo2 == Tipos.BOOL || tipo2==Tipos.CHAR)
			tipo1=Tipos.ERROR;
		else if (tipo2 == Tipos.REAL)
			tipo1=Tipos.REAL;
		else if (tipo2 == Tipos.NATURAL || tipo2 == Tipos.ENTERO)
			tipo1= Tipos.NATURAL;
		else tipo1=Tipos.ERROR;		
		
		codP1=codP2;
		try{codP1.appendIns(new Abs());}catch(Exception e){}
		return new Object[]{tipo1,codP1};
	}


	//Literal(out: tipo1, codP1) → id
	protected Object[] Literal_Id(Token t) {
		if (!TablaSimbolos.existe(ts, t.getLex())){
			errores.add(new ErrorTraductor("Identificador no declarado: "+t.getLex()));
			return new Object[]{Tipos.ERROR,new Codigo()};
		}
		Tipos tipo1 = TablaSimbolos.getTipo(ts, t.getLex());
		int dir= TablaSimbolos.getDir(ts, t.getLex());
		Codigo codP1=null;
		try {
			codP1 = new Codigo(new ApilarDir(new Entero(dir)));
		} catch (LectorExc e) {}	
		
		return new Object[]{tipo1,codP1};
	}
	//Literal(out: tipo1, codP1, codJ1) → litBoo
	protected Object[] Literal_LitBoo(Token t) {
		boolean valor;
		if (t.getLex().equals("true")) valor=true;
		else valor=false;
		Apilar i=null;
		try {
			i = new Apilar(new Booleano(valor));
		} catch (Exception e) {}
		return new Object[]{Tipos.BOOL,new Codigo(i)};
	}

	protected Object[] Literal_LitCha(Token t) {
		Apilar i=null;
		try {
			i=new Apilar(new Caracter(t.getLex().charAt(1)));
		} catch (Exception e) {}
		return new Object[]{Tipos.CHAR,new Codigo(i)};
	}

	//Literal(out: tipo1, codP1) → litNat
	protected Object[] Literal_LitNat(Token t) {
		Apilar i=null;
		try {
			i=new Apilar(new Natural(Integer.parseInt(t.getLex())));
		} catch (Exception e) {}
		return new Object[]{Tipos.NATURAL,new Codigo(i)};
	}

	//Literal(out: tipo1, codP1, codJ1) → litFlo
	protected Object[] Literal_LitFlo(Token t) {
		Apilar i=null;
		try {
			i=new Apilar(new Real(Float.parseFloat(t.getLex())));
		} catch (Exception e) {}
		return new Object[]{Tipos.REAL,new Codigo(i)};
	}

}