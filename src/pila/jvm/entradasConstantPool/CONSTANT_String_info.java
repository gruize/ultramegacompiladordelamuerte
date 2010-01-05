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
public class CONSTANT_String_info implements EstructuraClass{
    /*
        u1 tag;
        u2 string_index;
     */
    private U2 stringIndex;

    /**
     *
     * @param stringIndex The value of the string_index item must be a valid
     * index into the constant_pool table. The constant_pool entry at that index
     * must be a CONSTANT_Utf8_info (§4.4.7) structure representing the sequence
     * of characters to which the String object is to be initialized.
     */
    public CONSTANT_String_info(U2 stringIndex) {
        this.stringIndex = stringIndex;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_String);
        stringIndex.salvar(dos);
    }

    public int dameNumBytes() {
        return 1 + stringIndex.dameNumBytes();
    }

}
