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

public class TraductorTest extends JFrame{

	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args){
		
		TraductorTest t=new TraductorTest();
                System.exit(0);
	}
	
	public TraductorTest(File classFile){
            try{
                String nombreClase = classFile.getName();
                nombreClase = nombreClase.substring(0, nombreClase.indexOf('.'));
		AnalizadorLexico al=new AnalizadorLexico(abrirFich());
		ArrayList<Token> tokens=al.getArrayTokens();
		imprimirTokens(tokens);
		//TraductorCodP tcodp= new TraductorCodP(tokens);
                TraductorCodDual tcoddual= new TraductorCodDual(tokens);
		ArrayList<InstruccionInterprete> ai= tcoddual.getTraduccionP(nombreClase);
		imprimir(ai);
		File f= new File("./codigo_binario");
		EscritorPila ep= new EscritorPila();
		ep.escribirPrograma(ai, f);
		Interprete interprete = new Interprete(false);
		File f2= new File("./codigo_binario");
                interprete.leerPrograma(f2);
                interprete.ejecutarPrograma();

                //si llega aqui el programa interpretado acaba correctamente => generamos el class
                tcoddual.getTraduccionJ(nombreClase).dump(classFile);
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
	}

        public TraductorTest() {
            this(new File("./Clase.class"));
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
