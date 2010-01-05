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
public class CONSTANT_NameAndType_info implements EstructuraClass {
    /*
        u1 tag;
    	u2 name_index;
    	u2 descriptor_index;
     */
    private U2 nameIndex;
    private U2 descriptorIndex;

    /**
     * @param nameIndex The value of the name_index item must be a valid index
     * into the constant_pool table. The constant_pool entry at that index must
     * be a CONSTANT_Utf8_info (§4.4.7) structure representing either a valid
     * field or method name (§2.7) stored as a simple name (§2.7.1), that is, as
     * a Java programming language identifier (§2.2) or as the special method
     * name <init> (§3.9).
     * @param descriptorIndex The value of the descriptor_index item must be a
     * valid index into the constant_pool table. The constant_pool entry at that
     * index must be a CONSTANT_Utf8_info (§4.4.7) structure representing a
     * valid field descriptor (§4.3.2) or method descriptor (§4.3.3).
     */
    public CONSTANT_NameAndType_info(U2 nameIndex, U2 descriptorIndex) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_NameAndType);
        nameIndex.salvar(dos);
        descriptorIndex.salvar(dos);
    }

    public int dameNumBytes() {
        return 1+nameIndex.dameNumBytes()+descriptorIndex.dameNumBytes();
    }

}
