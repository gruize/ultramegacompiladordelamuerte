package compilador.traductor;

import java.util.ArrayList;

/**
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
import pila.interprete.instrucciones.InstruccionInterprete;

public class Codigo {

    private ArrayList<InstruccionInterprete> cod;

    public Codigo() {
        cod = new ArrayList<InstruccionInterprete>();
    }

    public Codigo(InstruccionInterprete i) {
        cod = new ArrayList<InstruccionInterprete>();
        cod.add(i);
    }

    public ArrayList<InstruccionInterprete> getCod() {
        return cod;
    }

    public boolean appendCod(Codigo c) {
        return cod.addAll(c.getCod());
    }

    public void appendIns(InstruccionInterprete i) {
        cod.add(i);
    }

    public void insertaCod(InstruccionInterprete i, int aux) {
        cod.set(aux, i);
    }
}
