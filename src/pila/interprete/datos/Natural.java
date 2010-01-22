
package pila.interprete.datos;

import java.io.DataOutputStream;
import java.io.IOException;

import pila.interprete.excepiones.DatoExc;

/**
 * Este DatoPila representa a un Natural.
 */
public class Natural extends DatoPila {

    private int valor;


    public Natural(int valor) throws DatoExc {
        super(NAT_T);
        this.valor = valor;
        if(valor < 0)
            throw new DatoExc(this, "No puede asignarse un valor negativo a un natural");
    }

    @Override
    public Object getValor() {
        return valor;
    }

    @Override
    public int toNatural() {
        return valor;
    }

    @Override
    public char toChar() throws DatoExc {
        char c = (char)valor;
        if(c != valor)
            super.toChar(); //esto lanzara excepcion
        return c;
    }

    @Override
    public int toInt() {
        return valor;
    }

    @Override
    public float toFloat() {
        return valor;
    }

    @Override
    public String toString() {
        return "Natural ("+Long.toString(valor)+")";
    }

    public int comparar(DatoPila arg0) throws DatoExc {
        //TODO: Permitir comprar mediante casting automatico
        if(arg0.getTipoDato() != this.getTipoDato())
            throw new DatoExc(this);
        if(valor < arg0.toNatural())
            return -1;
        else if (valor > arg0.toNatural())
            return 1;
        else
            return 0;
    }

    @Override
    public void escribete(DataOutputStream dos) throws IOException {
        super.escribete(dos);
        dos.writeInt(valor);
    }
}
