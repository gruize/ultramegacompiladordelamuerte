/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import pila.jvm.atributos.CodeAttribute;
import pila.jvm.instrucciones.InstJvm;

/**
 * El objetivo de esta clase es crear archivos .class comprensibles por una
 * maquina virtual java (jvm) para compilar el lenguaje de alto nivel a este
 * lenguaje y no a codigo P.
 * El codigo P visto en clase puede verse como un subconjunto del formato class,
 * por lo que gran parte de la funcionalidad de la jvm que sobrepasa nuestros
 * objetivos no ha sido implementando.
 * Por ejemplo, los metodos no lanzan ni capturan excepciones y solo se hace uso
 * de los atributos necesarios (el del codigo de los metodos), dejando el resto
 * de lado
 */
public class ClaseJVM implements EstructuraClass {
/*    	u4 magic;
    	u2 minor_version;
    	u2 major_version;
    	u2 constant_pool_count;
    	cp_info constant_pool[constant_pool_count-1];
    	u2 access_flags;
    	u2 this_class;
    	u2 super_class;
    	u2 interfaces_count;
    	u2 interfaces[interfaces_count];
    	u2 fields_count;
    	field_info fields[fields_count];
    	u2 methods_count;
    	method_info methods[methods_count];
    	u2 attributes_count;
    	attribute_info attributes[attributes_count];
 */
    private U2 major, minor;
    private ConstantPool constantPool;
    private U2 accessFlags;
    private U2 thisClass,superClass; //Direcciones de constantPool
    private Tabla<U2> interfaces; //tabla de direcciones de interfaces en constantPool
    private Tabla<CampoInfo> campos;
    private Tabla<MetodoInfo> metodos;
    private Tabla<AtributoInfo> atributos;

    public ClaseJVM(String className, U2 accessFlags, String padreName) {
        constantPool = new ConstantPool();
        interfaces = new Tabla<U2>();
        campos = new Tabla<CampoInfo>();
        metodos = new Tabla<MetodoInfo>();
        atributos = new Tabla<AtributoInfo>();
        thisClass = constantPool.nuevaClase(className);
        this.accessFlags = accessFlags;
        if(padreName != null)
            superClass = constantPool.nuevaClase(padreName);
    }

    public ClaseJVM(String className, U2 accessFlags) {
        this(className,accessFlags,null);
    }

    /**
     * @return the major
     */
    public U2 getMajor() {
        return major;
    }

    /**
     * @param major the major to set
     */
    public void setMajor(U2 major) {
        this.major = major;
    }

    /**
     * @return the minor
     */
    public U2 getMinor() {
        return minor;
    }

    /**
     * @param minor the minor to set
     */
    public void setMinor(U2 minor) {
        this.minor = minor;
    }

    public void nuevaInterfaz(String interfaceName) {
        interfaces.add(constantPool.nuevaClase(interfaceName));
    }

    /**
     * Añade un nuevo campo a la clase. El lenguaje a pila P no requiere campos,
     * por lo que se deja sin implementar los atributos del campo (que no son
     * necesarios para que la jvm acepte el .class)
     * @param identificador
     * @param tipo
     * @param accessFlags
     */
    public void nuevoCampo(String identificador, Class<?> tipo, U2 accessFlags) {
        U2 dirId = constantPool.nuevoUtf8(identificador);
        U2 dirTipo = constantPool.nuevoInternalUtf8(tipo);
        CampoInfo campo = new CampoInfo(dirId, dirTipo, accessFlags);
        campos.add(campo);
    }

    /**
     * Añade un nuevo metodo a la clase. No permite crear metodos con excepciones
     * o el atributo Deprecated
     * @param identificador
     * @param tipoDevuelto
     * @param tiposArgs
     * @param accessFlags
     * @param codigo
     * @param maxLocals numero maximo de variables locales + parametros. Para esta
     * cuenta los longs y doubles cuentan doble.
     */
    public void nuevoMetodo(String identificador, Class<?> tipoDevuelto, 
            Class<?>[] tiposArgs, U2 accessFlags, Tabla<InstJvm> codigo,
            int maxLocals) {
        U2 dirId = constantPool.nuevoUtf8(identificador);
        StringBuilder methodDescriptor = new StringBuilder();
        methodDescriptor.append("(");
        for(int i = 0; i < tiposArgs.length; i++) {
            methodDescriptor.append(constantPool.toInternalName(tiposArgs[i]));
        }
        methodDescriptor.append(")");
        methodDescriptor.append(constantPool.toInternalName(tipoDevuelto));
        U2 dirDescriptor = constantPool.nuevoUtf8(new String(methodDescriptor));

        AtributoInfo at = new CodeAttribute(dirId, codigo, maxLocals);

        MetodoInfo metodo = new MetodoInfo(dirId, dirDescriptor, accessFlags, at);
        metodos.add(metodo);
    }

    public void salvar(DataOutputStream dos) throws IOException {
        dos.writeInt(0xCAFEBABE);
        major.salvar(dos);
        minor.salvar(dos);
        constantPool.salvar(dos);
        accessFlags.salvar(dos);
        thisClass.salvar(dos);
        superClass.salvar(dos);
        interfaces.salvar(dos);
        campos.salvar(dos);
        metodos.salvar(dos);
        atributos.salvar(dos);
    }

    public int dameNumBytes() {
        return 4 + major.dameNumBytes() + minor.dameNumBytes()
                + constantPool.dameNumBytes() + accessFlags.dameNumBytes()
                + thisClass.dameNumBytes() + superClass.dameNumBytes()
                + interfaces.dameNumBytes() + campos.dameNumBytes()
                + metodos.dameNumBytes() + atributos.dameNumBytes();
    }

}
