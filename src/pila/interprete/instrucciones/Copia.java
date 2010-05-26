package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

public class Copia extends InstruccionInterprete {

	public Copia() throws LectorExc {
		super(InstruccionInterprete.CODIGO_COPIA);
	}

	public Copia(DatoPila d) throws LectorExc {
		super(InstruccionInterprete.CODIGO_COPIA);
		throw new LectorExc("La instrucción no " + "acepta argumentos");
	}

	@Override
	public String toString() {
		return "Copia ";// + getDato();
	}

	/**
	 * Semantica: copia()
	 * 
	 * @return siempre true (nunca modifica el cp del interprete)
	 */
	@Override
	public boolean ejecutate(Interprete interprete) throws InstruccionExc {

		interprete.getPila().push(interprete.getPila().getFirst());

		return true;
	}
}
