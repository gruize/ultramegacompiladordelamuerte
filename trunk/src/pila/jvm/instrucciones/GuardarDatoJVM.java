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
public class GuardarDatoJVM implements PseudoInstruccionJVM {

    Tipos tipoDato;
    int dir;

    public GuardarDatoJVM(Tipos tipoDato, int dir) {
        this.tipoDato = tipoDato;
        this.dir = dir;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        switch (tipoDato) {
            case BOOL:
                cc.añadirU1(Constants.I2B);
                switch(dir) {
                    case 0:
                        cc.añadirU1(Constants.ISTORE_0);
                        break;
                    case 1:
                        cc.añadirU1(Constants.ISTORE_1);
                        break;
                    case 2:
                        cc.añadirU1(Constants.ISTORE_2);
                        break;
                    case 3:
                        cc.añadirU1(Constants.ISTORE_3);
                        break;
                    default:
                        cc.añadirU1(Constants.ISTORE);
                        cc.añadirU1(dir);
                }

                break;
            case CHAR:
                cc.añadirU1(Constants.I2C);
                switch(dir) {
                    case 0:
                        cc.añadirU1(Constants.ISTORE_0);
                        break;
                    case 1:
                        cc.añadirU1(Constants.ISTORE_1);
                        break;
                    case 2:
                        cc.añadirU1(Constants.ISTORE_2);
                        break;
                    case 3:
                        cc.añadirU1(Constants.ISTORE_3);
                        break;
                    default:
                        cc.añadirU1(Constants.ISTORE);
                        cc.añadirU1(dir);
                }
                break;
            case REAL:
                switch(dir) {
                    case 0:
                        cc.añadirU1(Constants.FSTORE_0);
                        break;
                    case 1:
                        cc.añadirU1(Constants.FSTORE_1);
                        break;
                    case 2:
                        cc.añadirU1(Constants.FSTORE_2);
                        break;
                    case 3:
                        cc.añadirU1(Constants.FSTORE_3);
                        break;
                    default:
                        cc.añadirU1(Constants.FSTORE);
                        cc.añadirU1(dir);
                }
                break;
            case ENTERO:
            case NATURAL:
                switch(dir) {
                    case 0:
                        cc.añadirU1(Constants.ISTORE_0);
                        break;
                    case 1:
                        cc.añadirU1(Constants.ISTORE_1);
                        break;
                    case 2:
                        cc.añadirU1(Constants.ISTORE_2);
                        break;
                    case 3:
                        cc.añadirU1(Constants.ISTORE_3);
                        break;
                    default:
                        cc.añadirU1(Constants.ISTORE);
                        cc.añadirU1(dir);
                }
                break;
            default:
                throw new Exception("Tipo de dato a escribir inválido");
        }
    }

    public int dameU1s() {
        int i;
        if(tipoDato== Tipos.BOOL || tipoDato == Tipos.CHAR)
            i = 1;
        else
            i = 0;
        if(dir >= 0 && dir <= 3)
            return i+1;
        else
            return i+2;
    }



}
