package compilador.traductor;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import pila.interprete.EscritorPila;
import pila.interprete.Interprete;
import pila.interprete.instrucciones.InstruccionInterprete;

import compilador.lexico.AnalizadorLexico;
import compilador.lexico.Tokens.Token;

/**
 *
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */

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
		ArrayList<InstruccionInterprete> ai= tcodp.traducir();
		imprimir(ai);
		File f= new File("./codigo_binario");
		EscritorPila ep= new EscritorPila();
		ep.escribirPrograma(ai, f);
		Interprete interprete = new Interprete(false);
		File f2= new File("./codigo_binario");
        interprete.leerPrograma(f2);
        interprete.ejecutarPrograma();
		
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

	private void imprimir(ArrayList<InstruccionInterprete> ai) {
		Iterator<InstruccionInterprete> it=ai.iterator();
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
