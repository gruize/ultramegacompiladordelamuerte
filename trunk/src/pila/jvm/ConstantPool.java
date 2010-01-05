/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import pila.jvm.entradasConstantPool.CONSTANT_Class_info;
import pila.jvm.entradasConstantPool.CONSTANT_Double_info;
import pila.jvm.entradasConstantPool.CONSTANT_Fieldref_info;
import pila.jvm.entradasConstantPool.CONSTANT_Float_info;
import pila.jvm.entradasConstantPool.CONSTANT_Integer_info;
import pila.jvm.entradasConstantPool.CONSTANT_InterfazMethodred_info;
import pila.jvm.entradasConstantPool.CONSTANT_Long_info;
import pila.jvm.entradasConstantPool.CONSTANT_Methodref_info;
import pila.jvm.entradasConstantPool.CONSTANT_NameAndType_info;
import pila.jvm.entradasConstantPool.CONSTANT_String_info;
import pila.jvm.entradasConstantPool.CONSTANT_Utf8_info;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class ConstantPool implements EstructuraClass {

    private int direccionActual;
    private Tabla<EstructuraClass> tabla;
    private HashMap<Object,U2> direcciones;

    public ConstantPool() {
        direccionActual = 0;
        tabla = new Tabla<EstructuraClass>();
        direcciones = new HashMap<Object, U2>();
    }

    public U2 nuevaEntrada(EstructuraClass entrada) {
        U2 dir = direcciones.get(entrada); //vemos si ya estaba en la tabla
        if(dir == null) { //si no estaba, lo guardamos
            dir = new U2();
            dir.setValor(direccionActual); //la entrada se guardara en el valor direccionActual
            direcciones.put(entrada, dir); //la ponemos para una posible nueva busqueda
            tabla.add(entrada);
            direccionActual += entrada.dameNumBytes();//se actualiza para la proxima entrada
        }
        return dir;
    }

    public String toInternalName(Class<?> clase) {
        if(clase.isArray())
            return clase.getName().replace('.', '/');
        if(clase.isPrimitive()) {
            if(clase.equals(Boolean.TYPE))
                return "Z";
            if(clase.equals(Byte.TYPE))
                return "B";
            if(clase.equals(Character.TYPE))
                return "C";
            if(clase.equals(Integer.TYPE))
                return "I";
            if(clase.equals(Short.TYPE))
                return "S";
            if(clase.equals(Long.TYPE))
                return "J";
            if(clase.equals(Float.TYPE))
                return "F";
            if(clase.equals(Double.TYPE))
                return "D";
            if(clase.equals(Void.TYPE))
                return "V";
        }
        return "L"+clase.getName().replace('.', '/')+";";
    }

    public U2 nuevoUtf8(String str) {
        if(direcciones.containsKey(str))
            return direcciones.get(str);
        
        Tabla<U1> ar = new Tabla<U1>();
        //mirar la documentacion de la jvm (apartado §4.4.7) si hay dudas
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            byte b;
            if(c >= 0x0001 && c <= 0x007F) {
                U1 u1 = new U1();
                u1.setValor(c); //se queda con los 8 bits mas bajos
                ar.add(u1);
            }
            else if(c >= 0x0080 && c <= 0x07FF || c == 0) {
                byte x = (byte) (3 << 5 + c & 0x7C0); //110 y los bits 10 al 6 de c
                byte y = (byte) (2 << 6 + c & 0x1F); //10 y los bits 5 al 0 de c
                U1 u1 = new U1();
                u1.setValor(x);
                ar.add(u1);
                u1 = new U1();
                u1.setValor(y);
                ar.add(u1);
            }
            else if(c >= 0x0800 && c <= 0xFFFF) {
                byte x = (byte) (5 << 4 + c & 0xF000); //1110 y los bits del 15 al 12
                byte y = (byte) (2 << 6 + c & 0xFC0); //10 y los bits del 11 al 6
                byte z = (byte) (2 << 6 + c & 0x1F); //10 y los bits del 5 al 0
                U1 u1 = new U1();
                u1.setValor(x);
                ar.add(u1);
                u1 = new U1();
                u1.setValor(y);
                ar.add(u1);
                u1 = new U1();
                u1.setValor(z);
                ar.add(u1);
            }
        }

        CONSTANT_Utf8_info entrada = new CONSTANT_Utf8_info(ar);
        return nuevaEntrada(entrada);
    }

    public U2 nuevoInternalUtf8(Class<?> clase) {
        return nuevaEntrada(nuevoUtf8(toInternalName(clase)));
    }
    
    public U2 nuevoNameAndType(U2 nameIndex, U2 descriptorIndex) {
        return nuevaEntrada(new CONSTANT_NameAndType_info(nameIndex, descriptorIndex));
    }

    public U2 nuevaClase(String internalName) {
        U2 dirNombre = nuevoUtf8(internalName);
        CONSTANT_Class_info entrada = new CONSTANT_Class_info(dirNombre);
        return nuevaEntrada(entrada);
    }

    public U2 nuevaClase(Class<?> clase) {
        if(direcciones.containsKey(clase))
            return direcciones.get(clase);
        U2 dirNombre = nuevoUtf8(toInternalName(clase));
        CONSTANT_Class_info entrada = new CONSTANT_Class_info(dirNombre);
        return nuevaEntrada(entrada);
    }

    

    /**
     * Crea un nuevo fieldref para un atributo definido en la clase "clase"
     * @param nombre
     * @param tipo clase del tipo del campo
     * @param clase
     * @return
     */
    public U2 nuevoFieldref(String nombre, Class<?> tipo, Class<?> clase) {
        U2 dir = nuevoNameAndType(nuevoUtf8(nombre), nuevoUtf8(toInternalName(tipo)));
        return nuevaEntrada(new CONSTANT_Fieldref_info(nuevaClase(clase), dir));
    }

    /**
     * Crea un nuevo methodref para un atributo definido en la clase "clase"
     * @param nombre
     * @param tipo debe ser de la forma especificada en <A HREF="http://java.sun.com/docs/books/jvms/second_edition/html/ClassFile.doc.html#7035">§4.3.3</A>
     * @param clase
     * @return la direccion dentro de la ConstantPool del methodref creado
     */
    public U2 nuevoMethodref(String nombre, Class<?> tipo, Class<?> clase) {
        U2 dir = nuevoNameAndType(nuevoUtf8(nombre), nuevoUtf8(toInternalName(tipo)));
        return nuevaEntrada(new CONSTANT_Methodref_info(nuevaClase(clase), dir));
    }

    public U2 nuevoInterfazMethodref(String nombre, Class<?>tipo, Class<?> interfaz) {
        U2 dir = nuevoNameAndType(nuevoUtf8(nombre), nuevoUtf8(toInternalName(tipo)));
        return nuevaEntrada(new CONSTANT_InterfazMethodred_info(nuevaClase(interfaz), dir));
    }

    public U2 nuevoString(String texto) {
        return nuevaEntrada(new CONSTANT_String_info(nuevoUtf8(texto)));
    }

    public U2 nuevoInteger(int i) {
        U4 valor = new U4();
        valor.setValor(i);
        return nuevaEntrada(new CONSTANT_Integer_info(valor));
    }

    public U2 nuevoFloat(float f) {
        U4 valor = new U4();
        valor.setValor(Float.floatToIntBits(f));
        return nuevaEntrada(new CONSTANT_Float_info(valor));
    }

    public U2 nuevoLong(long l) {
        U4 high = new U4();
        U4 low = new U4();
        high.setValor((int) (l >> 32));
        low.setValor((int) (l & 0xFFFF));
        return nuevaEntrada(new CONSTANT_Long_info(high, low));
    }

    public U2 nuevoDouble(double d) {
        long l = Double.doubleToLongBits(d);
        U4 high = new U4();
        U4 low = new U4();
        high.setValor((int) (l >> 32));
        low.setValor((int) (l & 0xFFFF));
        return nuevaEntrada(new CONSTANT_Double_info(high, low));
    }

    public void salvar(DataOutputStream dos) throws IOException {
        tabla.salvar(dos);
    }

    public int dameNumBytes() {
        return tabla.dameNumBytes();
    }
}
