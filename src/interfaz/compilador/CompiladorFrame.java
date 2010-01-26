/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CompiladorFrame.java
 *
 * Created on 25-ene-2010, 17:33:22
 */
package interfaz.compilador;

import compilador.lexico.AnalizadorLexico;
import compilador.lexico.Tokens.Token;
import compilador.traductor.TraductorCodP;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import pila.interprete.EscritorPila;
import pila.interprete.Interprete;
import pila.interprete.instrucciones.InstruccionInterprete;

/**
 *
 * @author usuario_local
 */
public class CompiladorFrame extends javax.swing.JFrame {

    File inputFile;
    PipedWriter pWriter = new PipedWriter();
    PipedReader pReader = new PipedReader();

    /** Creates new form CompiladorFrame */
    public CompiladorFrame() {
        initComponents();
        try {
            pReader.connect(pWriter);
        } catch (IOException ex) {
            Logger.getLogger(CompiladorFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        textAreaEntrada = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        textAreaDebug = new javax.swing.JTextArea();
        compilarButton = new javax.swing.JButton();
        ejecutarButton = new javax.swing.JButton();
        abrirButton = new javax.swing.JButton();
        inputTextField = new javax.swing.JTextField();
        EnviarButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        textAreaEjecucion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textAreaEntrada.setColumns(20);
        textAreaEntrada.setEditable(false);
        textAreaEntrada.setRows(5);
        textAreaEntrada.setName("EntradaCodigo"); // NOI18N
        jScrollPane1.setViewportView(textAreaEntrada);

        textAreaDebug.setColumns(20);
        textAreaDebug.setEditable(false);
        textAreaDebug.setRows(5);
        textAreaDebug.setName("ConsolaCompilacion"); // NOI18N
        jScrollPane2.setViewportView(textAreaDebug);

        compilarButton.setText("Compilar");
        compilarButton.setName("CompilarButton"); // NOI18N
        compilarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                compilarButtonMouseClicked(evt);
            }
        });

        ejecutarButton.setText("Ejecutar");
        ejecutarButton.setName("EjecutarButton"); // NOI18N
        ejecutarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ejecutarButtonMouseClicked(evt);
            }
        });

        abrirButton.setText("Abrir");
        abrirButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                abrirButtonMouseClicked(evt);
            }
        });

        EnviarButton.setText("Enviar");
        EnviarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EnviarButtonMouseClicked(evt);
            }
        });

        textAreaEjecucion.setColumns(20);
        textAreaEjecucion.setEditable(false);
        textAreaEjecucion.setRows(5);
        jScrollPane3.setViewportView(textAreaEjecucion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(abrirButton))
                            .addComponent(compilarButton)
                            .addComponent(ejecutarButton))
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(inputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(EnviarButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(abrirButton)
                        .addGap(6, 6, 6)
                        .addComponent(compilarButton)
                        .addGap(6, 6, 6)
                        .addComponent(ejecutarButton))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(inputTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(EnviarButton))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** Writes everything into the text area. */
    private class TextAreaOutputStream extends java.io.OutputStream {

        @Override
        public void write(int b) throws java.io.IOException {
            String s = new String(new char[]{(char) b});
            textAreaEjecucion.append(s);
        }
    }

    private void ejecutarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ejecutarButtonMouseClicked
        try {
            this.textAreaDebug.setText("");
            this.textAreaEjecucion.setText("");
            if (compilar())
                new EjecucionThread(pReader, pWriter).start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //System.exit(0);
        }
}//GEN-LAST:event_ejecutarButtonMouseClicked

    private void abrirButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_abrirButtonMouseClicked
        try {
            inputFile = new File(abrirFich());
            InputStreamReader reader = new InputStreamReader(new FileInputStream(inputFile));
            this.textAreaEntrada.read(reader, evt);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
}//GEN-LAST:event_abrirButtonMouseClicked

    private void compilarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_compilarButtonMouseClicked
        this.compilar();
}//GEN-LAST:event_compilarButtonMouseClicked

    private void EnviarButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EnviarButtonMouseClicked
        try {
            pWriter.write(inputTextField.getText() + "\n");
            // inputTextField.write(pWriter);
            pWriter.flush();
            textAreaEjecucion.append(inputTextField.getText() + "\n");
            inputTextField.setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_EnviarButtonMouseClicked

    private boolean compilar() {
        boolean error=false;
        try {
            System.setOut(new PrintStream(new TextAreaOutputStream()));
            InputStreamReader reader = new InputStreamReader(new FileInputStream(inputFile));
            AnalizadorLexico al = new AnalizadorLexico(reader);
            ArrayList<Token> tokens = al.getArrayTokens();
            imprimirTokens(tokens);
            if(al.getErrorLexico() == false){
                TraductorCodP tcodp = new TraductorCodP(tokens);
                ArrayList<InstruccionInterprete> ai = tcodp.traducir();
                imprimir(ai);
                File f = new File("./codigo_binario");
                EscritorPila ep = new EscritorPila();
                ep.escribirPrograma(ai, f);
            }
            else error=true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return !error;
    }

    private void ejecutar() {
        try {
            this.textAreaDebug.setText("");
            this.textAreaEjecucion.setText("");
            if (compilar()){
                EjecucionThread thread = new EjecucionThread(new PipedReader(pWriter), pWriter);
            thread.run();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            //System.exit(0);
        }
    }

    private void imprimirTokens(ArrayList<Token> tokens) {
        textAreaDebug.append("\n --TOKENS--\n\n");
        Iterator<Token> it = tokens.iterator();
        while (it.hasNext()) {
            textAreaDebug.append(it.next().toString() + "\n");
        }

    }

    private void imprimir(ArrayList<InstruccionInterprete> ai) {
        textAreaDebug.append("\n --INSTRUCCIONES INTERPRETE--\n\n");
        Iterator<InstruccionInterprete> it = ai.iterator();
        while (it.hasNext()) {
            textAreaDebug.append(it.next().toString() + "\n");
        }

    }

    private String abrirFich() {
        JFileChooser selectFich = new JFileChooser();
        selectFich.setEnabled(true);
        selectFich.setCurrentDirectory(new File("./src/compilador/traductor"));
        selectFich.showOpenDialog(this);
        return selectFich.getSelectedFile().getAbsolutePath();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {



            public void run() {
                new CompiladorFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EnviarButton;
    private javax.swing.JButton abrirButton;
    private javax.swing.JButton compilarButton;
    private javax.swing.JButton ejecutarButton;
    private javax.swing.JTextField inputTextField;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea textAreaDebug;
    private javax.swing.JTextArea textAreaEjecucion;
    protected javax.swing.JTextArea textAreaEntrada;
    // End of variables declaration//GEN-END:variables
}
