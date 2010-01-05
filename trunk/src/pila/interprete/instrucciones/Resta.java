package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 *
 * @author ruben
 */
public class Resta extends InstruccionInterprete{

    public Resta() throws LectorExc{
        super(InstruccionInterprete.CODIGO_RESTA);
    }

    public Resta(DatoPila d) throws LectorExc{
        super(InstruccionInterprete.CODIGO_RESTA);
        throw new LectorExc("La instrucción no admite operadores");
    }

    @Override
    public String toString() {
        return "Resta";
    }

    /**
     * Semantica:
     * apilar(DatoQueSea(desapilar - desapilar))
     * @return siempre true (nunca modifica el cp del interprete)
     * @throws InstruccionExc si encuentra tipos de datos incompatibles o si dos
     * naturales dan como resultado un numero negativo
     */
    @Override
    public boolean ejecutate(Interprete interprete) throws InstruccionExc {
        /*
         * TODO: Implementar
        DatoPila d1 = interprete.getPila().removeFirst();
        DatoPila d2 = interprete.getPila().removeFirst();
        DatoPila res;
        byte t1 = d1.getTipoDato();
        byte t2 = d2.getTipoDato();
        if (t1 != t2){
            throw new InstruccionExc(this,"Operadores invalidos ("
                    + d1.toString()+" - "+ d2.toString()+")");
        }
        else{
            try {
                if (d1.comparar(d2) < 0) {
                    throw new InstruccionExc(this, d1.toString() + " mayor que " + d2.toString());
                }
                switch (d1.getTipoDato()) {
                    case DatoPila.NAT_T:
                        res = new Natural(d1.toNatural() - d2.toNatural());
                        break;
                    case DatoPila.INT_T:
                        res = new Entero(d1.toInt() - d2.toInt());
                        break;
                    case DatoPila.FLOAT_T:
                        res = new Real(d1.toFloat() - d2.toFloat());
                        break;
                    default:
                        throw new InstruccionExc(this, "Tipo inválido (" + d1.toString() + ")");
                }
                interprete.getPila().addFirst(res);
                interprete.setCp(interprete.getCp());
            } catch (DatoExc ex) {
                Logger.getLogger(Resta.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         */
        return true;
    }

}
