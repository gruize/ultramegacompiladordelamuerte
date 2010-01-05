/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.entradasConstantPool;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.jvm.Constantes;
import pila.jvm.EstructuraClass;
import pila.jvm.Tabla;
import pila.jvm.U1;
import pila.jvm.U2;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class CONSTANT_Utf8_info implements EstructuraClass {
    /*
        u1 tag;
    	u2 length;
    	u1 bytes[length];
     */
    private Tabla<U1> tabla;

    /**
     * @param tabla The bytes array contains the bytes of the string. No byte
     * may have the value (byte)0 or lie in the range (byte)0xf0-(byte)0xff.
     */
    public CONSTANT_Utf8_info(Tabla<U1> tabla) {
        this.tabla = tabla;
        tabla.setAnchura((short)2); //por ser length U2 en la especificacion
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeByte(Constantes.CONSTANT_Utf8);
        tabla.salvar(dos);
    }

    public int dameNumBytes() {
        return 1 + tabla.dameNumBytes();
    }

}
