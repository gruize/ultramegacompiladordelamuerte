
package pila.interprete.datos;

import java.io.DataOutputStream;
import java.io.IOException;
import pila.interprete.excepiones.DatoExc;

/**
 * Este DatoPila representa a un Natural.
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
    public long toNatural() {
        return valor;
    }

    /*
     * TODO: Decidir si se permite
    @Override
    public float toFloat() throws DatoExc {
        
    }
     */

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
        dos.writeLong(valor);
    }
}
