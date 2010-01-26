
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
            printZIndex, printFIndex, printCIndex, printIIndex;

    private HashMap<Object,Integer> constantes; //key puede ser Integer o Float
    private int orMethodIndex;
    private int andMethodIndex;
    private int absFloatMethodIndex;
    private int notMethodIndex;
    private int absIntMethodIndex;

    public ClassConstructor(String nombreClase) {
        generator = new ClassGen(nombreClase,"java.lang.Object",null,
                Constants.ACC_PUBLIC | Constants.ACC_FINAL, null);

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
        printZIndex = 0;
        printFIndex = 0;
        printCIndex = 0;
        printIIndex = 0;
        orMethodIndex = 0;
        andMethodIndex = 0;
        notMethodIndex = 0;
        absFloatMethodIndex = 0;
        absIntMethodIndex = 0;

        generator.addEmptyConstructor(Constants.ACC_PRIVATE);
        /*
         * Ahora creamos el static que inicie el atributo
         * reader, que se usara para las instrucciones de lectura
         */
        codigo = new Codigo();
        //Codigo para iniciar el field reader
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
        //cargamos la referencia a System.in
        codigo.añadirU1(Constants.GETSTATIC); 
        codigo.añadirU2(getSystemInIndex());
        //llamamos a la constructora de InputStreamReader con el System.in como arg
        codigo.añadirU1(Constants.INVOKESPECIAL);
        codigo.añadirU2(getInitInputStreamReaderIndex());
        //llamamos a la constructora de BufferedReader con el nuevo InputStreamReader como arg
        codigo.añadirU1(Constants.INVOKESPECIAL);
        codigo.añadirU2(getInitBufferedReaderIndex());
        //guardamos el BufferedReader en su field
        codigo.añadirU1(Constants.PUTSTATIC);
        codigo.añadirU2(getReaderFieldIndex());
        //acabamos la constructora
        codigo.añadirU1(Constants.RETURN);
        generator.addMethod(new Method(
                Constants.ACC_PUBLIC | Constants.ACC_STATIC, //visibilidad
                generator.getConstantPool().addUtf8("<clinit>"), //nombre
                generator.getConstantPool().addUtf8("()V"), //firma
                new Attribute[] {new Code( //codigo
                        generator.getConstantPool().addUtf8("Code"),
                        0, //el tamaño en U1s se rellena solo
                        5, //altura maxima de la pila
                        1, //usamos solo el "argumento" this
                        codigo.getByteCode(), //el codigpo que tendra
                        null, //no captura excepciones
                        null, //no tiene atributos
                        generator.getConstantPool().getConstantPool()
                )},
                generator.getConstantPool().getConstantPool()
            ));
        codigo = new Codigo(); //creamos uno nuevo donde guardaremos el programa a ejecutar
    }

    public int getBufferedReaderIndex() {
        if(bufferedReaderIndex == 0)
            bufferedReaderIndex = generator.getConstantPool().addClass(
                BufferedReader.class.getName());
        return bufferedReaderIndex;
    }

    public int getReaderFieldIndex() {
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

    public int getPrinterFieldIndex() {
        if(printerIndex == 0)
            printerIndex = generator.getConstantPool().addFieldref(
                System.class.getName(),
                "out",
                new ObjectType(System.out.getClass().getName()).getSignature()
                );
        return printerIndex;
    }

    public int getInputStreamIndex() {
        return generator.getConstantPool().addClass(
                InputStreamReader.class.getName());
    }

    public int getSystemInIndex() {
        return generator.getConstantPool().addFieldref(
                System.class.getName(),
                "in",
                new ObjectType(InputStream.class.getName()).getSignature());
    }

    public int getExceptionIndex() {
        if(exceptionIndex == 0)
            exceptionIndex = generator.getConstantPool().addClass(Exception.class.getName());
        return exceptionIndex;
    }

    public int getInitInputStreamReaderIndex() {
        return generator.getConstantPool().addMethodref(
                InputStreamReader.class.getName(),
                "<init>",
                Type.getMethodSignature(
                    Type.VOID,
                    new Type[]{new ObjectType(InputStream.class.getName())})
                );
    }

    public int getInitBufferedReaderIndex() {
        return generator.getConstantPool().addMethodref(
                BufferedReader.class.getName(),
                "<init>",
                Type.getMethodSignature(
                    Type.VOID,
                    new Type[]{new ObjectType(Reader.class.getName())})
                );
    }

    public int getInitExceptionIndex() {
        if(initExceptionIndex == 0)
            initExceptionIndex = generator.getConstantPool().addMethodref(
                    Exception.class.getName(),
                    "<init>",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.STRING})
                );
        return initExceptionIndex;
    }

    public int getNatNegErrorStringIndex() {
        if(natNegStringIndex == 0)
            natNegStringIndex = generator.getConstantPool().addString("No puede asignarse a un natural un número negativo");
        return natNegStringIndex;
    }

    public int getParseIntIndex() {
        if(parseInt == 0)
            parseInt = generator.getConstantPool().addMethodref(
                Integer.class.getName(),
                "parseInt",
                Type.getMethodSignature(Type.INT, new Type[]{Type.STRING}));
        return parseInt;
    }

    public int getParseBoolIndex() {
        if(parseBool == 0)
            parseBool = generator.getConstantPool().addMethodref(
                Boolean.class.getName(),
                "parseBoolean",
                Type.getMethodSignature(Type.BOOLEAN, new Type[]{Type.STRING}));
        return parseBool;
    }

    public int getParseFloatIndex() {
        if(parseFloat == 0)
            parseFloat = generator.getConstantPool().addMethodref(
                Float.class.getName(),
                "parseFloat",
                Type.getMethodSignature(Type.FLOAT, new Type[]{Type.STRING}));
        return parseFloat;
    }

    public int getCharAtIndex() {
        if(charAtIndex == 0)
            charAtIndex = generator.getConstantPool().addMethodref(
                    String.class.getName(),
                    "charAt",
                    Type.getMethodSignature(Type.CHAR, new Type[]{Type.INT}));
        return charAtIndex;
    }
    
    public int getPrintZIndex() {
        if(printZIndex == 0)
            printZIndex = generator.getConstantPool().addMethodref(
                    PrintStream.class.getName(),
                    "print",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.BOOLEAN}));
        return printZIndex;
    }

    public int getPrintIIndex() {
        if(printIIndex == 0)
            printIIndex = generator.getConstantPool().addMethodref(
                    PrintStream.class.getName(),
                    "print",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.INT}));
        return printIIndex;
    }

    public int getPrintCIndex() {
        if(printCIndex == 0)
            printCIndex = generator.getConstantPool().addMethodref(
                    PrintStream.class.getName(),
                    "print",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.CHAR}));
        return printCIndex;
    }

    public int getPrintFIndex() {
        if(printFIndex == 0)
            printFIndex = generator.getConstantPool().addMethodref(
                    PrintStream.class.getName(),
                    "print",
                    Type.getMethodSignature(Type.VOID, new Type[]{Type.FLOAT}));
        return printFIndex;
    }

    public int getConstanteIndex(int i) {
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

    public int getReadLineIndex() {
        if(readLineIndex == 0) { //ponemos en la constant pool el metodo readLine
            readLineIndex = generator.getConstantPool().addMethodref(
                    BufferedReader.class.getName(),
                    "readLine",
                    Type.getMethodSignature(Type.STRING, null));
        }
        return readLineIndex;
    }
    
    public void añadirU1(int i) {
        codigo.añadirU1(i);
    }

    public void añadirU2(int i) {
        codigo.añadirU2(i);
    }

    public void añadirU4(int i) {
        codigo.añadirU4(i);
    }

    public byte[] getByteCode() {
        return codigo.getByteCode();
    }

    public int getOrMethodIndex() {
        if(orMethodIndex == 0) {
            final String signature = Type.getMethodSignature(Type.BOOLEAN, new Type[]{Type.BOOLEAN,Type.BOOLEAN});
            final String nombreMethodo = "oLogica";

            orMethodIndex = generator.getConstantPool().addMethodref(
                    generator.getClassName(),
                    nombreMethodo,
                    signature
                );

            Codigo codigoOr = new Codigo();
            codigoOr.añadirU1(Constants.ILOAD_0); //apilamos el 1er argumento
            codigoOr.añadirU1(Constants.IFNE); //si es != 0 saltamos a iconst_1
            codigoOr.añadirU2(7);
            codigoOr.añadirU1(Constants.ILOAD_1); //si era == 0 apilamos el 2 arg
            codigoOr.añadirU1(Constants.IFEQ); //si es == 0, saltamos a iconst_0
            codigoOr.añadirU2(7);
            codigoOr.añadirU1(Constants.ICONST_1); //apilamos 1 (cierto)
            codigoOr.añadirU1(Constants.GOTO); //saltamos al ireturn
            codigoOr.añadirU2(4);
            codigoOr.añadirU1(Constants.ICONST_0); //apilamos 0 (falso)
            codigoOr.añadirU1(Constants.IRETURN); //devolvemos el valor apilado

            generator.addMethod(new Method(
                    Constants.ACC_PUBLIC | Constants.ACC_STATIC,
                    generator.getConstantPool().addUtf8(nombreMethodo),
                    generator.getConstantPool().addUtf8(signature),
                    new Attribute[] { new Code(
                            generator.getConstantPool().addUtf8("Code"),
                            0,
                            1, //se apila a la vez hasta 1 argumento
                            2, //se tienen dos argumentos y ninguna variable local
                            codigoOr.getByteCode(),
                            null,
                            null,
                            generator.getConstantPool().getConstantPool()
                        )
                    },
                    generator.getConstantPool().getConstantPool()
            ));
        }
        return orMethodIndex;
    }

    public int getAndMethodIndex() {
        if(andMethodIndex == 0) {
            final String signature = Type.getMethodSignature(Type.BOOLEAN, new Type[]{Type.BOOLEAN,Type.BOOLEAN});
            final String nombreMethodo = "yLogica";
            andMethodIndex = generator.getConstantPool().addMethodref(
                    generator.getClassName(),
                    nombreMethodo,
                    signature
                );

            Codigo codigoAnd = new Codigo();
            codigoAnd.añadirU1(Constants.ILOAD_0); //apilamos el 1er arg
            codigoAnd.añadirU1(Constants.IFEQ);//si == 0 => saltamos a iconst_0
            codigoAnd.añadirU2(11);
            codigoAnd.añadirU1(Constants.ILOAD_1);
            codigoAnd.añadirU1(Constants.IFEQ);
            codigoAnd.añadirU2(7);
            codigoAnd.añadirU1(Constants.ICONST_1);
            codigoAnd.añadirU1(Constants.GOTO);
            codigoAnd.añadirU2(4);
            codigoAnd.añadirU1(Constants.ICONST_0);
            codigoAnd.añadirU1(Constants.IRETURN);

            generator.addMethod(new Method(
                    Constants.ACC_PUBLIC | Constants.ACC_STATIC,
                    generator.getConstantPool().addUtf8(nombreMethodo),
                    generator.getConstantPool().addUtf8(signature),
                    new Attribute[] { new Code(
                            generator.getConstantPool().addUtf8("Code"),
                            0,
                            1, //se apila a la vez hasta 1 argumento
                            2, //se tienen dos argumentos y ninguna variable local
                            codigoAnd.getByteCode(),
                            null,
                            null,
                            generator.getConstantPool().getConstantPool()
                        )
                    },
                    generator.getConstantPool().getConstantPool()
            ));
        }
        return andMethodIndex;
    }

    public int getNotMethodIndex() {
        if(notMethodIndex == 0) {
            final String signature = Type.getMethodSignature(Type.BOOLEAN, new Type[]{Type.BOOLEAN});
            final String nombreMethodo = "noLogica";
            notMethodIndex = generator.getConstantPool().addMethodref(
                    generator.getClassName(),
                    nombreMethodo,
                    signature
                );

            Codigo codigoNot = new Codigo();
            codigoNot.añadirU1(Constants.ILOAD_0); //apilamos el primer arg
            codigoNot.añadirU1(Constants.IFEQ); //si es falso saltamos a iconst_1
            codigoNot.añadirU2(7);
            codigoNot.añadirU1(Constants.ICONST_0); //si era cierto apilamos 0 (falso)
            codigoNot.añadirU1(Constants.GOTO); //saltamos al return
            codigoNot.añadirU2(4);
            codigoNot.añadirU1(Constants.ICONST_1); //apilamos 1 (cierto)
            codigoNot.añadirU1(Constants.IRETURN);

            generator.addMethod(new Method(
                    Constants.ACC_PUBLIC | Constants.ACC_STATIC,
                    generator.getConstantPool().addUtf8(nombreMethodo),
                    generator.getConstantPool().addUtf8(signature),
                    new Attribute[] { new Code(
                            generator.getConstantPool().addUtf8("Code"),
                            0,
                            1, //se apila 1 argumento 1 unica vez
                            1, //se tiene 1 argumento y ninguna variable local
                            codigoNot.getByteCode(),
                            null,
                            null,
                            generator.getConstantPool().getConstantPool()
                        )
                    },
                    generator.getConstantPool().getConstantPool()
            ));
        }
        return notMethodIndex;
    }

    public int getAbsIntMethodIndex() {
        if(absIntMethodIndex == 0) {
            final String signature = Type.getMethodSignature(Type.INT, new Type[]{Type.INT});
            final String nombreMethodo = "absInt";
            absIntMethodIndex = generator.getConstantPool().addMethodref(
                    generator.getClassName(),
                    nombreMethodo,
                    signature
                );

            Codigo codigoAbsInt = new Codigo();
            codigoAbsInt.añadirU1(Constants.ILOAD_0);//apilamos el argumento
            codigoAbsInt.añadirU1(Constants.DUP); //clonamos la cima
            codigoAbsInt.añadirU1(Constants.IFGE); //saltamos a IRETURN si la cima es mayor
            codigoAbsInt.añadirU2(4);
            codigoAbsInt.añadirU1(Constants.INEG); //si no era mayor negamos la cima (el primer apilar)
            codigoAbsInt.añadirU1(Constants.IRETURN); //devolvemos el valor

            generator.addMethod(new Method(
                    Constants.ACC_PUBLIC | Constants.ACC_STATIC,
                    generator.getConstantPool().addUtf8(nombreMethodo),
                    generator.getConstantPool().addUtf8(signature),
                    new Attribute[] { new Code(
                            generator.getConstantPool().addUtf8("Code"),
                            0,
                            2, //se apila 1 argumento 1 unica vez y se clona
                            1, //se tiene 1 argumento y ninguna variable local
                            codigoAbsInt.getByteCode(),
                            null,
                            null,
                            generator.getConstantPool().getConstantPool()
                        )
                    },
                    generator.getConstantPool().getConstantPool()
            ));
        }
        return absIntMethodIndex;
    }

    public int getAbsFloatMethodIndex() {
        if(absFloatMethodIndex == 0) {
            final String signature = Type.getMethodSignature(Type.FLOAT, new Type[]{Type.FLOAT});
            final String nombreMethodo = "absFloat";
            absFloatMethodIndex = generator.getConstantPool().addMethodref(
                    generator.getClassName(),
                    nombreMethodo,
                    signature
                );

            Codigo codigoAbsInt = new Codigo();
            codigoAbsInt.añadirU1(Constants.FLOAD_0); //apilamos el arg
            codigoAbsInt.añadirU1(Constants.DUP); //clonamos la cima
            codigoAbsInt.añadirU1(Constants.FCONST_0); //apilamos un 0
            codigoAbsInt.añadirU1(Constants.FCMPG); //comparamos los valores
            codigoAbsInt.añadirU1(Constants.IFGE); //si era >= 0 saltamos al return
            codigoAbsInt.añadirU2(4);
            codigoAbsInt.añadirU1(Constants.FNEG); //si era < 0 negamos el arg
            codigoAbsInt.añadirU1(Constants.FRETURN);

            generator.addMethod(new Method(
                    Constants.ACC_PUBLIC | Constants.ACC_STATIC,
                    generator.getConstantPool().addUtf8(nombreMethodo),
                    generator.getConstantPool().addUtf8(signature),
                    new Attribute[] { new Code(
                            generator.getConstantPool().addUtf8("Code"),
                            0,
                            3, //se apila 1 argumento 1 unica vez, se clona y luego se apila una constante
                            1, //se tiene 1 argumento y ninguna variable local
                            codigoAbsInt.getByteCode(),
                            null,
                            null,
                            generator.getConstantPool().getConstantPool()
                        )
                    },
                    generator.getConstantPool().getConstantPool()
            ));
        }
        return absFloatMethodIndex;
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
                        maxVars+1,//+1 del argumento
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
