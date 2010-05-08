package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

/**
 * 
 * @author GRUPO 3: Gonzalo Ortiz Jaureguizar, Alicia Perez Jimenez, Laura
 *         Reyero Sainz, Hector Sanjuan Redondo, Ruben Tarancon Garijo
 */

public class Delete extends InstruccionInterprete {

	public Delete() throws LectorExc {
		super(InstruccionInterprete.CODIGO_DELETE);
		throw new LectorExc("La instrucci√≥n requiere un  argumento entero");
	}

	public Delete(DatoPila d) throws LectorExc {
		super(InstruccionInterprete.CODIGO_DELETE, d);
		if (d.getTipoDato() != DatoPila.NAT_T) {
			throw new LectorExc("La instrucci√≥n requiere un argumento entero");
		}
	}

	@Override
	public String toString() {
		return "Delete " + getDato();
	}

	/**
	 * Semantica: 
	 * 1. desapilar la direcciÛn que se desea liberar 
	 * 1. libera las siguientes t celdas consecutivas a esa direcciÛn.
	 * 
	 * @return siempre true (nunca modifica el cp del interprete)
	 */
	@Override
	public boolean ejecutate(Interprete interprete) throws InstruccionExc {
		try {
			switch (getDato().getTipoDato()) {
			case DatoPila.INT_T:
				int dir = interprete.getPila().getFirst().toInt();
				interprete.getMemoria().liberar(dir, getDato().toInt());
				break;
			default:
				throw new InstruccionExc(this, "Tipo inv√°lido ("
						+ getDato().toString() + ")");
			}

		} catch (DatoExc ex) {
			throw new InstruccionExc(this, ex.getMessage());
		}
		return true;
	}
}
