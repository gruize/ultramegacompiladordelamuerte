package compilador.traductor;

import java.util.ArrayList;

import compilador.lexico.*;
import compilador.tablaSimbolos.TablaSimbolos;

public class TraductorCodP extends Traductor{



	
	public TraductorCodP(ArrayList<Token> tokens){
		super(tokens);
		errores=new ArrayList<ErrorTraductor>();
		i_token=0;
	}


	protected Object[] InsLectura() {
		String codP1="";
		if (!in()) return new Object[]{true,codP1};
		
		if (!abrePar()){
			errores.add(new ErrorTraductor("Error instrucción lectura: no hay abrir paréntesis"));
			return new Object[]{true,codP1};
		}
		Token t=sigToken();
		if (!(t instanceof Identificador) || !TablaSimbolos.existe(ts, t.getLex())){
			errores.add(new ErrorTraductor("Error instrucción lectura: identificador"));
			return new Object[]{true,codP1};
		}
		codP1="in "+TablaSimbolos.getDir(ts,t.getLex())+"\n";
		if (!cierraPar()){
			errores.add(new ErrorTraductor("Error instrucción lectura: cierra paréntesis"));
			return new Object[]{true,codP1};
		}
		return new Object[]{false,codP1};
		
	}
	
	protected Object[] insEscritura(){
		String codP1="";
		if (!out()) return new Object[]{true,codP1};
		
		if (!abrePar()){
			errores.add(new ErrorTraductor("Error instrucción escritura: no hay abrir paréntesis"));
			return new Object[]{true,codP1};
		}
		Object[] resExpr= Expresion();
		String tipo2=(String)resExpr[0];
		String codP2=(String)resExpr[1];
		if (tipo2.equals("error")){
			errores.add(new ErrorTraductor("Error instrucción escritura: expresión"));
			return new Object[]{true,codP1};
		}
		if (!cierraPar()){
			errores.add(new ErrorTraductor("Error instrucción escritura: cierra paréntesis"));
			return new Object[]{true,codP1};
		}
		
		codP1= codP2 + "out\n";		
		return new Object[]{false,codP1};
	}
	

}