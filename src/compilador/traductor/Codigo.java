package compilador.traductor;
import java.util.ArrayList;

import pila.interprete.instrucciones.InstruccionInterprete;

public class Codigo {
	private ArrayList<InstruccionInterprete> cod;
	
	public Codigo(){
		cod=new ArrayList<InstruccionInterprete>();
	}
	
	public Codigo(InstruccionInterprete i){
		cod=new ArrayList<InstruccionInterprete>();
		cod.add(i);
	}
	
	public ArrayList<InstruccionInterprete> getCod(){
		return cod;
	}
	
	public boolean appendCod(Codigo c){
		return cod.addAll(c.getCod());
	}
	
	public void appendIns(InstruccionInterprete i){
		cod.add(i);
	}
	
	
}
