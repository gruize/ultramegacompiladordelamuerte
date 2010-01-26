package pila.interprete.excepiones;

import pila.interprete.instrucciones.InstruccionInterprete;

/**
 * Esta excepción se lanza cuando se produce un error al
 * ejecutar una instrucción. Por ejemplo, al intentar
 * sumar un natural y un booleano. Tiene un atributo
 * "instruccion" que permite obtener la instrucción que
 * falló.
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class InstruccionExc extends Exception {
    
    private InstruccionInterprete inst;

    public InstruccionExc(InstruccionInterprete inst) {
        super();
        this.inst = inst;
    }

    public InstruccionExc(InstruccionInterprete inst, String str) {
        super(str);
        this.inst = inst;
    }

    public InstruccionInterprete getInstruccion() {
        return inst;
    }

    @Override
    public String getMessage() {
        return inst.toString()+": "+super.getMessage();
    }
}
