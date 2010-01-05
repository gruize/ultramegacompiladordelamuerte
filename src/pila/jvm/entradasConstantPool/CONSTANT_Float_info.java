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
public class CONSTANT_Float_info implements EstructuraClass {
    /*
        u1 tag;
    	u4 bytes;
    */
    private U4 bytes;

    /**
     * @param bytes The bytes item of the CONSTANT_Float_info structure
     * represents the value of the float constant in IEEE 754 floating-point
     * single format (§3.3.2). The bytes of the single format representation
     * are stored in big-endian (high byte first) order.
     */
    public CONSTANT_Float_info(U4 bytes) {
        this.bytes = bytes;
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_Float);
        bytes.salvar(dos);
    }

    public int dameNumBytes() {
        return 1+bytes.dameNumBytes();
    }

}
