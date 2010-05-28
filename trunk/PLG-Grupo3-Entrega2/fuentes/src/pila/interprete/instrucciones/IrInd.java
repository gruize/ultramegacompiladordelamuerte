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

public class IrInd  extends InstruccionInterprete {
	public byte tipo;

	public IrInd() throws LectorExc {
		super(InstruccionInterprete.CODIGO_IR_IND);
	}

	public IrInd(DatoPila d) throws LectorExc {
		super(InstruccionInterprete.CODIGO_IR_IND, d);
		throw new LectorExc("La instrucción ir-ind no acepta parámetros");
	}

	@Override
	public String toString() {
		return "ir-ind ";
	}

	@Override
	public boolean ejecutate(Interprete interprete) throws InstruccionExc 
	{
		DatoPila direccion = interprete.getPila().pop();
		try
		{
			if (direccion.getTipoDato() == DatoPila.INT_T ||
					direccion.getTipoDato() == DatoPila.NAT_T)
				interprete.setCp(direccion.toInt());
			else 
				throw new InstruccionExc(this, "Tipo inválido ("
						+ getDato().toString() + ")");
		} 
		catch (DatoExc ex) 
		{
			throw new InstruccionExc(this, ex.getMessage());
		}
		return false; //no actualizar CP
	}

}
