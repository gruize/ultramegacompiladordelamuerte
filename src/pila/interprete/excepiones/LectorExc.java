package pila.interprete.excepiones;


/**
 * Esta exepción puede ser lanzada en el momento de
 * leer el bytecode debido a fallos de codificación
 * (por ejemplo, no pasarle parametros a un apila)
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */
public class LectorExc extends Exception {
    
    public LectorExc() {
        super();
    }

    public LectorExc(String str) {
        super(str);
    }

}
