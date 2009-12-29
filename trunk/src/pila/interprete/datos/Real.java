
package pila.interprete.datos;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.interprete.excepiones.DatoExc;

/**
 * Este DatoPila representa a un Real (float).
 */
public class Real extends DatoPila {

    private float valor;

    public Real(float valor) {
        super(FLOAT_T);
        this.valor = valor;
    }

    @Override
    public Object getValor() {
        return valor;
    }

    @Override
    public float toFloat() {
        return valor;
    }

    @Override
    public String toString() {
        return "Real ("+Float.toString(valor)+")";
    }

    public int comparar(DatoPila arg0) throws DatoExc {
        //TODO: Permitir comprar mediante casting automatico
        if(arg0.getTipoDato() != this.getTipoDato())
            throw new DatoExc(this);
        if(valor < arg0.toFloat())
            return -1;
        else if (valor > arg0.toFloat())
            return 1;
        else
            return 0;
    }

    @Override
    public void escribete(DataOutputStream dos) throws IOException {
        super.escribete(dos);
        dos.writeFloat(valor);
    }
}
