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
public class CONSTANT_Methodref_info implements EstructuraClass {
    /*
        u1 tag;
    	u2 class_index;
    	u2 name_and_type_index;
    */
    private U2 classIndex;
    private U2 nameAndTypeIndex;

    /**
     * Javadoc copiado de la documentacion de la jvm
     * @param classIndex The value of the class_index item must be a valid index
     * into the constant_pool table. The constant_pool entry at that index must
     * be a CONSTANT_Class_info (§4.4.1) structure representing the class or
     * interface type that contains the declaration of the field or method.
     * The class_index item of a CONSTANT_Methodref_info structure must be a
     * class type, not an interface type
     * @param nameAndTypeIndex The value of the name_and_type_index item must be
     * a valid index into the constant_pool table. The constant_pool entry at
     * that index must be a CONSTANT_NameAndType_info (§4.4.6) structure. This
     * constant_pool entry indicates the name and descriptor of the field or
     * method. In a CONSTANT_Methodref_info the indicated descriptor must be a
     * method descriptor (§4.3.2).
     */
    public CONSTANT_Methodref_info(U2 classIndex, U2 nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_Methodref);
        classIndex.salvar(dos);
        nameAndTypeIndex.salvar(dos);
    }

    public int dameNumBytes() {
        return 1 + classIndex.dameNumBytes()  + nameAndTypeIndex.dameNumBytes();
    }
}