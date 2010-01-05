/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @param <E> 
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public interface EstructuraClass {
    public void salvar(DataOutputStream dos) throws IOException;
    /**
     * @return el numero de bytes que ocupa la estructura
     */
    public int dameNumBytes();

}
