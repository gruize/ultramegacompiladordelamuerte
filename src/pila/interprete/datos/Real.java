
package pila.interprete.datos;

import pila.interprete.excepiones.DatoExc;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar
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
    public int toInt() {
        return Math.round(valor);
    }

    @Override
    public long toNatural() {
        return (long)Math.round(valor);
    }

    @Override
    public float toFloat() {
        return valor;
    }

    @Override
    public char toChar() {
        throw new ClassCastException("No puede transformarse "
                +"un real en un char");
    }

    @Override
    public boolean toBoolean() {
        throw new ClassCastException("No puede transformarse "
                +"un real en un booleano");
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
}
