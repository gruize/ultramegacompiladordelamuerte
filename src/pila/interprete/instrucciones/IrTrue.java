package pila.interprete.instrucciones;

import pila.interprete.Interprete;
import pila.interprete.datos.DatoPila;
import pila.interprete.excepiones.DatoExc;
import pila.interprete.excepiones.InstruccionExc;
import pila.interprete.excepiones.LectorExc;

public class IrTrue  extends InstruccionInterprete {
	public byte tipo;

	public IrTrue() throws LectorExc {
		super(InstruccionInterprete.CODIGO_IR_T);
		throw new LectorExc("La instrucción ir-true necesita un parámetro");
	}

	public IrTrue(DatoPila d) throws LectorExc {
		super(InstruccionInterprete.CODIGO_IR_T, d);
	}

	@Override
	public String toString() {
		return "ir-t " + getDato();
	}

	@Override
	public boolean ejecutate(Interprete interprete) throws InstruccionExc 
	{
		boolean he_saltado = false;
		DatoPila salto = interprete.getPila().pop();
		try
		{
			if ((salto.getTipoDato() == DatoPila.BOOL_T) && (getDato().getTipoDato() == DatoPila.NAT_T))
				if (salto.toBoolean()) {
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