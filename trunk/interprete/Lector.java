package interprete;

import interprete.instrucciones.InstruccionPila;
import interprete.instrucciones.Menor;
import interprete.instrucciones.Shr;
import interprete.instrucciones.NoIgual;
import interprete.instrucciones.Menos;
import interprete.instrucciones.Mayor;
import interprete.instrucciones.Resta;
import interprete.instrucciones.O;
import interprete.instrucciones.MayorIg;
import interprete.instrucciones.Shl;
import interprete.instrucciones.Multiplica;
import interprete.instrucciones.MenorIg;
import interprete.instrucciones.No;
import interprete.instrucciones.Modulo;
import interprete.instrucciones.Suma;
import interprete.instrucciones.ApilarDir;
import interprete.instrucciones.CastChar;
import interprete.instrucciones.Divide;
import interprete.instrucciones.CastFloat;
import interprete.instrucciones.CastInt;
import interprete.instrucciones.Apilar;
import interprete.instrucciones.DesapilarDir;
import interprete.instrucciones.Desapilar;
import interprete.instrucciones.Igual;
import interprete.instrucciones.Y;
import interprete.instrucciones.Error;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
/**
 *
 * @author ruben
 */

public class Lector {

    public static final byte CODIGO_APILAR = (byte) 1;
    public static final byte CODIGO_APILARDIR = (byte) 2;
    public static final byte CODIGO_DESAPILAR = (byte) 3;
    public static final byte CODIGO_DESAPILARDIR = (byte) 4;
    public static final byte CODIGO_MENOR = (byte) 5;
    public static final byte CODIGO_MAYOR = (byte) 6;
    public static final byte CODIGO_MENORIG = (byte) 7;
    public static final byte CODIGO_MAYORIG = (byte) 8;
    public static final byte CODIGO_IGUAL = (byte) 9;
    public static final byte CODIGO_NOIGUAL = (byte) 10;
    public static final byte CODIGO_SUMA = (byte) 11;
    public static final byte CODIGO_RESTA = (byte) 12;
    public static final byte CODIGO_MULTIPLICA = (byte) 13;
    public static final byte CODIGO_DIVIDE = (byte) 14;
    public static final byte CODIGO_MODULO = (byte) 15;
    public static final byte CODIGO_Y = (byte) 16;
    public static final byte CODIGO_O = (byte) 17;
    public static final byte CODIGO_NO = (byte) 18;
    public static final byte CODIGO_MENOS = (byte) 19;
    public static final byte CODIGO_SHL = (byte) 20;
    public static final byte CODIGO_SHR = (byte) 21;
    public static final byte CODIGO_CASTINT = (byte) 22;
    public static final byte CODIGO_CASTCHAR = (byte) 23;
    public static final byte CODIGO_CASTFLOAT = (byte) 24;

    public static final byte CODIGO_BOOLEAN= (byte) 1;
    public static final byte CODIGO_CHAR= (byte) 2;
    public static final byte CODIGO_NATURAL= (byte) 8;
    public static final byte CODIGO_INTEGER= (byte) 9;
    public static final byte CODIGO_FLOAT= (byte) 10;


    public static ArrayList<InstruccionPila> leerFichero(String nomFich) throws IOException {
        ArrayList<InstruccionPila> sal=new ArrayList<InstruccionPila>();
        InstruccionPila inst = null;
        byte operador;
        byte tipo;

        try {
            DataOutputStream fichero1 = new DataOutputStream(new FileOutputStream(nomFich));
            fichero1.writeByte((byte)1);
            fichero1.writeByte((byte)8);
            fichero1.writeInt(4);
            fichero1.writeByte((byte)1);
            fichero1.writeByte((byte)8);
            fichero1.writeInt(4);
            fichero1.writeByte((byte)11);
            fichero1.writeByte((byte)1);
            fichero1.writeByte((byte)10);
            fichero1.writeFloat((float) 4.56);
            fichero1.writeByte((byte)1);
            fichero1.writeByte((byte)10);
            fichero1.writeFloat((float) 4.56);
            fichero1.writeByte((byte)11);
            fichero1.close();

            
            DataInputStream fichero = new DataInputStream(new FileInputStream(nomFich));
            while (true){
                operador=fichero.readByte();
              
                switch (operador){
                    case (byte) 0:
                        inst = new Error(operador);
                        break;
                    case CODIGO_APILAR:
                        tipo= fichero.readByte();
                        switch (tipo){
                            case CODIGO_BOOLEAN:
                            case CODIGO_CHAR:
                                byte dato1=fichero.readByte();
                                inst = new Apilar(operador,tipo,dato1);
                                break;
                            case CODIGO_NATURAL:
                            case CODIGO_INTEGER:
                                int dato4=fichero.readInt();
                                inst =new Apilar(operador,tipo,dato4);
                                break;
                            case CODIGO_FLOAT:
                                float dato2=fichero.readFloat();
                                inst = new Apilar(operador,tipo,dato2);
                                break;
                        }
                        break;
                    case CODIGO_APILARDIR:
                        tipo=fichero.readByte();
                        float dato3=fichero.readFloat();
                        inst = new ApilarDir(operador,tipo,dato3);
                        break;
                    case CODIGO_DESAPILAR:
                        inst = new Desapilar(operador);
                        break;
                    case CODIGO_DESAPILARDIR:
                        inst = new DesapilarDir(operador);
                        break;
                    case CODIGO_MENOR:
                        inst = new Menor(operador);
                        break;
                    case CODIGO_MAYOR:
                        inst = new Mayor(operador);
                        break;
                    case CODIGO_MENORIG:
                        inst = new MenorIg(operador);
                        break;
                    case CODIGO_MAYORIG:
                        inst = new MayorIg(operador);
                        break;
                    case CODIGO_IGUAL:
                        inst = new Igual(operador);
                        break;
                    case CODIGO_NOIGUAL:
                        inst = new NoIgual(operador);
                        break;
                    case CODIGO_SUMA:
                        inst = new Suma(operador);
                        break;
                    case CODIGO_RESTA:
                        inst = new Resta(operador);
                        break;
                    case CODIGO_MULTIPLICA:
                        inst = new Multiplica(operador);
                        break;
                    case CODIGO_DIVIDE:
                        inst = new Divide(operador);
                        break;
                    case CODIGO_MODULO:
                        inst = new Modulo(operador);
                        break;
                    case CODIGO_Y:
                        inst = new Y(operador);
                        break;
                    case CODIGO_O:
                        inst = new O(operador);
                        break;
                    case CODIGO_NO:
                        inst = new No(operador);
                        break;
                    case CODIGO_MENOS:
                        inst = new Menos(operador);
                        break;
                    case CODIGO_SHL:
                        inst = new Shl(operador);
                        break;
                    case CODIGO_SHR:
                        inst = new Shr(operador);
                        break;
                    case CODIGO_CASTINT:
                        inst = new CastInt(operador);
                        break;
                    case CODIGO_CASTCHAR:
                        inst = new CastChar(operador);
                        break;
                    case CODIGO_CASTFLOAT:
                        inst = new CastFloat(operador);
                        break;
                }
                sal.add(inst);
            }
        }
        catch (EOFException eof) {
            System.out.println(" >> Normal program termination.");
        }
        catch ( IOException e ) {
            System.err.println("Se produjo un error de E/S");
        }
        return sal;
    }
}
