/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import compilador.tablaSimbolos.InfoTs.Tipos;
import org.apache.bcel.Constants;
import pila.interprete.datos.DatoPila;
import pila.jvm.ClassConstructor;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class EntradaJVM implements PseudoInstruccionJVM{
    Tipos tipoDato;
    int dirVar;

    public EntradaJVM(Tipos tipoDato, int dirVar) {
        this.tipoDato = tipoDato;
        this.dirVar = dirVar;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        //las siguientes 4 sentencias leen una linea
        cc.añadirU1(Constants.GETSTATIC);
        cc.añadirU2(cc.getReaderFieldIndex());
        cc.añadirU1(Constants.INVOKEVIRTUAL);
        cc.añadirU2(cc.getReadLineIndex());
        switch(tipoDato) {
            case BOOL:
                cc.añadirU1(Constants.INVOKESTATIC);
                cc.añadirU2(cc.getParseBoolIndex());
                break;
            case CHAR:
                cc.añadirU1(Constants.ICONST_0);
                cc.añadirU1(Constants.INVOKEVIRTUAL);
                cc.añadirU2(cc.getCharAtIndex());
                break;
            case REAL:
                cc.añadirU1(Constants.INVOKESTATIC);
                cc.añadirU2(cc.getParseFloatIndex());
                break;
            case ENTERO:
                cc.añadirU1(Constants.INVOKESTATIC);
                cc.añadirU2(cc.getParseIntIndex());
                break;
            case NATURAL:
                cc.añadirU1(Constants.INVOKESTATIC);
                cc.añadirU2(cc.getParseIntIndex());
                cc.añadirU1(Constants.DUP); //gastamos 1 para el if y el otro es el resultado
                cc.añadirU1(Constants.IFGE);
                cc.añadirU2(13); //2 => saltamos hasta despues del throw
                cc.añadirU1(Constants.NEW); //3
                cc.añadirU2(cc.getExceptionIndex()); //5
                cc.añadirU1(Constants.DUP); //6
                cc.añadirU1(Constants.LDC); //7 => se carga el string
                cc.añadirU1(cc.getNatNegErrorStringIndex()); //8
                cc.añadirU1(Constants.INVOKESPECIAL); //9
                cc.añadirU2(cc.getInitExceptionIndex()); //11
                cc.añadirU1(Constants.ATHROW); //12
                break;
        }
    }

    public int dameU1s() {
        int u1sTipo = 0;
        switch(tipoDato) {
            case BOOL:
                u1sTipo = 3;
                break;
            case CHAR:
                u1sTipo = 4;
                break;
            case REAL:
                u1sTipo = 3;
                break;
            case ENTERO:
                u1sTipo = 3;
                break;
            case NATURAL:
                u1sTipo = 17;
                break;
        }
        return 6 + u1sTipo;
    }




}
