/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class MetodoInfo implements EstructuraClass {
/*
        u2 access_flags;
    	u2 name_index;
    	u2 descriptor_index;
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
 */
    private U2 accessFlags;
    private U2 nameIndex;
    private U2 descriptorIndex;
    private Tabla<AtributoInfo> atributos;

    public MetodoInfo(U2 nameIndex, U2 descriptiorIndex, U2 accessFlags, AtributoInfo codigo) {
        atributos = new Tabla<AtributoInfo>();
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptiorIndex;
        this.accessFlags = accessFlags;
        atributos.add(codigo);
    }

    public void salvar(DataOutputStream dos) throws IOException {
        accessFlags.salvar(dos);
        nameIndex.salvar(dos);
        descriptorIndex.salvar(dos);
        atributos.salvar(dos);
    }

    public int dameNumBytes() {
        return accessFlags.dameNumBytes() + nameIndex.dameNumBytes()
                + descriptorIndex.dameNumBytes() + atributos.dameNumBytes();
    }
}
