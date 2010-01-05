/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.atributos;

import java.util.Iterator;
import pila.jvm.AtributoInfo;
import pila.jvm.Tabla;
import pila.jvm.U2;
import pila.jvm.instrucciones.InstruccionJVM;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class CodeAttribute extends AtributoInfo {
    
    public static final int MAX_STACK = 50;

    /*
        u2 attribute_name_index;
    	u4 attribute_length;
    	u2 max_stack;
    	u2 max_locals;
    	u4 code_length;
    	u1 code[code_length];
    	u2 exception_table_length;
    	{    	u2 start_pc;
    	      	u2 end_pc;
    	      	u2  handler_pc;
    	      	u2  catch_type;
    	}	exception_table[exception_table_length];
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
     */
    /**
     * Crea un CodeAttribute, la estructura que guarda el codigo de un metodo.
     * En esta implementacion se supone que no hay excepciones ni opciones de
     * depuracion (que en la jvm se guardan en el campo attribute_info)
     * @param nameIndex
     * @param codigo
     * @param maxLocals
     */
    public CodeAttribute (U2 nameIndex, Tabla<InstruccionJVM> codigo, int maxLocals) {
        super(nameIndex);
        //guardamos max_stack
        U2 u2 = new U2();
        u2.setValor(MAX_STACK);
        u2.appendTo(info);

        //guardamos max_locals
        u2 = new U2();
        u2.setValor(maxLocals);
        u2.appendTo(info);

        //guardamos el codigo
        for(Iterator<InstruccionJVM> it = codigo.iterator(); it.hasNext();)
            it.next().appendTo(info);

        //guardamos 0 en el nº de expceciones
        u2 = new U2();
        u2.setValor(0);
        u2.appendTo(info);

        //guardamos 0 en el nº de atributos
        u2 = new U2();
        u2.setValor(0);
        u2.appendTo(info);
    }
}