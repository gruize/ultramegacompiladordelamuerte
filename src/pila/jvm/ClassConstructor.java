
package pila.jvm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Attribute;
import org.apache.bcel.classfile.Code;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.FieldGen;
import org.apache.bcel.generic.ObjectType;
import org.apache.bcel.generic.Type;
import pila.interprete.datos.DatoPila;

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
    
    private int parseInt, parseBool, parseFloat, charAtIndex,
            printlnZIndex, printlnFIndex, printlnCIndex, printlnIIndex;

    private HashMap<Object,Integer> constantes; //key puede ser Integer o Float

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
        printlnZIndex = 0;
        printlnFIndex = 0;
        printlnCIndex = 0;
        printlnIIndex = 0;
    }

    private int getBufferedReaderIndex() {
        if(bufferedReaderIndex == 0)
            bufferedReaderIndex = generator.getConstantPool().addClass(
                BufferedReader.class.getName());
        return bufferedReaderIndex;
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
                new ObjectType(InputStream.class.getName()).getSignature());
    }

    private int getExceptionIndex() {
        if(exceptionIndex == 0)
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
        if(initExceptionIndex == 0)
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
    
    private int getPrintlnZIndex() {
        if(printlnZIndex == 0)
            printlnZIndex = generator.getConstantPool().addMethodref(
                    PrintStream.class.getName(),
                    "println",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.BOOLEAN}));
        return printlnZIndex;
    }

    private int getPrintlnIIndex() {
        if(printlnIIndex == 0)
            printlnIIndex = generator.getConstantPool().addMethodref(
                    PrintStream.class.getName(),
                    "println",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.INT}));
        return printlnIIndex;
    }

    private int getPrintlnCIndex() {
        if(printlnCIndex == 0)
            printlnCIndex = generator.getConstantPool().addMethodref(
                    PrintStream.class.getName(),
                    "println",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.CHAR}));
        return printlnCIndex;
    }

    private int getPrintlnFIndex() {
        if(printlnFIndex == 0)
            printlnFIndex = generator.getConstantPool().addMethodref(
                    PrintStream.class.getName(),
                    "println",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.FLOAT}));
        return printlnFIndex;
    }

    private int getConstanteIndex(int i) {
        Integer dir;
        if(constantes == null) {
            constantes = new HashMap<Object, Integer>();
        }
        dir = constantes.get(i);
        if(dir == null) { //no teniamos posicion para este valor
            dir = generator.getConstantPool().addInteger(i);
            constantes.put(i, dir);
        }
        return dir;
    }

    public int getConstanteIndex(float f) {
        Integer dir;
        if(constantes == null) {
            constantes = new HashMap<Object, Integer>();
        }
        dir = constantes.get(f);
        if(dir == null) { //no teniamos posicion para este valor
            dir = generator.getConstantPool().addFloat(f);
            constantes.put(f, dir);
        }
        return dir;
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
        codigo.añadirU1(Constants.IFGE);
        codigo.añadirU2(13); //2 => saltamos hasta despues del throw
        codigo.añadirU1(Constants.NEW); //3
        codigo.añadirU2(getExceptionIndex()); //5
        codigo.añadirU1(Constants.DUP); //6
        codigo.añadirU1(Constants.LDC); //7 => se carga el string
        codigo.añadirU1(getNatNegStringIndex()); //9
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

    public void cargarDato(int tipoDato, int direccion) throws Exception {
        switch (tipoDato) {
            case DatoPila.BOOL_T:
                codigo.añadirU1(Constants.I2B);
                switch(direccion) {
                    case 0:
                        codigo.añadirU1(Constants.ILOAD_0);
                        break;
                    case 1:
                        codigo.añadirU1(Constants.ILOAD_1);
                        break;
                    case 2:
                        codigo.añadirU1(Constants.ILOAD_2);
                        break;
                    case 3:
                        codigo.añadirU1(Constants.ILOAD_3);
                        break;
                    default:
                        codigo.añadirU1(Constants.ILOAD);
                        codigo.añadirU1(direccion);
                }

                break;
            case DatoPila.CHAR_T:
                codigo.añadirU1(Constants.I2C);
                switch(direccion) {
                    case 0:
                        codigo.añadirU1(Constants.ILOAD_0);
                        break;
                    case 1:
                        codigo.añadirU1(Constants.ILOAD_1);
                        break;
                    case 2:
                        codigo.añadirU1(Constants.ILOAD_2);
                        break;
                    case 3:
                        codigo.añadirU1(Constants.ILOAD_3);
                        break;
                    default:
                        codigo.añadirU1(Constants.ILOAD);
                        codigo.añadirU1(direccion);
                }
                break;
            case DatoPila.FLOAT_T:
                switch(direccion) {
                    case 0:
                        codigo.añadirU1(Constants.FLOAD_0);
                        break;
                    case 1:
                        codigo.añadirU1(Constants.FLOAD_1);
                        break;
                    case 2:
                        codigo.añadirU1(Constants.FLOAD_2);
                        break;
                    case 3:
                        codigo.añadirU1(Constants.FLOAD_3);
                        break;
                    default:
                        codigo.añadirU1(Constants.FLOAD);
                        codigo.añadirU1(direccion);
                }
                break;
            case DatoPila.INT_T:
            case DatoPila.NAT_T:
                switch(direccion) {
                    case 0:
                        codigo.añadirU1(Constants.ILOAD_0);
                        break;
                    case 1:
                        codigo.añadirU1(Constants.ILOAD_1);
                        break;
                    case 2:
                        codigo.añadirU1(Constants.ILOAD_2);
                        break;
                    case 3:
                        codigo.añadirU1(Constants.ILOAD_3);
                        break;
                    default:
                        codigo.añadirU1(Constants.ILOAD);
                        codigo.añadirU1(direccion);
                }
                break;
            default:
                throw new Exception("Tipo de dato a escribir inválido");
        }
    }

    public void guardarDato(int tipoDato, int direccion) throws Exception {
        switch (tipoDato) {
            case DatoPila.BOOL_T:
                codigo.añadirU1(Constants.I2B);
                switch(direccion) {
                    case 0:
                        codigo.añadirU1(Constants.ISTORE_0);
                        break;
                    case 1:
                        codigo.añadirU1(Constants.ISTORE_1);
                        break;
                    case 2:
                        codigo.añadirU1(Constants.ISTORE_2);
                        break;
                    case 3:
                        codigo.añadirU1(Constants.ISTORE_3);
                        break;
                    default:
                        codigo.añadirU1(Constants.ISTORE);
                        codigo.añadirU1(direccion);
                }

                break;
            case DatoPila.CHAR_T:
                codigo.añadirU1(Constants.I2C);
                switch(direccion) {
                    case 0:
                        codigo.añadirU1(Constants.ISTORE_0);
                        break;
                    case 1:
                        codigo.añadirU1(Constants.ISTORE_1);
                        break;
                    case 2:
                        codigo.añadirU1(Constants.ISTORE_2);
                        break;
                    case 3:
                        codigo.añadirU1(Constants.ISTORE_3);
                        break;
                    default:
                        codigo.añadirU1(Constants.ISTORE);
                        codigo.añadirU1(direccion);
                }
                break;
            case DatoPila.FLOAT_T:
                switch(direccion) {
                    case 0:
                        codigo.añadirU1(Constants.FSTORE_0);
                        break;
                    case 1:
                        codigo.añadirU1(Constants.FSTORE_1);
                        break;
                    case 2:
                        codigo.añadirU1(Constants.FSTORE_2);
                        break;
                    case 3:
                        codigo.añadirU1(Constants.FSTORE_3);
                        break;
                    default:
                        codigo.añadirU1(Constants.FSTORE);
                        codigo.añadirU1(direccion);
                }
                break;
            case DatoPila.INT_T:
            case DatoPila.NAT_T:
                switch(direccion) {
                    case 0:
                        codigo.añadirU1(Constants.ISTORE_0);
                        break;
                    case 1:
                        codigo.añadirU1(Constants.ISTORE_1);
                        break;
                    case 2:
                        codigo.añadirU1(Constants.ISTORE_2);
                        break;
                    case 3:
                        codigo.añadirU1(Constants.ISTORE_3);
                        break;
                    default:
                        codigo.añadirU1(Constants.ISTORE);
                        codigo.añadirU1(direccion);
                }
                break;
            default:
                throw new Exception("Tipo de dato a escribir inválido");
        }
    }

    /**
     * Añade al programa el código necesario para leer de la entrada estandar
     * un tipo de dato dado y para guardarlo en una direccion de memoria concreta.
     * Deja intacta la pila
     * @param tipo tipo del dato a leer, tal como se definen en DatoPila
     * @param dir direccion de memoria del dato a leer
     * @throws Exception si el tipo es inválido
     * @see DatoPila
     */
    public void leerDato(int tipo, int dir) throws Exception {
        switch (tipo) {
            case DatoPila.BOOL_T:
                leerBool();
                break;
            case DatoPila.CHAR_T:
                leerChar();
                break;
            case DatoPila.FLOAT_T:
                leerFloat();
                break;
            case DatoPila.INT_T:
                leerInt();
                break;
            case DatoPila.NAT_T:
                leerNat();
                break;
            default:
                throw new Exception("Tipo de dato a escribir inválido");
        }
        guardarDato(tipo, dir);
    }

    public void apilarPrinter() {
        codigo.añadirU1(Constants.GETSTATIC);
        codigo.añadirU2(getPrinterFieldIndex());
    }

    public void escribirDato(int tipo) throws Exception {
        int dirMetodo;
        switch (tipo) {
            case DatoPila.BOOL_T:
                dirMetodo = getPrintlnZIndex();
                break;
            case DatoPila.CHAR_T:
                dirMetodo = getPrintlnCIndex();
                break;
            case DatoPila.FLOAT_T:
                dirMetodo = getPrintlnFIndex();
                break;
            case DatoPila.INT_T:
                dirMetodo = getPrintlnIIndex();
                break;
            case DatoPila.NAT_T:
                dirMetodo = getPrintlnIIndex();
                break;
            default:
                throw new Exception("Tipo de dato a escribir inválido");
        }
        codigo.añadirU1(Constants.INVOKEVIRTUAL);
        codigo.añadirU2(dirMetodo);


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
        if(codigo.getByte(codigo.size()-1) != Constants.RETURN)
            codigo.añadirU1(Constants.RETURN);
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

        private byte getByte(int i) {
            return codigo.get(i);
        }

    }


}
