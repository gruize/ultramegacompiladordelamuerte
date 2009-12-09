package pila.interprete;

import pila.interprete.datos.DatoPila;
import pila.interprete.instrucciones.InstruccionInterprete;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import pila.LectorBytecode;
import pila.interprete.datos.Booleano;
import pila.interprete.datos.Caracter;
import pila.interprete.datos.Entero;
import pila.interprete.datos.Natural;
import pila.interprete.datos.Real;
import pila.interprete.instrucciones.Apilar;
import pila.interprete.instrucciones.ApilarDir;
import pila.interprete.instrucciones.CastChar;
import pila.interprete.instrucciones.CastFloat;
import pila.interprete.instrucciones.CastInt;
import pila.interprete.instrucciones.Desapilar;
import pila.interprete.instrucciones.DesapilarDir;
import pila.interprete.instrucciones.Divide;
import pila.interprete.instrucciones.Igual;
import pila.interprete.instrucciones.Mayor;
import pila.interprete.instrucciones.MayorIg;
import pila.interprete.instrucciones.Menor;
import pila.interprete.instrucciones.MenorIg;
import pila.interprete.instrucciones.Menos;
import pila.interprete.instrucciones.Modulo;
import pila.interprete.instrucciones.Multiplica;
import pila.interprete.instrucciones.No;
import pila.interprete.instrucciones.NoIgual;
import pila.interprete.instrucciones.O;
import pila.interprete.instrucciones.Resta;
import pila.interprete.instrucciones.Shl;
import pila.interprete.instrucciones.Shr;
import pila.interprete.instrucciones.Suma;
import pila.interprete.instrucciones.Y;

public class LectorPila implements LectorBytecode {

    private DatoPila leerDato(DataInputStream dis) throws IOException {
        byte tipo = dis.readByte();
        switch(tipo) {
            case DatoPila.BOOL_T:
                return new Booleano(dis.readBoolean());
            case DatoPila.CHAR_T:
                return new Caracter(dis.readChar());
            case DatoPila.NAT_T:
                return new Natural(dis.readLong());
            case DatoPila.INT_T:
                return new Entero(dis.readInt());
            case DatoPila.FLOAT_T:
                return new Real(dis.readFloat());
            default:
                throw new IOException("Dato inválido");
        }
    }

    private InstruccionInterprete leerInstruccion(DataInputStream dis) throws IOException {
        byte tipoIns = dis.readByte();
        InstruccionInterprete inst;
        switch(tipoIns) {
            case (byte) 0:
                inst = new Error();
                break;
            case InstruccionInterprete.CODIGO_APILAR:
                inst = new Apilar(leerDato(dis));
                break;
            case InstruccionInterprete.CODIGO_APILARDIR:
                inst = new ApilarDir(leerDato(dis));                
                break;
            case InstruccionInterprete.CODIGO_DESAPILAR:
                inst = new Desapilar();
                break;
            case InstruccionInterprete.CODIGO_DESAPILARDIR:
                inst = new DesapilarDir();
                break;
            case InstruccionInterprete.CODIGO_MENOR:
                inst = new Menor();
                break;
            case InstruccionInterprete.CODIGO_MAYOR:
                inst = new Mayor();
                break;
            case InstruccionInterprete.CODIGO_MENORIG:
                inst = new MenorIg();
                break;
            case InstruccionInterprete.CODIGO_MAYORIG:
                inst = new MayorIg();
                break;
            case InstruccionInterprete.CODIGO_IGUAL:
                inst = new Igual();
                break;
            case InstruccionInterprete.CODIGO_NOIGUAL:
                inst = new NoIgual();
                break;
            case InstruccionInterprete.CODIGO_SUMA:
                inst = new Suma();
                break;
            case InstruccionInterprete.CODIGO_RESTA:
                inst = new Resta();
                break;
            case InstruccionInterprete.CODIGO_MULTIPLICA:
                inst = new Multiplica();
                break;
            case InstruccionInterprete.CODIGO_DIVIDE:
                inst = new Divide();
                break;
            case InstruccionInterprete.CODIGO_MODULO:
                inst = new Modulo();
                break;
            case InstruccionInterprete.CODIGO_Y:
                inst = new Y();
                break;
            case InstruccionInterprete.CODIGO_O:
                inst = new O();
                break;
            case InstruccionInterprete.CODIGO_NO:
                inst = new No();
                break;
            case InstruccionInterprete.CODIGO_MENOS:
                inst = new Menos();
                break;
            case InstruccionInterprete.CODIGO_SHL:
                inst = new Shl();
                break;
            case InstruccionInterprete.CODIGO_SHR:
                inst = new Shr();
                break;
            case InstruccionInterprete.CODIGO_CASTINT:
                inst = new CastInt();
                break;
            case InstruccionInterprete.CODIGO_CASTCHAR:
                inst = new CastChar();
                break;
            case InstruccionInterprete.CODIGO_CASTFLOAT:
                inst = new CastFloat();
                break;


            default:
                throw new IOException("Instrucción inválida");
        }
        return inst;
    }

    public ArrayList<InstruccionInterprete> leerFuente(File f) throws FileNotFoundException, IOException {
        ArrayList<InstruccionInterprete> ad = new ArrayList<InstruccionInterprete>();
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        while (dis.available() > 0) {
            ad.add(leerInstruccion(dis));
        }
        return ad;
    }

}
