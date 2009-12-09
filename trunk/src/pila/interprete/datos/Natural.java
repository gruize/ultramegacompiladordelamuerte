
package pila.interprete.datos;

import pila.interprete.excepiones.DatoExc;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar
 */
public class Natural extends DatoPila {

    private long valor;

    public Natural(long valor) {
        super(NAT_T);
        this.valor = valor;
    }

    @Override
    public Object getValor() {
        return valor;
    }

    @Override
    public int toInt() {
        throw new ClassCastException("No puede transformarse "
                +"un natural en un entero");
    }

    @Override
    public long toNatural() {
        return valor;
    }

    @Override
    public float toFloat() {
        throw new ClassCastException("No puede transformarse "
                +"un natual en un real");
    }

    @Override
    public char toChar() {
        throw new ClassCastException("No puede transformarse "
                +"un natural en un char");
    }

    @Override
    public boolean toBoolean() {
        throw new ClassCastException("No puede transformarse "
                +"un natural en un booleano");
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
}
