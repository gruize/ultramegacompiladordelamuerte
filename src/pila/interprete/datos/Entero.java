
package pila.interprete.datos;

import pila.interprete.excepiones.DatoExc;

/**
 * Este DatoPila representa a un Entero
 */
public class Entero extends DatoPila {

    private int valor;

    public Entero(int valor) {
        super(NAT_T);
        this.valor = valor;
    }

    @Override
    public Object getValor() {
        return valor;
    }

    @Override
    public int toInt() {
        return valor;
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
    public String toString() {
        return "Entero ("+Integer.toString(valor)+")";
    }

    public int comparar(DatoPila arg0) throws DatoExc {
        //TODO: Permitir comprar mediante casting automatico
        if(arg0.getTipoDato() != this.getTipoDato())
            throw new DatoExc(this);
        if(valor < arg0.toInt())
            return -1;
        else if (valor > arg0.toInt())
            return 1;
        else
            return 0;
    }
}
