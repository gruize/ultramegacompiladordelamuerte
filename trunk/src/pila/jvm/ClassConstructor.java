
package pila.jvm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class ClassConstructor {

    private ClassGen generator;
    private Codigo codigo;

    private int bufferedReaderIndex;
    private int readerIndex;
    private int printerIndex;
    private int readLineIndex;
    private int exceptionIndex;
    private int initExceptionIndex;
    private int natNegStringIndex;
    
    private int parseInt, parseBool, parseFloat,charAtIndex;

    public ClassConstructor() {
        String className = "Clase";

        generator = new ClassGen(className,"java.lang.Object",null,
                Constants.ACC_PUBLIC | Constants.ACC_FINAL, null);

        codigo = new Codigo();
        generator.addEmptyConstructor(Constants.ACC_PUBLIC);

        bufferedReaderIndex = 0;
        readLineIndex = 0;
        readerIndex = 0;
        printerIndex = 0;
        exceptionIndex = 0;
        initExceptionIndex = 0;
        natNegStringIndex = 0;
        parseBool = 0;
        parseFloat = 0;
        parseInt = 0;
        charAtIndex = 0;
    }

    private int getBufferedReaderIndex() {
        if(bufferedReaderIndex == 0)
            bufferedReaderIndex = generator.getConstantPool().addClass(
                BufferedReader.class.getName());
        return printerIndex;
    }

    private int getReaderFieldIndex() {
        if(readerIndex == 0) {
            generator.addField(
                    new FieldGen(
                        Constants.ACC_PRIVATE | Constants.ACC_STATIC,
                        new ObjectType(BufferedReader.class.getName()),
                        "reader",
                        generator.getConstantPool()
                    ).getField());
            readerIndex = generator.getConstantPool().addFieldref(
                generator.getClassName(),
                "reader",
                new ObjectType(BufferedReader.class.getName()).getSignature());
        }
        return readerIndex;
    }

    private int getPrinterFieldIndex() {
        if(printerIndex == 0)
            printerIndex = generator.getConstantPool().addFieldref(
                System.class.getName(),
                "out",
                new ObjectType(System.out.getClass().getName()).getSignature()
                );
        return printerIndex;
    }

    private int getInputStreamIndex() {
        return generator.getConstantPool().addClass(
                InputStreamReader.class.getName());
    }

    private int getSystemInIndex() {
        return generator.getConstantPool().addFieldref(
                System.class.getName(),
                "in",
                new ObjectType(InputStreamReader.class.getName()).getClassName());
    }

    private int getExceptionIndex() {
        if(exceptionIndex != 0)
            exceptionIndex = generator.getConstantPool().addClass(Exception.class.getName());
        return exceptionIndex;
    }

    private int getInitInputStreamReaderIndex() {
        return generator.getConstantPool().addMethodref(
                InputStreamReader.class.getName(),
                "<init>",
                Type.getMethodSignature(
                    Type.VOID,
                    new Type[]{new ObjectType(InputStream.class.getName())})
                );
    }

    private int getInitBufferedReaderIndex() {
        return generator.getConstantPool().addMethodref(
                BufferedReader.class.getName(),
                "<init>",
                Type.getMethodSignature(
                    Type.VOID,
                    new Type[]{new ObjectType(Reader.class.getName())})
                );
    }

    private int getInitExceptionIndex() {
        if(initExceptionIndex != 0)
            initExceptionIndex = generator.getConstantPool().addMethodref(
                    Exception.class.getName(),
                    "<init>",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.STRING})
                );
        return initExceptionIndex;
    }

    private int getNatNegStringIndex() {
        if(natNegStringIndex == 0)
            natNegStringIndex = generator.getConstantPool().addString("No puede asignarse a un natural un número negativo");
        return natNegStringIndex;
    }

    private int getParseIntIndex() {
        if(parseInt == 0)
            parseInt = generator.getConstantPool().addMethodref(
                Integer.class.getName(),
                "parseInt",
                Type.getMethodSignature(Type.INT, new Type[]{Type.STRING}));
        return parseInt;
    }

    private int getParseBoolIndex() {
        if(parseBool == 0)
            parseBool = generator.getConstantPool().addMethodref(
                Boolean.class.getName(),
                "parseBoolean",
                Type.getMethodSignature(Type.BOOLEAN, new Type[]{Type.STRING}));
        return parseBool;
    }

    private int getParseFloatIndex() {
        if(parseFloat == 0)
            parseFloat = generator.getConstantPool().addMethodref(
                Float.class.getName(),
                "parseFloat",
                Type.getMethodSignature(Type.FLOAT, new Type[]{Type.STRING}));
        return parseFloat;
    }

    private int getCharAtIndex() {
        if(charAtIndex == 0)
            charAtIndex = generator.getConstantPool().addMethodref(
                    String.class.getName(),
                    "charAt",
                    Type.getMethodSignature(Type.CHAR, new Type[]{Type.INT}));
        return charAtIndex;
    }


    /**
     * Añade al programa el codigo necesario para leer una linea por la
     * entrada estandar. Al ejecutar el código el String quedará en la cima de
     * la pila
     */
    public void leerLinea() {
        if(readLineIndex == 0) { //ponemos en la constant pool el metodo readLine
            readLineIndex = generator.getConstantPool().addMethodref(
                    BufferedReader.class.getName(),
                    "readLine",
                    Type.getMethodSignature(Type.STRING, null));
            
            //ademas readLineIndex == 0 indica que no hemos hecho ninguna lectura
            //por lo que no esta iniciado el field reader
            //new BufferedReader
            codigo.añadirU1(Constants.NEW);
            codigo.añadirU2(getBufferedReaderIndex());
            //se duplica la entrada (primero se usa para la constructora y luego
            //para ponerlo en la variable reader
            codigo.añadirU1(Constants.DUP);
            //new InputStreamReader
            codigo.añadirU1(Constants.NEW);
            codigo.añadirU2(getInputStreamIndex());
            //se duplica la entrada. Se usa primero para la constructora propia
            //y luego para la construra del BufferedReader
            codigo.añadirU1(Constants.DUP);
            //getstatic	#3; //Field java/lang/System.in:Ljava/io/InputStream;
            codigo.añadirU1(Constants.GETSTATIC);
            codigo.añadirU2(getSystemInIndex());
            //invokespecial	#4; //Method java/io/InputStreamReader."<init>":(Ljava/io/InputStream;)V
            codigo.añadirU1(Constants.INVOKESPECIAL);
            codigo.añadirU2(getInitInputStreamReaderIndex());
            //invokespecial	#8; //Method java/io/BufferedReader."<init>":(Ljava/io/Reader;)V
            codigo.añadirU1(Constants.INVOKESPECIAL);
            codigo.añadirU2(getInitBufferedReaderIndex());
            //putstatic	#5; //Field reader:Ljava/io/Reader;
            codigo.añadirU1(Constants.PUTSTATIC);
            codigo.añadirU2(getReaderFieldIndex());
        }
        codigo.añadirU1(Constants.GETSTATIC);
        codigo.añadirU2(readerIndex);
        codigo.añadirU1(Constants.INVOKEVIRTUAL);
        codigo.añadirU2(readLineIndex);
    }

    /**
     * Añade al program el codigo necesario para leer un entero por la entrada
     * estandar. Al ejecutar el codigo el entero estará en la cima de la pila
     */
    public void leerInt() {
        leerLinea();
        codigo.añadirU1(Constants.INVOKESTATIC);
        codigo.añadirU2(getParseIntIndex());
    }

    /**
     * Añade al program el codigo necesario para leer un natural por la entrada
     * estandar. Al ejecutar el codigo el natural estará en la cima de la pila
     */
    public void leerNat() {
        leerLinea();
        codigo.añadirU1(Constants.INVOKESTATIC);
        codigo.añadirU2(getParseIntIndex());
        codigo.añadirU1(Constants.DUP); //gastamos 1 para el if y el otro es el resultado
        codigo.añadirU1(Constants.IF_ICMPGE);
        codigo.añadirU2(13+codigo.size()); //2 => saltamos hasta despues del throw
        codigo.añadirU1(Constants.NEW); //3
        codigo.añadirU2(getExceptionIndex()); //5
        codigo.añadirU1(Constants.DUP); //6
        codigo.añadirU1(Constants.LDC); //7 => se carga el string
        codigo.añadirU2(getNatNegStringIndex()); //9
        codigo.añadirU1(Constants.INVOKESPECIAL); //10
        codigo.añadirU2(getInitExceptionIndex()); //12
        codigo.añadirU1(Constants.ATHROW); //13
    }

    /**
     * Añade al program ael codigo necesario para leer un booleano por la entrada
     * estandar. Al ejecutar el codigo el boolean estará en la cima de la pila
     */
    public void leerBool() {
        leerLinea();
        codigo.añadirU1(Constants.INVOKESTATIC);
        codigo.añadirU2(getParseBoolIndex());
    }

    /**
     * Añade al program el codigo necesario para leer un float por la entrada
     * estandar. Al ejecutar el codigo el float estará en la cima de la pila
     */
    public void leerFloat() {
        leerLinea();
        codigo.añadirU1(Constants.INVOKESTATIC);
        codigo.añadirU2(getParseFloatIndex());
    }

    /**
     * Añade al program el codigo necesario para leer un char por la entrada
     * estandar. Al ejecutar el codigo el char estará en la cima de la pila
     */
    public void leerChar() {
        leerLinea();
        codigo.añadirU1(Constants.ICONST_0);
        codigo.añadirU1(Constants.INVOKEVIRTUAL);
        codigo.añadirU2(getCharAtIndex());
    }

    public void saltar(int insSalto, int desplazamiento) {
        codigo.añadirU1(insSalto);
        codigo.añadirU2(desplazamiento + codigo.size());
    }

    public void añadirU1(int i) {
        codigo.añadirU1(i);
    }

    public void añadirU2(int i) {
        añadirU2(i);
    }

    public void añadirU4(int i) {
        añadirU4(i);
    }

    public byte[] getByteCode() {
        return codigo.getByteCode();
    }

    public JavaClass getJavaClass(int maxVars, int maxStack) {
        generator.addMethod(new Method(
                Constants.ACC_PUBLIC | Constants.ACC_STATIC,
                generator.getConstantPool().addUtf8("main"),
                generator.getConstantPool().addUtf8(
                        Type.getMethodSignature(Type.VOID, new Type[]{new ArrayType(Type.STRING, 1)})
                    ),
                new Attribute[] {new Code(
                        generator.getConstantPool().addUtf8("Code"),
                        0,//da igual, se recalcula en la constructora
                        maxStack,
                        maxVars,
                        codigo.getByteCode(),
                        null,
                        null,
                        generator.getConstantPool().getConstantPool())},
                generator.getConstantPool().getConstantPool()
            ));
        return generator.getJavaClass();
    }

    private class Codigo {
        private ArrayList<Byte> codigo;

        public Codigo() {
            codigo = new ArrayList<Byte>();
        }

        public void añadirU1(int i) {
            codigo.add((byte)i);
        }

        public void añadirU2(int i) {
            añadirU1(i >> 8);
            añadirU1(i);
        }

        public void añadirU4(int i) {
            añadirU2(i >> 16);
            añadirU2(i);
        }

        public byte[] getByteCode() {
            byte[] resp = new byte[size()];
            for(int i = 0; i < resp.length; i++)
                resp[i] = codigo.get(i);
            return resp;
        }

        public int size() {
            return codigo.size();
        }

    }


}
