/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaz.compilador;

import compilador.lexico.Tokens.Token;
import java.io.File;
import java.io.PipedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import pila.interprete.Interprete;
import pila.interprete.instrucciones.InstruccionInterprete;

/**
 *
 * @author Alicia
 */
public class EjecucionThread extends Thread {

    Reader reader;
    Writer writer;
    boolean debug;


    public EjecucionThread(Reader reader, Writer writer,boolean debug) {
        this.reader = reader;
        this.writer = writer;
        this.debug=debug;
    }



    @Override
    public void run() {
         try {
            Interprete interprete = new Interprete(false, reader, new PrintWriter(System.out,true));
            File f2 = new File("./codigo_binario");
            interprete.leerPrograma(f2);
            interprete.ejecutarPrograma();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
