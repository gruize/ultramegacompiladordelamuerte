/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @param <E>
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class Tabla<E extends EstructuraClass> extends ArrayList<E> implements EstructuraClass{

    /**
     * Numero de bits que ocupara el tamaño de la estructura
     */
    private short anchura;

    public Tabla() {
        anchura = 2;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        UAbstracto u = null;
        switch(anchura) {
            case 1:
                u = new U1();
                break;
            case 2:
                u = new U2();
                break;
            case 4:
                u = new U4();
                break;
        }
        u.setValor(dameNumBytes()-anchura);
        u.salvar(dos);

        Iterator<E> it;
        it = iterator();
        while(it.hasNext())
            it.next().salvar(dos);
    }

    public int dameNumBytes() {
        int i = anchura;
        Iterator<E> it = iterator();
        while(it.hasNext()) {
            i += it.next().dameNumBytes();
        }
        return i;
    }

    /**
     * Numero de bits que ocupara el tamaño de la estructura
     * @return the numBits
     */
    public short getAnchura() {
        return anchura;
    }

    /**
     * Numero de bits que ocupara el tamaño de la estructura
     * @param numBits the numBits to set
     */
    public void setAnchura(short numBits) {
        this.anchura = numBits;
    }
}
