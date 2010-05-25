/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CompiladorFrame.java
 *
 * Created on 25-ene-2010, 17:33:22
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
package interfaz.compilador;

import java.io.File;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;

import pila.interprete.EscritorPila;
import pila.interprete.instrucciones.InstruccionInterprete;

import compilador.lexico.AnalizadorLexico;
import compilador.lexico.Tokens.Token;
import compilador.traductor.Traductor;
import java.io.FileReader;
import java.io.Reader;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author usuario_local
 */
public class CompiladorFrame extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;
    File inputFile;
    PipedWriter pWriter = new PipedWriter();
    PipedReader pReader = new PipedReader();
    JFileChooser selectFich = new JFileChooser();
    JPanel opcionesCompilacionPanel, opcionesEjecucionPanel;
    JCheckBox checkJVM, checkP, checkDebug;

    /** Creates new form CompiladorFrame */
    public CompiladorFrame() {
        initComponents();
        try {
            pReader.connect(pWriter);
            System.setOut(new PrintStream(new TextAreaOutputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
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
        compilarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilarButtonActionPerformed(evt);
            }
        });

        ejecutarButton.setText("Ejecutar");
        ejecutarButton.setName("EjecutarButton"); // NOI18N
        ejecutarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutarButtonActionPerformed(evt);
            }
        });

        abrirButton.setText("Abrir");
        abrirButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirButtonActionPerformed(evt);
            }
        });

        EnviarButton.setText("Enviar");
        EnviarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnviarButtonActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ejecutarButton)
                            .addComponent(compilarButton)
                            .addComponent(abrirButton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
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

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {abrirButton, compilarButton, ejecutarButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(abrirButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(compilarButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ejecutarButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

    private void abrirButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirButtonActionPerformed
        try {
            String str = abrirFich();
            if(str != null) {
                inputFile = new File(str);
                Reader reader = new FileReader(inputFile);
                textAreaEntrada.read(reader, null);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_abrirButtonActionPerformed

    private void EnviarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnviarButtonActionPerformed
        try {
            pWriter.write(inputTextField.getText() + "\n");
            // inputTextField.write(pWriter);
            pWriter.flush();
            textAreaEjecucion.append(inputTextField.getText() + "\n");
            inputTextField.setText("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_EnviarButtonActionPerformed

    private void compilarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilarButtonActionPerformed
        if(opcionesCompilacionPanel == null) {
            checkJVM = new JCheckBox("Compilar a codigo JVM");
            checkP = new JCheckBox("Compilar a codigo P");
            opcionesCompilacionPanel = new JPanel();
            opcionesCompilacionPanel.setLayout(new BoxLayout(opcionesCompilacionPanel, BoxLayout.Y_AXIS));
            opcionesCompilacionPanel.add(checkP);
            opcionesCompilacionPanel.add(checkJVM);
        }
        int i = JOptionPane.showConfirmDialog(this, opcionesCompilacionPanel,
                "Opciones de compilación", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(i == JOptionPane.OK_OPTION) {
            File f = null;
            if(checkJVM.isSelected()) {
                if(selectFich.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
                    f = selectFich.getSelectedFile();
                else
                    return ;
            }
            compilar(f, checkP.isSelected());
        }
    }//GEN-LAST:event_compilarButtonActionPerformed

    private void ejecutarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutarButtonActionPerformed
        if(opcionesEjecucionPanel == null) {
            opcionesEjecucionPanel = new JPanel();
            opcionesEjecucionPanel.setLayout(new BoxLayout(opcionesEjecucionPanel, BoxLayout.Y_AXIS));
            checkDebug = new JCheckBox("Modo depuración");
            opcionesEjecucionPanel.add(checkDebug);
        }
        int i = JOptionPane.showConfirmDialog(this, opcionesEjecucionPanel,
                "Opciones de ejecución", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if(i == JOptionPane.OK_OPTION)
            ejecutar(checkDebug.isSelected());
    }//GEN-LAST:event_ejecutarButtonActionPerformed

    public boolean compilar(File classFile, boolean compilarP) {
        try {
            this.textAreaEjecucion.setText("");
            String codigo = textAreaEntrada.getText();
            if (codigo.equals("")) {
                return false;
            }
            AnalizadorLexico al = new AnalizadorLexico(codigo);
            if (al.getErrorLexico()) {
                return false;
            }
            ArrayList<Token> tokens = al.getArrayTokens();
            imprimirTokens(tokens);
            Traductor tcoddual = new Traductor(tokens);

            if(classFile != null) {
                String nombreClase = classFile.getName();
                int i = nombreClase.indexOf('.');
                if(i != -1) {//tiene un punto
                    if(!nombreClase.substring(i).equals(".class")) {
                        JOptionPane.showMessageDialog(this, "El archivo de destino del " +
                                "programa compilado a JVM debe acabar en .class");
                        return false;
                    }
                    else
                        nombreClase = nombreClase.substring(0, i);
                }
                else {
                    classFile = new File(classFile.getParent()+File.separator+nombreClase+".class");
                }
                //tcoddual.getTraduccionJ(nombreClase).dump(classFile);
            }
            if(compilarP) {
                tcoddual.traducir(codigo);
                ArrayList<InstruccionInterprete> ai = tcoddual.getCod().getCod();
                imprimir(ai);
                File f = new File("./codigo_binario");
                EscritorPila ep = new EscritorPila();
                ep.escribirPrograma(ai, f);
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private void ejecutar(boolean debug) {

        if (!compilar(null,true)) {
            return;
        }
        EjecucionThread thread = new EjecucionThread(pReader, pWriter, debug);
        thread.start();
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
    	selectFich.setCurrentDirectory(new File("."));
        if(selectFich.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
            return selectFich.getSelectedFile().getAbsolutePath();
        else
            return null;
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
