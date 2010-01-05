/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class Constantes {
    //Flags para permisos
    public static final int ACC_PUBLIC = 0x0001;
    public static final int ACC_PRIVATE = 0x002;
    public static final int ACC_PROTECTED = 0x0004;
    public static final int ACC_STATIC = 0x0008;
    public static final int ACC_FINAL = 0x0010;
    public static final int ACC_SYNCHRONIZED = 0x0020;
    public static final int ACC_SUPER = 0x0020;
    public static final int ACC_NATIVE = 0x0100;
    public static final int ACC_INTERFACE = 0x0200;
    public static final int ACC_ABSTRACT = 0x0400;
    public static final int ACC_STRICT = 0x0800;

    //Constantes para la ConstantPool
    public static final byte CONSTANT_Class = 7;
    public static final byte CONSTANT_Fieldref = 9;
    public static final byte CONSTANT_Methodref = 10;
    public static final byte CONSTANT_InterfaceMethodref = 11;
    public static final byte CONSTANT_String = 8;
    public static final byte CONSTANT_Integer = 3;
    public static final byte CONSTANT_Float = 4;
    public static final byte CONSTANT_Long = 5;
    public static final byte CONSTANT_Double = 6;
    public static final byte CONSTANT_NameAndType = 12;
    public static final byte CONSTANT_Utf8 = 1;

    public static int activarBytes(int permisos, int bytes) {
        return permisos | bytes;
    }

    public static int desactivarBytes(int permisos, int bytes) {
        return permisos & ~bytes;
    }

    public static boolean bytesActivados(int permisos, int bytes) {
        return (permisos & bytes) != 0;
    }
    
}
