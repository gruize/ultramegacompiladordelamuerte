
package pila.interprete.datos;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.interprete.excepiones.DatoExc;

/**
 * Este DatoPila representa a un caracter
 */
public class Caracter extends DatoPila {

    private char valor;

    public Caracter(char valor) {
        super(CHAR_T);
        this.valor = valor;
    }

    @Override
    public Object getValor() {
        return valor;
    }

    @Override
    public int toInt() {
        return (int)valor;
    }

    @Override
    public long toNatural() {
        return (long)valor;
    }

    @Override
    public float toFloat() {
        return (float)valor;
    }

    @Override
    public char toChar() {
        return valor;
    }

    @Override
    public String toString() {
        return "Caracter ("+Character.toString(valor)+")";
    }

    public int comparar(DatoPila arg0) throws DatoExc {
        //TODO: Permitir comprar mediante casting automatico
        if(arg0.getTipoDato() != this.getTipoDato())
            throw new DatoExc(this);
        if(valor < arg0.toChar())
            return -1;
        else if (valor > arg0.toChar())
            return 1;
        else
            return 0;
    }

    @Override
    public void escribete(DataOutputStream dos) throws IOException {
        super.escribete(dos);
        dos.writeChar(valor);
    }
}
