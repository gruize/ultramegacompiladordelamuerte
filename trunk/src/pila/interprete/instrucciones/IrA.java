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
public class IrA extends InstruccionInterprete {
	public byte tipo;

	public IrA() throws LectorExc {
		super(InstruccionInterprete.CODIGO_IR_A);
		throw new LectorExc("La instrucción ir-a necesita " + "un parámetro");
	}

	public IrA(DatoPila d) throws LectorExc {
		super(InstruccionInterprete.CODIGO_IR_A, d);
	}

	@Override
	public String toString() {
		return "ir-a " + getDato();
	}

	@Override
	public boolean ejecutate(Interprete interprete) throws InstruccionExc 
	{
		try
		{
			if (getDato().getTipoDato() == DatoPila.NAT_T)
				interprete.setCp(getDato().toInt() -1 ); //porque se va a avanzar
			else 
				throw new InstruccionExc(this, "Tipo inválido ("
						+ getDato().toString() + ")");
		} 
		catch (DatoExc ex) 
		{
			throw new InstruccionExc(this, ex.getMessage());
		}
		return true;
	}

}
