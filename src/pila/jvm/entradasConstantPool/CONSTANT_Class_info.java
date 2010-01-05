/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.entradasConstantPool;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.jvm.Constantes;
import pila.jvm.EstructuraClass;
import pila.jvm.U2;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class CONSTANT_Class_info implements EstructuraClass {
    /*
        u1 tag;
    	u2 name_index;
     */


    private U2 nameIndex;

    /**
     * 
     * @param nameIndex puntero a un CONSTANT_Utf8_info en la constantPool que represente
     * un nombre de clase o interfaz
     */
    public CONSTANT_Class_info(U2 nameIndex) {
        this.nameIndex = nameIndex;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_Class);
        nameIndex.salvar(dos);
    }

    public int dameNumBytes() {
        return 1+nameIndex.dameNumBytes();
    }
}
