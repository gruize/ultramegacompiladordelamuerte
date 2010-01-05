/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class AtributoInfo implements EstructuraClass {
/*
        u2 attribute_name_index;
    	u4 attribute_length;
    	u1 info[attribute_length];
 */
    private U2 nameIndex;
    protected Tabla<U1> info;

    public AtributoInfo(U2 nameIndex) {
        this(nameIndex,null);
    }

    public AtributoInfo(U2 nameIndex, Collection<U1> info)  {
        this.nameIndex = nameIndex;
        this.info = new Tabla<U1>();
        if(info != null) {
            Iterator<U1> it = info.iterator();
            while(it.hasNext())
                this.info.add(it.next());
        }
        short s = 4;
        this.info.setAnchura(s);
    }

    public void salvar(DataOutputStream dos) throws IOException {
        nameIndex.salvar(dos);
        info.salvar(dos);
    }

    public int dameNumBytes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
