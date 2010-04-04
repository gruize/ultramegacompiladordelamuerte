/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pila.jvm.instrucciones;

import com.sun.org.apache.bcel.internal.Constants;
import compilador.tablaSimbolos.InfoTs.Tipos;
import pila.jvm.ClassConstructor;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class AbsInstruccionJVM implements PseudoInstruccionJVM {

    Tipos tipo;

    public AbsInstruccionJVM(Tipos tipo) {
        this.tipo = tipo;
    }

    public int dameU1s() {
        return 3;
    }

    public void toClass(ClassConstructor cc) throws Exception {
        cc.añadirU1(Constants.INVOKESTATIC);
        if(tipo == Tipos.REAL)
            cc.añadirU2(cc.getAbsFloatMethodIndex());
        else
            cc.añadirU2(cc.getAbsIntMethodIndex());
    }





}
