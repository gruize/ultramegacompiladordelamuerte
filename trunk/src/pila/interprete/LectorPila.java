package pila.interprete;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import pila.interprete.datos.Dato_Bool;
import pila.interprete.datos.Dato_Caracter;
import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Dato_Entero;
import pila.interprete.datos.Dato_Nat;
import pila.interprete.datos.Dato_Real;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.LectorExc;
import pila.interprete.instrucciones.*;

public class LectorPila {

    /**
     * Este método lee un dato de un DataInputStream
     * @param dis
     * @return el dato leido
     * @throws LectorExc en el caso de que ocurra algún error
     * al leer el dato
     * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
     */
    private DatoPila leerDato(DataInputStream dis) throws LectorExc {
        try {
            byte tipo = dis.readByte(); //se lee el tipo
            switch(tipo) { //segun el tipo se crea un DatoPila distinto
                case DatoPila.BOOL_T:
                    return new Dato_Bool(dis.readBoolean());
                case DatoPila.CHAR_T:
                    return new Dato_Caracter(dis.readChar());
                case DatoPila.NAT_T:
                    try {
                        return new Dato_Nat(dis.readInt());
                    } catch (DatoExc ex) {
                        throw new LectorExc(ex.getLocalizedMessage());
                    }
                case DatoPila.INT_T:
                    return new Dato_Entero(dis.readInt());
                case DatoPila.FLOAT_T:
                    return new Dato_Real(dis.readFloat());
                default:
                    throw new LectorExc("Tipo de dato "+
                            Byte.toString(tipo)+"inválido");
            }
        }
        catch (IOException e) {
            throw new LectorExc(e.getMessage());
        }
    }

    /**
     * Este método lee una instrucción de un DataInputStream
     * @param dis
     * @return la InstruccionInterprete leida
     * @throws IOException En el caso de que ocurra un error
     * del stream
     * @throws LectorExc En el caso de que ocurra un error
     * de formato del programa fuente (por ejemplo, apilar
     * sin argumento)
     */
    private InstruccionInterprete leerInstruccion(DataInputStream dis) throws IOException, LectorExc {
        byte tipoIns = dis.readByte();
        InstruccionInterprete inst;
        switch(tipoIns) {
            default:
                throw new IOException("Instrucción inválida");
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
                inst = new DesapilarDir(leerDato(dis));
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
            case InstruccionInterprete.CODIGO_CASTNAT:
                inst = new CastNat();
                break;
            case InstruccionInterprete.CODIGO_PARAR:
                inst = new Parar();
                break;
            case InstruccionInterprete.CODIGO_ABS:
                inst = new Abs();
                break;
            case InstruccionInterprete.CODIGO_SALIDA:
                inst = new Salida();
                break;
            case InstruccionInterprete.CODIGO_ENTRADA_BOOL:
                inst = new EntradaBool(leerDato(dis));
                break;
            case InstruccionInterprete.CODIGO_ENTRADA_CHAR:
                inst = new EntradaChar(leerDato(dis));
                break;
            case InstruccionInterprete.CODIGO_ENTRADA_INT:
                inst = new EntradaInt(leerDato(dis));
                break;
            case InstruccionInterprete.CODIGO_ENTRADA_FLOAT:
                inst = new EntradaFloat(leerDato(dis));
                break;
            case InstruccionInterprete.CODIGO_ENTRADA_NAT:
                inst = new EntradaNat(leerDato(dis));
                break;
        }
        return inst;
    }

    public ArrayList<InstruccionInterprete> leerPrograma(File f) throws FileNotFoundException, IOException, LectorExc{
        ArrayList<InstruccionInterprete> ad = new ArrayList<InstruccionInterprete>();
        DataInputStream dis = new DataInputStream(new FileInputStream(f));
        while (dis.available() > 0) {//mientras haya bytes disponibles sigo leyendo
            ad.add(leerInstruccion(dis));
        }
        return ad;
    }

}
