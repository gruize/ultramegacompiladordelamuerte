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
public class CONSTANT_Double_info implements EstructuraClass {
    /*
        u1 tag;
    	u4 high_bytes;
    	u4 low_bytes;
    */
    private U4 high_bytes, low_bytes;

    /**
     * The high_bytes and low_bytes items of the CONSTANT_Double_info structure
     * together represent the double value in IEEE 754 floating-point double
     * format (§3.3.2). The bytes of each item are stored in big-endian (high
     * byte first) order.
     * @param high_bytes
     * @param low_bytes
     */
    public CONSTANT_Double_info(U4 high_bytes, U4 low_bytes) {
        this.high_bytes = high_bytes;
        this.low_bytes = low_bytes;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_Double);
        high_bytes.salvar(dos);
        low_bytes.salvar(dos);
    }

    public int dameNumBytes() {
        return 1+high_bytes.dameNumBytes()+low_bytes.dameNumBytes();
    }
}
