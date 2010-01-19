package compilador.traductor;

import java.util.ArrayList;

import compilador.lexico.*;
import compilador.tablaSimbolos.TablaSimbolos;

public class TraductorCodP extends Traductor{


	ArrayList<ErrorTraductor> errores;
	int i_token;
	
	public TraductorCodP(ArrayList<Token> tokens){
		super(tokens);
		errores=new ArrayList<ErrorTraductor>();
		i_token=0;
	}
	
	//Programa(out: error1, codP1, codJ1)
	private Object[] Programa(){
		boolean errorDec=Declaraciones();
		boolean errorAmp=ampersand();
		Object[] resInst= Instrucciones();

		return new Object[]{errorDec
				||errorAmp
				||(Boolean)resInst[0],
				resInst[1]};

	}

	//Declaraciones (out: error1)→
	private boolean Declaraciones (){
		Object[] decRes = Declaracion();
		String idh3 = (String)decRes[0];
		String tipoh3 = (String)decRes[1];
		boolean errorh3 = (Boolean)decRes[2];
		boolean error3 = DeclaracionesFact(idh3,tipoh3,errorh3);
		return error3;
	}
	
	//Declaración(out: error1, id1, tipo1) → id : tipo
	private Object[] Declaracion(){
		String id1=null;
		String tipo1=null;
		boolean error1=false;
		Token t=sigToken();
		if (!(t instanceof Identificador)){
			errores.add(new ErrorTraductor("Error identificando id en declaración"));
			error1=true;
		}
		else id1=t.getLex();
		
		boolean error2 = dosPuntos();
		t=sigToken();
		if (!(t instanceof Tipo)){
			errores.add(new ErrorTraductor("Error identificando tipo en declaración"));
			error1=true;
		}
		else tipo1=t.getLex();
	
		
		return new Object[]{error1 || error2 ,id1,tipo1};			
	}
	
	//DeclaracionesFact(in: idh1, tipoh1, errorh1; out: error1) → λ
	
	private boolean DeclaracionesFact(String idh1,String tipoh1,boolean errorh1){
		boolean error1=false;
		boolean errorPC= puntoYComa();
		if (!errorPC && !errorh1){//no lambda
			boolean error2= Declaraciones();
			error1 = error2 || TablaSimbolos.existe(ts, idh1);
			ts = TablaSimbolos.inserta(ts, idh1, tipoh1, numVars);
			numVars++;
		}
		else if (!errorh1) { //interpretamos como vacío
			ts = TablaSimbolos.inserta(TablaSimbolos.creaTS(),idh1,tipoh1,0);
			numVars=1;
		}
		else error1=true;
		return error1;
	}
	
	//Instrucciones(out: error1,codP1) → 
	private Object[] Instrucciones(){
		boolean error1;
		String codP1;
		Object[] resIns= Instruccion();
		boolean error2 = (Boolean)resIns[0];
		String codP2 = (String)resIns[1];
		Object[] resInsFact = InstruccionesFact(error2,codP2);
		error1 = (Boolean)resInsFact[0];
		codP1 = (String)resInsFact[1];
	}
	
	//InstruccionesFact(in: errorh1,codPh1,codJh1; out: error1,codP1,codJ1) →
	private Object[] InstruccionFact(boolean errorh1, String codPh1 ){
		boolean error1=false;
		String codP1="";
		boolean errorPC = puntoYComa();
		if (!errorPC && !errorh1){ //no lambda
			Object[] resInst = Instrucciones();
			boolean error2 = (Boolean) resInst[0];
			String codP2 = (String) resInst[1];
			error1 = error2;
			codP1 = codPh1 + codP2;
		}
		else if (!errorh1){ //lambda
			error1=errorh1;
			codP1=codPh1;
		}
		else error1=true;
		
		return new Object[]{error1,codP1};
	}
	
	//Instrucción(out: error1,codP1) → 
	private Object[] Instruccion(){
		boolean error1;
		String codP1;
		Object[] resLect= InsLectura();
		Object[] resEscr= InsEscritura();
		Object[] resAsig= InsAsignacion();
		boolean error2L = (Boolean)resLect[0];
		boolean error2E = (Boolean)resEscr[0];
		boolean error2A = (Boolean)resAsig[0];
		String codP2="";
		
		if (!error2L){//ins Lectura
			codP2=(String)resLect[1];
		}
		else if (!error2E){
			codP2=(String)resEscr[1];
		}
		else if (!error2A){
			codP2=(String)resAsig[1];
		}
		else error1=true;
		
		return new Object[]{error1,codP2};
	}
	
	//--- auxiliares---


//InsLectura(out:error1,codP1,codJ1) →  
//	in()
//	abrePar()
//	{
//	error1 ← NOT existeID(ts,id.lex)
//	codP1 ← in ts[id.lex].dir
//	codP2 ← lectura dameNumTipo(ts[id.lex].tipo)
//	}
//	cierraPar()
//
//InsEscritura(out: error1,codP1,codJ1) → 
//	out()
//	abrePar()
//	Expresión(out: tipo2, codP2, codJ2)
//	cierraPar()
//	{	
//	error1 ← (tipo2=Error)
//	codP1 ← codP2 || out
//	codJ1 ← codJ2 || escritura dameNumTipo(tipo2)
//	}
//
//InsAsignación(out: error1,codP1,codJ1) → 
//	dosPuntosIgual()
//	Expresión(out: tipo3,codP3,codJ3)
//	{
//	error1 ← (NOT existeID(ts,id.lex)) v (tipo2 = error) v
//			      (ts[id.lex].tipo = float ᴧ (tipo3 = character v tipo3 = boolean)) v
//			        (ts[id.lex].tipo = integer ᴧ (tipo3=float v tipo3 = character v  tipo3 = boolean))  v
//			        (ts[id.lex].tipo = natural ᴧ tipo3 =/= natural)  v
//			        (ts[id.lex].tipo = character ᴧ tipo3 =/= character)  v
//			        (ts[id.lex].tipo = boolean ᴧ tipo3 =/= boolean)			
//
//	direccion ← ts[id.lex].dir
//
//
//
//	codP1 ← codP3 || desapila-dir direccion
//	codJ1 ← case (ts[id.lex].tipo)
//			boolean:
//				codJ3 || i2b || istore direccion
//			character:
//				codJ3 || i2c || istore InstAsignación.tsh[id.lex].dir
//			natural:
//			entero:
//				codJ3 || istore direccion
//			float:
//				codJ3 || fstore direccion
//
//
//Expresión(out: tipo1,codP1,codJ1) → 
//
//	ExpresiónNiv1(out: tipo2,codP2,codJ2)
//	{
//	tipo3h ← tipo2
//	codPh3 ← codP2
//	codJh3 ← codJ2
//	}
//	ExpresiónFact(in: tipo3h,codPh3,codJh3; out: tipo3,codP3,codJ3)
//	{
//	tipo1 ← tipo3
//	codP1 ← codP3
//	codJ1 ← codJ3
//	}
//
//ExpresiónFact(in: tipo1h,codPh1,codJh1; out: tipo1,codP1,codJ1) → λ
//	{
//	tipo1 ← tipo1h
//	codP1 ← codPh1
//	codJ1 ← codJh1
//	}
//
//ExpresiónFact(in: tipo1h,codPh1,codJh1; out: tipo1,codP1,codJ1) → 
//	OpNiv0(out: op2)
//	ExpresiónNiv1(out: tipo3,codP3,codJ3)
//	{
//	tipo1 ←  si (tipo1h = error v tipo3 = error) v
//		 	      ( tipo1h = character ᴧ tipo3 =/= character) v
//			      ( tipo1h =/= character ᴧ tipo3 = character)
//		 	      (tipo1h = boolean ᴧ tipo3 =/= boolean) v
//			      ( tipo1h =/= boolean ᴧ tipo3 = boolean))
//			              error
//			  sino  boolean
//
//
//	codP1 ← 
//		case (op2)
//			menor:
//				case (tipo1h)
//					float:
//						si (tipo3 = float)
//						       codPh1 || codP3 || menor
//						sino
//						       codPh1 || codP3 || CastFloat || menor
//					entero:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || menor
//						sino si (tipo3 = natural)
//						       codPh1 || codP3 || CastInt || menor
//						sino
//						       codPh1 || codP3 || menor
//					natural:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || menor
//						sino si (tipo3 = entero)
//						       codPh1 || CastInt || codP3 || menor
//						sino
//						       codPh1 || codP3 || menor
//					otro:
//						codPh1 || codP3 || menor
//			mayor
//				case (tipo1h)
//					float:
//						si (tipo3 = float)
//						       codPh1 || codP3 || mayor
//						sino
//						       codPh1 || codP3 || CastFloat || mayor
//					entero:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || mayor
//						sino si (tipo3 = natural)
//						       codPh1 || codP3 || CastInt || mayor
//						sino
//						       codPh1 || codP3 || mayor
//					natural:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || mayor
//						sino si (tipo3 = entero)
//						       codPh1 || CastInt || codP3 || mayor
//						sino
//						       codPh1 || codP3 || mayor
//					otro:
//						codPh1 || codP3 ||  mayor
//			menor-ig
//				case (tipo1h)
//					float:
//						si (tipo3 = float)
//						       codPh1 || codP3 || menorIg
//						sino
//						       codPh1 || codP3 || CastFloat || menorIg
//					entero:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || menorIg
//						sino si (tipo3 = natural)
//						       codPh1 || codP3 || CastInt || menorIg
//						sino
//						       codPh1 || codP3 || menorIg
//					natural:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || menorIg
//						sino si (tipo3 = entero)
//						       codPh1 || CastInt || codP3 || menorIg
//						sino
//						       codPh1 || codP3 || menorIg
//					otro:
//						codPh1 || codP3 ||  menorIg
//			mayor-ig
//				case (tipo1h)
//					float:
//						si (tipo3 = float)
//						       codPh1 || codP3 || mayorIg
//						sino
//						       codPh1 || codP3 || CastFloat || mayorIg
//					entero:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || mayorIg
//						sino si (tipo3 = natural)
//						       codPh1 || codP3 || CastInt || mayorIg
//						sino
//						       codPh1 || codP3 || mayorIg
//					natural:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || mayorIg
//						sino si (tipo3 = entero)
//						       codPh1 || CastInt || codP3 || mayorIg
//						sino
//						       codPh1 || codP3 || mayorIg
//					otro:
//						codPh1 || codP3 ||  mayorIg
//			igual
//				case (tipo1h)
//					float:
//						si (tipo3 = float)
//						       codPh1 || codP3 || igual
//						sino
//						       codPh1 || codP3 || CastFloat || igual
//					entero:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || igual
//						sino si (tipo3 = natural)
//						       codPh1 || codP3 || CastInt || igual
//						sino
//						       codPh1 || codP3 || igual
//					natural:
//						si (tipo3 = float)
//						       codPh1 || CastFloat || codP3 || igual
//						sino si (tipo3 = entero)
//						       codPh1 || CastInt || codP3 || igual
//						sino
//						       codPh1 || codP3 || igual
//					otro:
//						codPh1 || codP3 ||  igual
//			no-igual
//				case (tipo1h)
//					float:
//						si (tipo3 = float)
//						       codPh1 || codP3 || no-igual
//						sino
//						       codPh1 || codP3 || CastFloat || no-igual
//					entero:
//						si (tipo3 = float)
//						      codPh1 || CastFloat || codP3 || no-igual
//						sino si (tipo3 = natural)
//						      codPh1 || codP3 || CastInt || no-igual
//						sino
//						      codPh1 || codP3 || no-igual
//					natural:
//						si (tipo3 = float)
//						      codPh1 || CastFloat || codP3 || no-igual
//						sino si (tipo3 = entero)
//						      codPh1 || CastInt || codP3 || no-igual
//						sino
//						      codPh1 || codP3 || no-igual
//					otro:
//						codPh1 || codP3 ||  no-igual
//
//	codJ1 ←  
//		case (op)
//			menor:
//				si (tipo1h = float)
//					si (tipo3 = float)
//						codJh1 || 
//						codJ3 || 
//						fcmpg || 
//						if_ge +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						i2f ||
//						fcmpg ||
//						if_ge +7
//						iconst_1
//						goto +4
//						iconst_0  
//				sino
//					si(tipo3 = float)
//						codJh1 ||
//						i2f ||
//						codJ3 ||
//						fcmpg ||
//						if_ge +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						if_icmpge +7
//						iconst_1
//						goto +4
//						iconst_0
//			mayor
//				si (tipo1h = float)
//					si (tipo3 = float)
//						codJh1 || 
//						codJ3 || 
//						fcmpg || 
//						if_le +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						i2f ||
//						fcmpg ||
//						if_le +7
//						iconst_1
//						goto +4
//						iconst_0  
//				sino
//					si(tipo3 = float)
//						codJh1 ||
//						i2f ||
//						codJ3 ||
//						fcmpg ||
//						if_le +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						if_icmple +7
//						iconst_1
//						goto +4
//						iconst_0
//			menor-ig
//				si (tipo1h = float)
//					si (tipo3 = float)
//						codJh1 || 
//						codJ3 || 
//						fcmpg || 
//						if_gt +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						i2f ||
//						fcmpg ||
//						if_gt +7
//						iconst_1
//						goto +4
//						iconst_0  
//				sino
//					si(tipo3 = float)
//						codJh1 ||
//						i2f ||
//						codJ3 ||
//						fcmpg ||
//						if_gt +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						if_icmpgt +7
//						iconst_1
//						goto +4
//						iconst_0
//			mayor-ig
//				si (tipo1h = float)
//					si (tipo3 = float)
//						codJh1 || 
//						codJ3 || 
//						fcmpg || 
//						if_lt +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						i2f ||
//						fcmpg ||
//						if_lt +7
//						iconst_1
//						goto +4
//						iconst_0  
//				sino
//					si(tipo3 = float)
//						codJh1 ||
//						i2f ||
//						codJ3 ||
//						fcmpg ||
//						if_lt +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						if_icmplt +7
//						iconst_1
//						goto +4
//						iconst_0
//			igual
//				si (tipo1h = float)
//					si (tipo3 = float)
//						codJh1 || 
//						codJ3 || 
//						fcmpg || 
//						if_ne +7
//						iconst_1
//						goto +4
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						i2f ||
//						fcmpg ||
//						if_ne +7
//						iconst_1
//						goto +4
//						iconst_0  
//				sino
//					si(tipo3 = float)
//						codJh1 ||
//						i2f ||
//						codJ3 ||
//						fcmpg ||
//						if_ne +7 ||
//						iconst_1 ||
//						goto +4 ||
//						iconst_0  
//					sino
//						codJh1 ||
//						codJ3 ||
//						if_icmpne +7 ||
//						iconst_1 ||
//						goto +4 ||
//						iconst_0
//			no-igual
//				si (tipo1h = float)
//					si (tipo3 = float)
//						codJh1 || 
//						codJ3 || 
//						fcmpg || 
//						if_eq +7 ||
//						iconst_1 ||
//						goto +4 ||
//						iconst_0
//					sino
//						codJh1 ||
//						codJ3 ||
//						i2f ||
//						fcmpg ||
//						if_eq +7 ||
//						iconst_1 ||
//						goto +4 ||
//						iconst_0
//				sino
//					si(tipo3 = float)
//						codJh1 ||
//						i2f ||
//						codJ3 ||
//						fcmpg ||
//						if_eq +7 ||
//						iconst_1 ||
//						goto +4 ||
//						iconst_0
//					sino
//						codJh1 ||
//						codJ3 ||
//						if_icmpeq +7 ||
//						iconst_1 ||
//						goto +4 ||
//						iconst_0
//	}
//
//ExpresiónNiv1(out: tipo1, codP1, codJ1) → 
//	
//	ExpresiónNiv2(out:  tipo2, codP2, codJ2) 
//	{
//	tipoh3 ← tipo2
//	codPh3 ← codP2
//	codJh3 ← codJ2
//	}
//	ExpresiónNiv1Rec(in: tipoh3, codPh3, codJh3; out: tipo3, codP3,codJ3)
//	{	
//	tipo1 ← tipo3
//	codP1 ← codP3
//	codJ1 ← codJ3
//	}
//
//ExpresiónNiv1Rec(in: tipoh1, codPh1, codJh1; out: tipo1, codP1,codJ1) → λ
//	{
//	tipo1 ← tipoh1
//	codP1 ← codPh1
//	codJ1 ← codJh1
//	}
//ExpresiónNiv1Rec(in: tipoh1, codPh1, codJh1; out: tipo1, codP1,codJ1) → 
//	OpNiv1 (out: op2)
//	ExpresiónNiv2 (out: tipo3, codP3, codJ3)
//	{
//	tipoh4 = 
//		si (tipoh1 = error v tipo3 = error v
//		     tipoh1 = char v tipo3 = char v
//		     (tipoh1 = boolean ᴧ tipo3 =/= boolean) v
//	                   (tipoh1 =/= boolean ᴧ tipo3 = boolean))
//			error
//		sino case (op2)
//			suma,resta: 
//			         si (tipoh1=float v tipo3 = float)
//				float
//			         sino si (tipoh1 =integer v tipo3 = integer)
//				 integer
//			         sino si (tipoh1 =natural ᴧ tipo3 = natural)
//				 natural
//			                 sino error
//			o:  
//			         si (tipoh1 = boolean ᴧ tipo3 = boolean)
//				    boolean
//			         sino error
//	
//	codPh4 ←  case (op2)
//			suma:
//				case (tipoh1)
//					float:
//						si(tipo3 = float)
//							codPh1 || codP3 || sumar
//						sino 
//							codPh1 || codP3 || CastFloat || sumar
//					entero:
//						si (tipo3 = float)
//							codPh1 || CastFloat || codP3 || sumar
//						sino si (tipo3 = natural)
//							codPh1 || codP3 || CastInt || sumar
//						sino 
//							codPh1 || codP3 || sumar
//					natural:
//						si (tipo3 = float)
//							codPh1 || CastFloat || codP3 || sumar
//						sino si (tipo3 = entero)
//							codPh1 || CastInt || codP3 ||  sumar
//						sino 
//							codPh1 || codP3 || sumar
//			resta:
//				case (tipoh1)
//					float:
//						si(ExpresionNiv2 .tipo = float)
//							codPh1 || codP3 || restar
//						sino 
//							codPh1 || codP3 || CastFloat || restar
//					entero:
//						si (tipo3 = float)
//							codPh1 || CastFloat || codP3 || restar
//						sino si (tipo3 = natural)
//							codPh1 || codP3 || CastInt || restar
//						sino 
//							codPh1 || codP3 || restar
//					natural:
//						si (tipo3 = float)
//							codPh1 || CastFloat || codP3 || restar
//						sino si (tipo3 = entero)
//							codPh1 || CastInt || codP3 ||  restar
//						sino 
//							codPh1 || codP3 || restar
//			o:
//				codPh1 || codP3 || o		
//
//	codJh4 =
//		case (op2)
//			suma:
//				si (tipoh1 = float)
//					si(tipo3 = float)
//						 codJh1 || codJ3 || fadd
//					sino
//						 codJh1 || codJ3 || i2f || fadd
//				sino
//					si(tipo3 = float)
//						 codJh1 || i2f || codJ3 || fadd
//					sino
//						 codJh1 || codJ3 || iadd
//			resta:
//				si (tipoh1 = float)
//					si(tipo3 = float)
//						 codJh1 || codJ3 || fsub
//					sino
//						 codJh1 || codJ3 || i2f || fsub
//				sino
//					si(tipo3 = float)
//						 codJh1 || i2f || codJ3 || fsub
//					sino
//						 codJh1 || codJ3 || isub
//			o:
//				codJh1 ||
//				ifne +7 ||
//				codJ3 ||
//				ifeq +7 ||
//				iconst_1 ||
//				goto +4 ||
//				iconst_0
//	}
//	ExpresiónNiv1Rec(in: tipoh4, codPh4, codJh4; out: tipo4, codP4,codJ4)
//	{
//	tipo1 ← tipo4
//	codP1 ← codP4
//	codJ1 ← codJ4
//	}
//
//
//ExpresiónNiv2(out: tipo1, codP1, codJ1) → 
//	ExpresiónNiv3(out: tipo2, codP2, codJ2)
//	{
//	tipoh3 ← tipo2
//	codPh3 ← codP2
//	codJh3 ← codJ2
//	}
//	ExpresiónNiv2Rec(in: tipoh3, codPh3, codJh3; out: tipo3, codP3, codJ3)
//	{
//	tipo1 ← tipo3
//	codP1 ← codP3
//	codJ1 ← codJ3
//	}
//
//ExpresiónNiv2Rec(in: tipoh1, codPh1, codJh1; out: tipo1, codP1, codJ1) → λ
//	{
//	tipo1 ← tipoh1
//	codP1 ← codPh1
//	codJ1 ← codJh1
//	}
//
//ExpresiónNiv2Rec(in: tipoh1, codPh1, codJh1; out: tipo1, codP1, codJ1) → 
//	OpNiv2(out: op2)
//	ExpresiónNiv3(out: tipo3, codP3, codJ3)
//	{
//	tipoh4 = 
//		si (tipoh1 = error v tipo3 = error v
//                      tipoh1 = character v tipo3 = character v
//		    (tipoh1 = boolean ᴧ tipo3 =/= boolean v
//		    (tipoh1 =/= boolean ᴧ tipo3 = boolean))
//		          error
//		sino case (op2)
//			multiplica,divide: 
//			         si (tipoh1=float v tipo3 = float)
//				    float
//			         sino si (tipoh1 =integer v tipo3 = integer)
//					integer
//			         sino si (tipoh1 =natural ᴧ tipo3=natural)
//				  natural
//			       sino error
//			modulo:
//			         si (tipo3 = natural ᴧ
//				(tipoh1=natural v tipoh1=integer))	
//				      tipoh1
//			         sino     error
//			y:  
//			         si (tipoh1 = boolean ᴧ tipo3 = boolean)
//				    boolean
//			         sino error
//	
//	codPh4 ←  
//		case(op2)
//			Multiplica:
//				case (tipoh1)
//					float:
//						si(tipo3 = float)
//							codPh1 || codP3 || Mul
//						sino 
//							codPh1 || codP3 || CastFloat || Mul
//					entero:
//						si (tipo3 = float)
//							codPh1 || CastFloat || codP3 || Mul
//						sino si (tipo3 = natural)
//							codPh1 || codP3 || CastInt || Mul
//						sino 
//							codPh1 || codP3 || Mul
//					natural:
//						si (tipo3 = float)
//							codPh1 || CastFloat || codP3 || Mul
//						sino si (tipo3 = entero)
//							codPh1 || CastInt || codP3 ||  Mul
//						sino 
//							codPh1 || codP3 || Mul
//			Divide:
//				case (tipoh1)
//					float:
//						si(tipo3 = float)
//							codPh1 || codP3 || Div
//						sino 
//							codPh1 || codP3 || CastFloat || Div
//					entero:
//						si (tipo3 = float)
//							codPh1 || CastFloat || codP3 || Div
//						sino si (tipo3 = natural)
//							codPh1 || codP3 || CastInt || Div
//						sino 
//							codPh1 || codP3 || Div
//					natural:
//						si (tipo3 = float)
//							codPh1 || CastFloat || codP3 || Div
//						sino si (tipo3 = entero)
//							codPh1 || CastInt || codP3 ||  Div
//						sino 
//							codPh1 || codP3 || Div
//			Modulo:
//				codPh1 || codP3 || Mod
//			y:
//				codPh1 || codP3 || Y
//
//	codJh4 ← 
//		case(op2)
//			Multiplica:
//				si(tipoh1 = float)
//					si(tipo3 = float)
//						codJh1 || codJ3 || fmul
//					sino
//						codJh1 || codJ3 || i2f || fmul
//				sino
//					si(tipo3 = float)
//						codJh1 || i2f || codJ3|| fmul
//					sino
//						codJh1 || codJ3|| imul
//			Divide:
//				si(tipoh1 = float)
//					si(tipo3 = float)
//						codJh1 || codJ3 || fdiv
//					sino
//						codJh1 || codJ3 || i2f || fdiv
//				sino
//					si(tipo3 = float)
//						codJh1 || i2f || codJ3|| fdiv
//					sino
//						codJh1 || codJ3|| idiv
//			Modulo:
//				codJh1 || codJ3 || imod
//			y:
//				codJh1 ||
//				ifeq +11 ||
//				codJ3 ||
//				ifeq +7 ||
//				iconst_1 ||
//				goto +4 ||
//				iconst_0
//	}
//	ExpresiónNiv2Rec(in: tipoh4, codPh4, codJh4; out: tipo4, codP4, codJ4)
//	{
//	tipo1 ← tipo4
//	codP1 ← codP4
//	codJ1 ← codJ4
//	}
//
//
//
//ExpresiónNiv3(out: tipo1, codP1, codJ1) → 
//	ExpresiónNiv4( out: tipo2, codP2, codJ2)
//	{
//	tipoh3 ← tipo2
//	codPh3 ← codP2
//	codJh3 ← codJ2
//	}
//	ExpresiónNiv3Fact(in: tipoh3, codPh3, codJh3; out: tipo3, codP3, codJ3)
//	{
//	tipo1 ← tipo3
//	codP1 ← codP3
//	codJ1 ← codJ3
//	}
//ExpresiónNiv3Fact(in: tipoh1, codPh1, codJh1; out: tipo1, codP1, codJ1) → λ
//	{
//	tipo1 ← tipoh1
//	codP1 ← codPh1
//	codJ1 ← codJh1
//	}
//
//ExpresiónNiv3Fact(in: tipoh1, codPh1, codJh1; out: tipo1, codP1, codJ1)  → 
//	OpNiv3(out: op2)
//	ExpresiónNiv3(out: tipo3, codP3, codJ3)
//	{
//	tipo1 ←  
//		si (tipoh1 = error v tipo3 = error v
//                                 tipoh1 =/= natural v tipo3 =/= natural)
//		       error
//		sino natural
//
//	codP1 ←  
//		case (op2)
//			shl:
//				codPh1 || codP3 || shl
//			shr:
//				codPh1 || codP3 || shr
//
//	codJ1 ← 
//		case (op2)
//			shl:
//				codJh1 || codJ3 || ishl
//			shr:
//				codJh1 || codJ3 || ishr
//	}
//
//ExpresiónNiv4(out: tipo1, codP1, codJ1) → 
//	OpNiv4(out: op2)
//	ExpresiónNiv4(out tipo3, codP3, codJ3)
//	{
//	tipo1 ←  
//		si (tipo3 = error)
//		          error
//		sino case (op2)
//			no: 
//			         si (tipo3=boolean)
//				    boolean
//			         sino   error
//			menos:
//			         si (tipo3=float)
//				    float
//			         sino si (tipo3=integer v tipo3 = natural)
//					integer
//				   sino error
//			cast-float:
//			         si (tipo3=/=boolean)
//				    float
//			         sino error
//			cast-int:
//			         si (tipo3=/=boolean)
//				    integer
//			         sino error
//			cast-nat:
//		   	         si (tipo3=natural v tipo3=character)
//				   natural
//			         sino error
//			cast-char:
//			         si (tipo3=natural v tipo3=character)
//				   character
//			         sino error
//
//
//	codP1 ←  
//		case (op2)
//			no:
//				codP3 || no
//			negativo:
//				codP3 || negativo
//			cast-float:
//				codP3 || CastFloat
//			cast-int:
//				codP3 || CastInt
//			cast-nat:
//				codP3 || CastNat
//			cast-char:
//				codP3 || CastChar
//	
//	codJ1 ← 
//		case (op2)
//			no:
//				codJ3||
//				ifeq +7 ||
//				iconst_0 ||
//				goto +4 || 
//				iconst_1
//			negativo:
//				case (tipo3)
//					entero:
//						codJ3 || ineg
//					float:
//						codJ3 || fneg
//			cast-float:
//				case (tipo3)
//					character:
//					natural:
//					entero:
//						codJ3 || i2f
//					float:
//						codJ3
//			cast-int:
//				case (tipo3)
//					character:
//					natural:
//					entero:
//						codJ3
//					float:
//						codJ3 || f2i
//			cast-nat:
//				case (tipo3)
//					character:
//					natural:
//					entero:
//						codJ3
//					float:
//						codJ3 || f2i
//			cast-char:
//				case (tipo3)
//					character:
//					natural:
//					entero:
//						codJ3
//					float:
//						codJ3 || f2i
//	}
//
//ExpresiónNiv4(out: tipo1, codP1, codJ1) → 
//	barraVertical()
//	Expresión(out: tipo2, codP2, codJ2)
//	barraVertical()
//	{
//	tipo1 ← 
//		si (tipo2 = error v tipo2=boolean v tipo2=character)
//		          error
//		sino si (tipo2 = float)
//			float
//		sino si (tipo2 = natural v tipo2 = integer)
//			natural
//		sino error
//	
//
//	codP1 ← codP2 || abs
//	codJ1 ←  
//		case (tipo2)
//			float:
//				codJ2 ||
//				dup ||
//				fconst_0 ||
//				fcmpg ||
//				ifge +4
//				fneg
//			otro:
//				codJ2 ||
//				dup ||
//				ifge +4
//				fneg
//	}
//
//ExpresiónNiv4(out: tipo1, codP1, codJ1) → 
//	abrePar()
//	Expresión(out: tipo2, codP2, codJ2)
//	cierraPar()
//	{
//	tipo1 ← tipo2
//	codP1 ← codP2
//	codJ1 ← codJ2
//	}
//
//ExpresiónNiv4(out: tipo1, codP1, codJ1) → 
//	Literal(out: tipo2, codP2, codJ2)
//	{
//	tipo1 ← tipo2
//	codP1 ← codP2
//	codJ1 ← codJ2
//	}
//
//Literal(out: tipo1, codP1, codJ1) → id
//	{
//	tipo1 ← ts[id.lex].tipo
//	dir ← ts[id.lex].dir
//	codP1 ← apila-dir dir
//	codJ1 ←  case(ts[id.lex].tipo)
//		boolean:
//			i2b || iload  dir
//		tCha:
//			i2c || iload  dir
//		natural:
//		entero:
//			iload dir
//		float:
//			fload  dir
//	}
//Literal(out: tipo1, codP1, codJ1) → litNat
//	{
//	tipo1 ← natural
//	codP1 ← apila getValor(litNat.lex)
//	codJ1 ← iconst getValor(litNat.lex))
//	}
//Literal(out: tipo1, codP1, codJ1) → litFlo
//	{
//	tipo1 ← float
//	codP1 ← apila getValor(litFlo.lex)
//	codJ1 ← fconst getValor(litFlo.lex))
//	}
//Literal(out: tipo1, codP1, codJ1) → litBoo
//	{
//	tipo1 ← boolean	
//	codP1 ← apila getValor(litBoo.lex)
//	codJ1 ← iconst getValor(litEnt.lex))
//	}
//Literal(out: tipo1, codP1, codJ1) → litCha
//	{
//	tipo1 ← character
//	codP1 ← apila getValor(litCha.lex)
//	codJ1 ← iconst getValor(litCha.lex))
//	}
//
//OpNiv0(out: op) → <
//	{op = menor}
//OpNiv0(out: op) → >	
//	{op = mayor}
//OpNiv0(out: op) → <=
//	{op = menor-ig}
//OpNiv0(out: op) → >=
//	{op = mayor-ig}
//OpNiv0(out: op) → =
//	{op = igual}
//OpNiv0(out: op) → =/=
//	{op = no-igual}
//OpNiv1(out: op) → +
//	{op = suma}
//OpNiv1(out: op) → -
//	{op = resta}
//OpNiv1(out: op) → or
//	{op = o}
//OpNiv2(out: op) → *
//	{op = multiplica}
//OpNiv2(out: op) → /
//	{op = divide}
//OpNiv2(out: op) → %
//	{op = modulo}
//OpNiv2(out: op) → and
//	{op = y}
//OpNiv3(out: op) → >>
//	{op = shl}
//OpNiv3(out: op) → <<
//	{op = shr}
//OpNiv4(out: op) → not
//	{op = no}
//OpNiv4(out: op) → -
//	{op = menos}
//OpNiv4(out: op) → (float)
//	{op = cast-float}
//OpNiv4(out: op) → (int)
//	{op = cast-int}
//OpNiv4(out: op) → (nat)
//	{op = cast-nat}
//OpNiv4(out: op) → (char)
//	{op = cast-char}
//	
//	
//	
	
	private Token sigToken(){
		Token t= arrayTokens.get(i_token);
		i_token++;
		return t;
	}
	
	private boolean ampersand(){
		Token t=sigToken();
		boolean error=false;
		if (!(t instanceof Separador)) error=true;
		return error;
	}
	
	private boolean dosPuntos(){
		Token t=sigToken();
		boolean error=false;
		if (!(t instanceof Dos_puntos)) error=true;
		return error;
	}
	
	private boolean puntoYComa(){
		Token t=sigToken();
		boolean error=false;
		if (!(t instanceof Punto_coma)) error=true;
		return error;
	}
	
}
