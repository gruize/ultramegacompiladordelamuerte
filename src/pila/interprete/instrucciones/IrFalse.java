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

public class IrFalse extends InstruccionInterprete {
	public byte tipo;

	public IrFalse() throws LectorExc {
		super(InstruccionInterprete.CODIGO_IR_F);
		throw new LectorExc("La instrucción ir-false necesita un parámetro");
	}

	public IrFalse(DatoPila d) throws LectorExc {
		super(InstruccionInterprete.CODIGO_IR_F, d);
	}

	@Override
	public String toString() {
		return "ir-f " + getDato();
	}

	@Override
	public boolean ejecutate(Interprete interprete) throws InstruccionExc 
	{
		boolean he_saltado = false;
		DatoPila salto = interprete.getPila().pop();
		try
		{
			if ((salto.getTipoDato() == DatoPila.BOOL_T) && (getDato().getTipoDato() == DatoPila.NAT_T))
				if (!salto.toBoolean()) {
					interprete.setCp(getDato().toInt());
					he_saltado = true;
				}
				else {
					he_saltado = false;
				}
			else 
				throw new InstruccionExc(this, "Tipo inválido ("
						+ getDato().toString() + ")");
		} 
		catch (DatoExc ex) 
		{
			throw new InstruccionExc(this, ex.getMessage());
		}
		return !he_saltado;
	}

}