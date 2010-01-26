/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compilador.traductor;

import java.util.ArrayList;
import org.apache.bcel.classfile.JavaClass;
import pila.interprete.instrucciones.InstruccionInterprete;
import pila.jvm.ClassConstructor;
import pila.jvm.instrucciones.PseudoInstruccionJVM;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar (Golthiryus)
 */
public class CodigoJVM {

    int numU1s;
    ArrayList<PseudoInstruccionJVM> ar;
    
    public CodigoJVM() {
        numU1s = 0;
        ar = new ArrayList<PseudoInstruccionJVM>();
    }

    public void append(CodigoJVM c) {
        ar.addAll(c.ar);
        numU1s += c.dameU1s();
    }

    public void add(PseudoInstruccionJVM i) {
        numU1s += i.dameU1s();
        ar.add(i);
    }

    public ArrayList<PseudoInstruccionJVM> getCod() {
        return ar;
    }

    public int size(){
        return ar.size();
    }

    public int dameU1s() {
        return numU1s;
    }

    public JavaClass dameTraduccion(int numVars, int maxStack, String nombreClase) throws Exception {
        ClassConstructor cc = new ClassConstructor(nombreClase);
        final int size = ar.size();
        for(int i = 0; i < size; i++)
            ar.get(i).toClass(cc);
        return cc.getJavaClass(numVars,maxStack);
    }


}
