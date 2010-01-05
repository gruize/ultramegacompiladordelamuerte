/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class CampoInfo implements EstructuraClass {
    /*    	u2 access_flags;
    	u2 name_index;
    	u2 descriptor_index;
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
*/
    private U2 accessFlags;
    private U2 nameIndex;
    private U2 descriptorIndex;
    private Tabla<AtributoInfo> atributos;

    public CampoInfo(U2 nameIndex, U2 descriptiorIndex, U2 accessFlags) {
        atributos = new Tabla<AtributoInfo>();
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptiorIndex;
        this.accessFlags = accessFlags;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        accessFlags.salvar(dos);
        nameIndex.salvar(dos);
        descriptorIndex.salvar(dos);
        atributos.salvar(dos);
    }

    public void nuevoAtributo(AtributoInfo atributo) {
        atributos.add(atributo);
    }

    public int dameNumBytes() {
        return accessFlags.dameNumBytes() + nameIndex.dameNumBytes()
                + descriptorIndex.dameNumBytes() + atributos.dameNumBytes();
    }



}
