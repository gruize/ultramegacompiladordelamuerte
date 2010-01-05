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
public class CONSTANT_Long_info implements EstructuraClass {
    /*
        u1 tag;
    	u4 high_bytes;
    	u4 low_bytes;
    */
    private U4 high_bytes, low_bytes;

    /**
     * The unsigned high_bytes and low_bytes items of the CONSTANT_Long_info
     * structure together represent the value of the long constant where the
     * bytes of each of high_bytes and low_bytes are stored in big-endian (high
     * byte first) order.
     * @param high_bytes
     * @param low_bytes
     */
    public CONSTANT_Long_info(U4 high_bytes, U4 low_bytes) {
        this.high_bytes = high_bytes;
        this.low_bytes = low_bytes;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_Long);
        high_bytes.salvar(dos);
        low_bytes.salvar(dos);
    }

    public int dameNumBytes() {
        return 1+high_bytes.dameNumBytes()+low_bytes.dameNumBytes();
    }

}
