/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InterpretePanel.java
 *
 * Created on 04-dic-2009, 20:51:58
 */

package interfaz.pila;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pila.EscritorBytecode;
import pila.Instruccion;
import pila.LectorBytecode;
import pila.interprete.EscritorPila;
import pila.interprete.Interprete;
import pila.interprete.LectorPila;

/**
 *
 * @author golthiryus
 */
public class InterpretePanel extends javax.swing.JPanel {

    JFileChooser fileChooser;

    public static void main(String [] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(new InterpretePanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /** Creates new form InterpretePanel */
    public InterpretePanel() {
        initComponents();
        fileChooser = new JFileChooser();
    }

    public void decompilar(File f) {
        try {
            if(f == null || !f.canRead())
                throw new IOException("Archivo inválido o ilegible");

            LectorBytecode lb;
            if(true) {
                 lb = new LectorPila();
            }

            ArrayList<Instruccion> programa = lb.leerPrograma(f);

            textArea.setText("");
            for(Iterator<Instruccion> it = programa.iterator(); it.hasNext();) {
                Instruccion ins = it.next();
                textArea.append(ins.toString());
                if(ins.getDato() != null)
                    textArea.append(" "+ins.getDato().toString());
                textArea.append("\n");
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.toString(), "Error al leer " +
                    "el bytecode", JOptionPane.ERROR_MESSAGE);
        }

        
    }

    public void compilar(File f) throws FileNotFoundException, IOException, Exception {
        String texto = textArea.getText();
        TraductorPila traductor;
        EscritorBytecode escritor;
        if(lenguajeGroup.getSelection() == propioLengBut)
            throw new UnsupportedOperationException("Not supported yet.");
        else {
            traductor = new TraductorInterprete();
            escritor = new EscritorPila();
        }
        ArrayList<Instruccion> ar =  traductor.traducirPrograma(texto);
        escritor.escribirPrograma(ar, f);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lenguajeGroup = new javax.swing.ButtonGroup();
        botonesPanel = new javax.swing.JPanel();
        decompilarBot = new javax.swing.JButton();
        compilarBot = new javax.swing.JButton();
        EjecutarBot = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        propioLengBut = new javax.swing.JRadioButton();
        javaLengBut = new javax.swing.JRadioButton();

        setLayout(new java.awt.BorderLayout());

        botonesPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        decompilarBot.setText("Decompilar...");
        decompilarBot.setToolTipText("Al decompilar un archivo en bytecode este\nse mostrará como cadenas alfanumericas");
        decompilarBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decompilarBotActionPerformed(evt);
            }
        });

        compilarBot.setText("Compilar...");
        compilarBot.setToolTipText("Al compilar, el texto escrito sera traducido a lenguaje \nde pila, siempre y cuando su sintáxis sea correcta");
        compilarBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                compilarBotActionPerformed(evt);
            }
        });

        EjecutarBot.setText("Ejecutar");
        EjecutarBot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EjecutarBotActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout botonesPanelLayout = new javax.swing.GroupLayout(botonesPanel);
        botonesPanel.setLayout(botonesPanelLayout);
        botonesPanelLayout.setHorizontalGroup(
            botonesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(decompilarBot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addComponent(compilarBot)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EjecutarBot)
                .addContainerGap())
        );
        botonesPanelLayout.setVerticalGroup(
            botonesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botonesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(decompilarBot)
                .addComponent(compilarBot)
                .addComponent(EjecutarBot))
        );

        add(botonesPanel, java.awt.BorderLayout.PAGE_END);

        textArea.setColumns(20);
        textArea.setLineWrap(true);
        textArea.setRows(5);
        textArea.setText("Este panel hace de intermediario entre el bytecode del lenguaje a pila y el explicado en clase (con sentencias alfanumericas como \"apila 3\" o \"suma\").\n\nAl decompilar un archivo en bytecode este se mostrará como cadenas alfanumericas\n\nAl compilar, el texto escrito sera traducido a lenguaje de pila, siempre y cuando su sintáxis sea correcta");
        textArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(textArea);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setText("Lenguaje objeto:");
        jPanel1.add(jLabel1);

        lenguajeGroup.add(propioLengBut);
        propioLengBut.setSelected(true);
        propioLengBut.setText("Lenguaje Propio");
        jPanel1.add(propioLengBut);

        lenguajeGroup.add(javaLengBut);
        javaLengBut.setText("Java Bytecode");
        javaLengBut.setEnabled(false);
        jPanel1.add(javaLengBut);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void decompilarBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decompilarBotActionPerformed
        int ret = fileChooser.showOpenDialog(this);
        if(ret == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            decompilar(f);
        }
    }//GEN-LAST:event_decompilarBotActionPerformed

    private void compilarBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_compilarBotActionPerformed
        int ret = fileChooser.showSaveDialog(this);
        if(ret == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            try {
                compilar(f);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Error al compilar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_compilarBotActionPerformed

    private void EjecutarBotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EjecutarBotActionPerformed
        int ret = fileChooser.showOpenDialog(this);
        if(ret == JFileChooser.APPROVE_OPTION) {
            try {
                File f = fileChooser.getSelectedFile();
                Interprete interprete = new Interprete(true);
                interprete.leerPrograma(f);
                interprete.ejecutarPrograma();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.toString(), "Error al compilar", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_EjecutarBotActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EjecutarBot;
    private javax.swing.JPanel botonesPanel;
    private javax.swing.JButton compilarBot;
    private javax.swing.JButton decompilarBot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton javaLengBut;
    private javax.swing.ButtonGroup lenguajeGroup;
    private javax.swing.JRadioButton propioLengBut;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables

}
