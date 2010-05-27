package pila.interprete;

public class Hueco implements Comparable<Hueco>
{
	
	private Integer direccion;
	private Integer tamanno;
	
	public Hueco (int dir, int tam) 
	{
		direccion = dir;
		tamanno = tam;
	}

	@Override
	public int compareTo(Hueco o) 
	{
		Hueco hueco = (Hueco)o; 
		return(hueco.direccion.compareTo(this.direccion));
	}
	
	public int getDireccion() 
	{
		return direccion;
	}
	
	public void setDireccion(Integer direccion) {
		this.direccion = direccion;
	}

	public void setTamanno(Integer tamanno) {
		this.tamanno = tamanno;
	}

	public int getTamanno()
	{
		return tamanno;
	}
	
	
	

}
