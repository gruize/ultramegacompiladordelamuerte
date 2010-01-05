/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.entradasConstantPool;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.jvm.Constantes;
import pila.jvm.EstructuraClass;
import pila.jvm.U4;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class CONSTANT_Integer_info implements EstructuraClass {
    /*
        u1 tag;
    	u4 bytes;
    */
    private U4 bytes;

    /**
     * @param bytes The bytes item of the CONSTANT_Integer_info structure
     * represents the value of the int constant. The bytes of the value are
     * stored in big-endian (high byte first) order.
     */
    public CONSTANT_Integer_info(U4 bytes) {
        this.bytes = bytes;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_Integer);
        bytes.salvar(dos);
    }

    public int dameNumBytes() {
        return 1+bytes.dameNumBytes();
    }



}
