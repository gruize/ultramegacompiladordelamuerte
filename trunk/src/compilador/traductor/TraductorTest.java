package compilador.traductor;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import pila.Instruccion;
import pila.interprete.EscritorPila;

import compilador.lexico.AnalizadorLexico;
import compilador.lexico.Tokens.Token;

public class TraductorTest extends JFrame{

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args){
		
		TraductorTest t=new TraductorTest();

	}
	
	public TraductorTest(){
		try{
		AnalizadorLexico al=new AnalizadorLexico(abrirFich());
		ArrayList<Token> tokens=al.getArrayTokens();
		imprimirTokens(tokens);
		TraductorCodP tcodp= new TraductorCodP(tokens);
		ArrayList<Instruccion> ai= tcodp.traducir();
		imprimir(ai);
		//File f= new File("./codigo_binario");
		//EscritorPila ep= new EscritorPila();
		//ep.escribirPrograma(ai, f);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{ System.exit(0);}
	}
	
	private void imprimirTokens(ArrayList<Token> tokens) {
		Iterator<Token> it=tokens.iterator();
		while (it.hasNext()){
			System.out.println(it.next());
		}
		
	}

	private void imprimir(ArrayList<Instruccion> ai) {
		Iterator<Instruccion> it=ai.iterator();
		while (it.hasNext()){
			System.out.println(it.next());
		}
		
	}

	private String abrirFich() {
		JFileChooser selectFich = new JFileChooser();
		selectFich.setEnabled(true);
		selectFich.setCurrentDirectory(new File("./src/compilador/traductor"));
		selectFich.showOpenDialog(this);
		return selectFich.getSelectedFile().getAbsolutePath();
	}
	

}
