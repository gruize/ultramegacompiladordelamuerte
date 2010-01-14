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
    private static final int CASTING_INT3 =6;
    private static final int CAST_INT = 7;
    private static final int CASTING_NAT1 = 8;
    private static final int CASTING_NAT2 = 9;
    private static final int CASTING_NAT3 = 10;
    private static final int CAST_NAT = 11;
    private static final int CASTING_FLOAT1 = 12;
    private static final int CASTING_FLOAT2 = 13;
    private static final int CASTING_FLOAT3 = 14;
    private static final int CASTING_FLOAT4 = 15;
    private static final int CASTING_FLOAT5 = 16;
    private static final int CAST_FLOAT = 17;
    private static final int CASTING_CHAR1 = 18;
    private static final int CASTING_CHAR2 = 19;
    private static final int CASTING_CHAR3 = 20;
    private static final int CASTING_CHAR4 = 21;
    private static final int CAST_CHAR = 22;
    private static final int MAYOR = 23;
    private static final int SHL = 24;
    private static final int MAYOR_IG = 25;
    private static final int MENOR = 26;
    private static final int SHR = 27;
    private static final int MENOR_IG = 28;
    private static final int DOS_PUNTOS = 29;
    private static final int DOS_PUNTOS_IG = 30;
    private static final int IGUAL = 31;
    private static final int DISTINTO1 = 32;
    private static final int DISTINTO = 33;
    private static final int LIT_NAT1 = 34;
    private static final int LIT_NAT2 = 35;
    private static final int FLOAT1 = 36;
    private static final int LIT_FLO1 = 37;
    private static final int LIT_FLO2 = 38;
    private static final int FLOAT2 = 39;
    private static final int FLOAT3 = 40;
    private static final int LIT_FLO3 = 41;
    private static final int FLOAT4 = 42;
    private static final int LIT_FLO4 = 43;
    private static final int SEPARADOR =44;
    private static final int PUNTO_COMA = 45;
    private static final int SUMA = 46;
    private static final int MULTIPLICACION = 47;
    private static final int DIVISION = 48;
    private static final int MODULO = 49;
    private static final int ABSOLUTO = 50;
    private static final int CADENA = 51;
    private static final int CHAR1 = 52;
    private static final int CHAR2 = 53;
    private static final int LIT_CHA= 54;
    private static final int SIGNO_MENOS = 55;

    private char buff;
    private String lex;
    private int estado;
    private InputStreamReader reader = null;
    private ArrayList<Token> arrayTokens;

    public AnalizadorLexico(String src) throws FileNotFoundException, IOException{
        File f=new File(src);
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
public void error(int estado) {
    }

public void terminaEstado(){
    lex="";
    estado=INICIAL;
}

public void scanner() throws IOException{
    while(true){
        switch(estado){
            case INICIAL :
                switch (buff){
                    case '\n':
                    case '\t':
                    case ' ': transita(INICIAL); break;
                    case ')': transita(PARENTESIS_C); break;
                    case '#': transita(COMENTARIO); break;
                    case '(': transita(PARENTESIS_A); break;
                    case '>': transita(MAYOR); break;
                    case '<': transita(MENOR); break;
                    case ':': transita(DOS_PUNTOS); break;
                    case '=': transita(IGUAL); break;
                    case '&': transita(SEPARADOR); break;
                    case ';': transita(PUNTO_COMA); break;
                    case '+': transita(SUMA); break;
                    case '*': transita(MULTIPLICACION); break;
                    case '/': transita(DIVISION); break;
                    case '%': transita(MODULO); break;
                    case '|': transita(ABSOLUTO); break;
                    case '\'' : transita(CHAR1); break;
                    case '-': transita(SIGNO_MENOS); break;
                    default:
                        if (Character.isDigit(buff)){
                            switch(buff){
                                 case '0': transita(LIT_NAT2); break;
                                 default : transita(LIT_NAT1);
                            }
                        }
                        else{
                            if(Character.isLetter(buff))
                                transita(CADENA);
                            else error(estado);
                        }
                }
                break;
            case PARENTESIS_C :
                arrayTokens.add(new Parentesis_c(lex));
                terminaEstado();
                break;
            case COMENTARIO :
                switch(buff){
                    case '\n': arrayTokens.add(new Parentesis_c(lex)); terminaEstado(); break;
                    default: transita(COMENTARIO);
                }

                break;
            case PARENTESIS_A :
                switch (buff){
                    case 'i': transita(CASTING_INT1); break;
                    case 'n': transita(CASTING_NAT1); break;
                    case 'f': transita(CASTING_FLOAT1); break;
                    case 'c': transita(CASTING_CHAR1); break;
                    default: arrayTokens.add(new Parentesis_a(lex)); terminaEstado();
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
                    case 't': transita(CASTING_INT3); break;
                    default: error(estado);
                }
                break;
            case CASTING_INT3 :
                switch (buff){
                    case ')': transita(CAST_INT); break;
                    default: error(estado);
                }
                break;
            case CAST_INT :
                arrayTokens.add(new Cast_int(lex));
                terminaEstado();
                break;
            case CASTING_NAT1 :
                switch (buff){
                    case 'a': transita(CASTING_NAT2); break;
                    default: error(estado);
                }
                break;
            case CASTING_NAT2 :
                switch (buff){
                    case 't': transita(CASTING_NAT3); break;
                    default: error(estado);
                }
                break;
            case CASTING_NAT3 :
                switch (buff){
                    case ')': transita(CAST_NAT); break;
                    default: error(estado);
                }
                break;
            case CAST_NAT :
                arrayTokens.add(new Cast_nat(lex));
                terminaEstado();
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
                    case 't': transita(CASTING_FLOAT5); break;
                    default: error(estado);
                }
                break;
            case CASTING_FLOAT5 :
                switch (buff){
                    case ')': transita(CAST_FLOAT); break;
                    default: error(estado);
                }
                break;
            case CAST_FLOAT :
                arrayTokens.add(new Cast_float(lex));
                terminaEstado();
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
                    case 'r': transita(CASTING_CHAR4); break;
                    default: error(estado);
                }
                break;
            case CASTING_CHAR4 :
                switch (buff){
                    case ')': transita(CAST_CHAR); break;
                    default: error(estado);
                }
                break;
            case CAST_CHAR :
                arrayTokens.add(new Cast_char(lex));
                terminaEstado();
                break;
            case MAYOR :
                switch(buff){
                    case '>': transita(SHL); break;
                    case '=': transita(MAYOR_IG); break;
                    default: arrayTokens.add(new Mayor(lex)); terminaEstado();
                }
                break;
            case MAYOR_IG :
                arrayTokens.add(new Mayor_ig(lex));
                terminaEstado();
                break;
            case SHL :
                arrayTokens.add(new Shl(lex));
                terminaEstado();
                break;
            case MENOR :
                 switch(buff){
                    case '<': transita(SHR); break;
                    case '=': transita(MENOR_IG); break;
                    default: arrayTokens.add(new Menor(lex)); terminaEstado();
                }
                break;
            case MENOR_IG :
                arrayTokens.add(new Menor_ig(lex));
                terminaEstado();
                break;
            case SHR:
                arrayTokens.add(new Shl(lex));
                terminaEstado();
                break;
            case DOS_PUNTOS :
                switch(buff){
                    case '=': transita(DOS_PUNTOS_IG); break;
                    default: arrayTokens.add(new Dos_puntos(lex)); terminaEstado();
                }
                break;
            case DOS_PUNTOS_IG :
                arrayTokens.add(new Dos_puntos_ig(lex));
                terminaEstado();
                break;
            case IGUAL:
                switch(buff){
                    case '/': transita(DISTINTO1); break;
                    default: arrayTokens.add(new Igual(lex)); terminaEstado();
                }
                break;
            case DISTINTO1 :
                switch(buff){
                    case '=': transita(DISTINTO); break;
                    default: error(estado);
                }
                break;
            case DISTINTO:
                arrayTokens.add(new Distinto(lex));
                terminaEstado();
                break;
            case LIT_NAT1 :
                switch(buff){
                    case '.': transita(FLOAT1); break;
                    default: arrayTokens.add(new Distinto(lex)); terminaEstado();
                }
                break;
            case LIT_NAT2 :
                switch(buff){
                    case '.': transita(FLOAT1); break;
                    default: arrayTokens.add(new Distinto(lex)); terminaEstado();
                }
                break;
            case FLOAT1 :
                if(Character.isDigit(buff))
                    switch(buff){
                        case '0': transita(LIT_FLO1); break;
                        default: transita(LIT_FLO2);
                    }
                else
                    error(estado);
                break;
            case LIT_FLO1 :
                switch(buff){
                    case 'e':
                    case 'E': transita(FLOAT3); break;
                    default:
                        if(Character.isDigit(buff))
                            switch(buff){
                                case '0': transita(FLOAT2); break;
                                default: transita(LIT_FLO2);
                            }
                        else{
                            arrayTokens.add(new LitFlo(lex));
                            terminaEstado();
                        }
                }
                break;
            case LIT_FLO2 :
                switch(buff){
                    case 'e':
                    case 'E': transita(FLOAT3); break;
                    default: 
                        if(Character.isDigit(buff))
                            switch(buff){
                                case '0': transita(FLOAT2); break;
                                default: transita(LIT_FLO2);
                            }
                        else{
                            arrayTokens.add(new LitFlo(lex));
                            terminaEstado();
                        }
                }
                break;
            case FLOAT2 :
                if(Character.isDigit(buff))
                    switch(buff){
                        case '0': transita(FLOAT2); break;
                        default: transita(LIT_FLO2);
                    }
                else
                    error(estado);
                break;
            case FLOAT3 :
                switch(buff){
                    case '-': transita(FLOAT4); break;
                    default:
                        if(Character.isDigit(buff))
                            switch(buff){
                                case '0': transita(LIT_FLO3); break;
                                default: transita(LIT_FLO4);
                            }
                        else
                            error(estado);
                }
                break;
            case FLOAT4 :
                if(Character.isDigit(buff))
                        switch(buff){
                            case '0': error(estado); break;
                            default: transita(LIT_FLO4);
                        }
                else
                    error(estado);

            case LIT_FLO3:
                arrayTokens.add(new LitFlo(lex));
                terminaEstado();
                break;
            case LIT_FLO4 :
                arrayTokens.add(new LitFlo(lex));
                terminaEstado();
                break;
            case SEPARADOR:
                arrayTokens.add(new Separador(lex));
                terminaEstado();
                break;
            case PUNTO_COMA:
                arrayTokens.add(new Punto_coma(lex));
                terminaEstado();
                break;
            case SUMA:
                arrayTokens.add(new Suma(lex));
                terminaEstado();
                break;
            case MULTIPLICACION:
                arrayTokens.add(new Multiplicacion(lex));
                terminaEstado();
                break;
            case DIVISION :
                arrayTokens.add(new Division(lex));
                terminaEstado();
            case MODULO:
                arrayTokens.add(new Modulo(lex));
                terminaEstado();
                break;
            case ABSOLUTO:
                arrayTokens.add(new Absoluto(lex));
                terminaEstado();
                break;
            case CADENA:
                switch(buff){
                    case ' ': transita(CADENA); break;
                    default: //mirar si es palabra reservada
                }
            case CHAR1:
                if(Character.isDigit(buff) || Character.isLetter(buff))
                    transita(CHAR2);
                else error(estado);
                break;
            case CHAR2:
                switch(buff){
                    case '\'': transita(LIT_CHA);
                    default: error(estado);
                }
                break;
            case LIT_CHA:
                arrayTokens.add(new LitCha(lex));
                terminaEstado();
                break;
            case SIGNO_MENOS:
                arrayTokens.add(new Signo_menos(lex));
                terminaEstado();
                break;
            
            
        }
    }
}
 public static void main(String [] args) throws FileNotFoundException, IOException {
        AnalizadorLexico a= new AnalizadorLexico("/home/ruben/Documentos/prueba.txt");
        a.lex="eee";
    }

}

