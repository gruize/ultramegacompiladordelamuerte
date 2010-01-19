package compilador.traductor;

import java.util.ArrayList;
import compilador.lexico.Token;
import compilador.tablaSimbolos.TablaSimbolos;


public abstract class Traductor {

	protected ArrayList<Token> arrayTokens;
	protected TablaSimbolos ts;
	protected int numVars;
	
	public Traductor(ArrayList<Token> tokens){
		arrayTokens=tokens;
	}
	
}
