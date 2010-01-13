/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.lexico;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author ruben
 */
public class AnalizadorLexico {
private static final int INICIAL = 0;
private static final int PARENTESIS_C = 1;
private static final int COMENTARIO = 2;
private static final int PARENTESIS_A = 3;
private static final int CASTING_INT1 =4;
private static final int CASTING_INT2 = 5;
private static final int CASTING_NAT1 = 6;
private static final int CASTING_NAT2 = 7;
private static final int CASTING_FLOAT1 = 8;
private static final int CASTING_FLOAT2 = 9;
private static final int CASTING_FLOAT3 = 10;
private static final int CASTING_FLOAT4 = 11;
private static final int CASTING_CHAR1 = 12;
private static final int CASTING_CHAR2 = 13;
private static final int CASTING_CHAR3 = 14;
private static final int CASTING_COMUN = 15;
private static final int CASTING_FINAL =16;
private static final int MAYOR = 17;
private static final int MAYOR_IG = 18;
private static final int SHL = 19;
private static final int MENOR = 20;
private static final int MENOR_IG = 21;
private static final int SHR = 22;
private static final int DOS_PUNTOS = 23;
private static final int DOS_PUNTOS_IG = 24;
private static final int SEPARADOR =25;
private static final int PUNTO_COMA = 26;
private static final int DISTINTO1 = 27;
private static final int DISTINTO2 = 28;
private static final int DISTINTO_FINAL = 29;
private static final int SUMA = 30;
private static final int MULTIPLICACION = 31;
private static final int MODULO = 32;
private static final int SIGNO_MENOS = 33;
private static final int NATURAL_FINAL1 = 34;
private static final int NATURAL_FINAL2 = 35;
private static final int FLOAT1 = 36;
private static final int FLOAT_FINAL1 = 37;
private static final int FLOAT_FINAL2 = 38;
private static final int FLOAT2 = 39;
private static final int FLOAT3 = 40;
private static final int FLOAT4 = 41;
private static final int FLOAT_FINAL3 = 42;
private static final int FLOAT_FINAL4 = 43;


private char buff;
private String lex;
private int estado;
private InputStreamReader reader = null;
private ArrayList<Token> arrayTokens;

public AnalizadorLexico(File f) throws FileNotFoundException, IOException{
    reader = new InputStreamReader(new FileInputStream(f));
    lex="";
    estado = 0;
    buff = sigCar();//inicioScan() de los apuntes
    arrayTokens = new ArrayList<Token>();
    scanner();
    

}

public char sigCar() throws IOException{
    char res;
    if(reader==null) throw new IOException("No creaste ningun Reader para la clase!");
    res = (char) reader.read();
    return res;
}

public void transita(int est) throws IOException{
    lex = lex + buff;
    buff = sigCar();
    estado = est;
}
private void error(int estado) {
    }

public void scanner() throws IOException{
    while(true){
        switch(estado){
            case INICIAL :
                switch (buff){
                    case ')': transita(PARENTESIS_C); break;
                    case '#': transita(COMENTARIO); break;
                    case '(': transita(PARENTESIS_A); break;
                    case '>': transita(MAYOR); break;
                    case '<': transita(MENOR); break;
                    case '=': transita(DISTINTO1); break;
                    case ':': transita(DOS_PUNTOS); break;
                    case '&': transita(SEPARADOR); break;
                    case ';': transita(PUNTO_COMA); break;
                    case '+': transita(SUMA); break;
                    case '*': transita(MULTIPLICACION); break;
                    case '%': transita(MODULO); break;
                    case '-': transita(SIGNO_MENOS); break;
                    case '0': transita(NATURAL_FINAL1); break;
                    case '[0-9]': transita(NATURAL_FINAL2); break;
                }
                break;
            case PARENTESIS_C :
                arrayTokens.add(new Parentesis_c(lex));
                break;
            case COMENTARIO :
                break;
            case PARENTESIS_A :
                switch (buff){
                    case 'i': transita(CASTING_INT1); break;
                    case 'n': transita(CASTING_NAT1); break;
                    case 'f': transita(CASTING_FLOAT1); break;
                    case 'c': transita(CASTING_CHAR1); break;
                    default: error(estado);
                }
                break;
            case CASTING_INT1 :
                switch (buff){
                    case 'n': transita(CASTING_INT2); break;
                    default: error(estado);
                }
                break;
            case CASTING_INT2 :
                switch (buff){
                    case 't': transita(CASTING_COMUN); break;
                    default: error(estado);
                }
                break;
            case CASTING_NAT1 :
                switch (buff){
                    case 'a': transita(CASTING_NAT2); break;
                    default: error(estado);
                }
                break;
            case CASTING_NAT2 :
                switch (buff){
                    case 't': transita(CASTING_COMUN); break;
                    default: error(estado);
                }
                break;
            case CASTING_FLOAT1 :
                switch (buff){
                    case 'l': transita(CASTING_FLOAT2); break;
                    default: error(estado);
                }
                break;
            case CASTING_FLOAT2 :
                switch (buff){
                    case 'o': transita(CASTING_FLOAT3); break;
                    default: error(estado);
                }
                break;
            case CASTING_FLOAT3 :
                switch (buff){
                    case 'a': transita(CASTING_FLOAT4); break;
                    default: error(estado);
                }
                break;
            case CASTING_FLOAT4 :
                switch (buff){
                    case 't': transita(CASTING_CHAR1); break;
                    default: error(estado);
                }
                break;
            case CASTING_CHAR1 :
                switch (buff){
                    case 'h': transita(CASTING_CHAR2); break;
                    default: error(estado);
                }
                break;
            case CASTING_CHAR2 :
                switch (buff){
                    case 'a': transita(CASTING_CHAR3); break;
                    default: error(estado);
                }
                break;
            case CASTING_CHAR3 :
                switch (buff){
                    case 'a': transita(CASTING_COMUN); break;
                    default: error(estado);
                }
                break;
            case CASTING_COMUN :
                switch (buff){
                    case ')': transita(CASTING_FINAL); break;
                    default: error(estado);
                }
                break;
            case CASTING_FINAL :
                if (lex.equals("(int)"))
                    arrayTokens.add(new Cast_int(lex));
                else if (lex.equals("(nat)"))
                        arrayTokens.add(new Cast_nat(lex));
                else if (lex.equals("(float)"))
                    arrayTokens.add(new Cast_float(lex));
                else if (lex.equals("(char)"))
                    arrayTokens.add(new Cast_char(lex));
                else error(estado);
                break;
            case MAYOR :
                switch(buff){
                    case '>': transita(SHL); break;
                    case '=': transita(MAYOR_IG); break;
                    default: arrayTokens.add(new Menor(lex)); break;
                }
                break;
            case MAYOR_IG :
                arrayTokens.add(new Mayor_ig(lex));
                break;
            case SHL :
                arrayTokens.add(new Shl(lex));
                break;
            case MENOR :
                 switch(buff){
                    case '<': transita(SHR); break;
                    case '=': transita(MENOR_IG); break;
                    default: arrayTokens.add(new Menor(lex)); break;
                }
                break;
            case MENOR_IG :
                arrayTokens.add(new Menor_ig(lex));
                break;
            case SHR:
                arrayTokens.add(new Shl(lex));
                break;
            case DOS_PUNTOS :
                switch(buff){
                    case '<': transita(DOS_PUNTOS_IG); break;
                    default: arrayTokens.add(new Dos_puntos(lex)); break;
                }
                break;
            case DOS_PUNTOS_IG :
                arrayTokens.add(new Dos_puntos_ig(lex));
                break;
            case SEPARADOR:
                arrayTokens.add(new Separador(lex));
                break;
            case PUNTO_COMA:
                arrayTokens.add(new Punto_coma(lex));
                break;
            case DISTINTO1:
                switch(buff){
                    case '/': transita(DISTINTO2); break;
                    default: error(estado);
                }
                break;
            case DISTINTO2 :
                switch(buff){
                    case '=': transita(DISTINTO_FINAL); break;
                    default: error(estado);
                }
                break;
            case DISTINTO_FINAL:
                arrayTokens.add(new Distinto(lex));
                break;
            case SUMA:
                arrayTokens.add(new Suma(lex));
                break;
            case MULTIPLICACION:
                arrayTokens.add(new Multiplicacion(lex));
                break;
            case MODULO:
                arrayTokens.add(new Modulo(lex));
                break;
            case SIGNO_MENOS:
                arrayTokens.add(new Signo_menos(lex));
                break;
            case NATURAL_FINAL1 :
                switch(buff){
                    case '.': transita(FLOAT1); break;
                    default: error(estado);
                }
                break;
            case NATURAL_FINAL2 :
                switch(buff){
                    case '.': transita(FLOAT1); break;
                    default: error(estado);
                }
                break;
            case FLOAT1 :
                break;
            case FLOAT_FINAL1 :
                break;
            case FLOAT_FINAL2 :
                break;
            case FLOAT2 :
                break;
            case FLOAT3 :
                break;
            case FLOAT4 :
                break;
            case FLOAT_FINAL3 :
                break;
            case FLOAT_FINAL4 :
                break;
            
        }
    }
}

}

