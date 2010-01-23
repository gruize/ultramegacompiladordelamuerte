package compilador.traductor;
import java.util.ArrayList;

import pila.Instruccion;

public class Codigo {
	private ArrayList<Instruccion> cod;
	
	public Codigo(){
		cod=new ArrayList<Instruccion>();
	}
	
	public Codigo(Instruccion i){
		cod=new ArrayList<Instruccion>();
		cod.add(i);
	}
	
	public ArrayList<Instruccion> getCod(){
		return cod;
	}
	
	public boolean appendCod(Codigo c){
		return cod.addAll(c.getCod());
	}
	
	public void appendIns(Instruccion i){
		cod.add(i);
	}
	
	
}
