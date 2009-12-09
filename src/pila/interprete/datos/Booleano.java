
package pila.interprete.datos;

import pila.interprete.excepiones.DatoExc;

/**
 *
 * @author Gonzalo Ortiz Jaureguizar
 */
public class Booleano extends DatoPila {

    private boolean valor;

    public Booleano(boolean valor) {
        super(BOOL_T);
        this.valor = valor;
    }

    @Override
    public Object getValor() {
        return valor;
    }

    @Override
    public int toInt() {
        throw new ClassCastException("No puede transformarse "
                +"un booleano en un entero");
    }

    @Override
    public long toNatural() {
        throw new ClassCastException("No puede transformarse "
                +"un booleano en un natural");
    }

    @Override
    public float toFloat() {
        throw new ClassCastException("No puede transformarse "
                +"un booleano en un float");
    }

    @Override
    public char toChar() {
        throw new ClassCastException("No puede transformarse "
                +"un booleano en un char");
    }

    @Override
    public boolean toBoolean() {
        return valor;
    }

    @Override
    public String toString() {
        return "Booleano ("+Boolean.toString(valor)+")";
    }

    public int comparar(DatoPila arg0) throws DatoExc {
        if(arg0.getTipoDato() != this.getTipoDato())
            throw new DatoExc(this);
        if(valor)
            if(arg0.toBoolean())
                return 0;
            else
                return 1;
        else
            if(arg0.toBoolean())
                return -1;
            else
                return 0;
    }
}
