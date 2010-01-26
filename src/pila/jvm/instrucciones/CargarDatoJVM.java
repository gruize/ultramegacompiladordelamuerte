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
public class CargarDatoJVM implements PseudoInstruccionJVM {

    Tipos tipoDato;
    int dir;

    public CargarDatoJVM(Tipos tipoDato, int dir) {
        this.tipoDato = tipoDato;
        this.dir = dir;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        switch (tipoDato) {
            case BOOL:
            case CHAR:
            case ENTERO:
            case NATURAL:
                switch(dir) {
                    case 0:
                        cc.añadirU1(Constants.ILOAD_0);
                        break;
                    case 1:
                        cc.añadirU1(Constants.ILOAD_1);
                        break;
                    case 2:
                        cc.añadirU1(Constants.ILOAD_2);
                        break;
                    case 3:
                        cc.añadirU1(Constants.ILOAD_3);
                        break;
                    default:
                        cc.añadirU1(Constants.ILOAD);
                        cc.añadirU1(dir);
                }

                break;
            case REAL:
                switch(dir) {
                    case 0:
                        cc.añadirU1(Constants.FLOAD_0);
                        break;
                    case 1:
                        cc.añadirU1(Constants.FLOAD_1);
                        break;
                    case 2:
                        cc.añadirU1(Constants.FLOAD_2);
                        break;
                    case 3:
                        cc.añadirU1(Constants.FLOAD_3);
                        break;
                    default:
                        cc.añadirU1(Constants.FLOAD);
                        cc.añadirU1(dir);
                }
                break;
            default:
                throw new Exception("Tipo de dato a escribir inválido");
        }
    }

    public int dameU1s() {
        if(dir >= 0 && dir <= 3)
            return 1;
        else return 2;
    }



}
