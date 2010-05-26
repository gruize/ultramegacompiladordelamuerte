package pila.interprete;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import pila.interprete.datos.DatoPila;

public class Memoria {
    
	private DatoPila[] memoria;
    private int heapIndex; //tama�o de la memoria est�tica
    /**
     * Esta variable ser� un �rbol ordenado de clave-valor donde la 
     * clave ser� el �ndice del hueco en el heap y el valor el n�mero
     * de celdas vac�as del hueco
     */
    private TreeSet<Hueco> huecos;
    
    public Memoria() 
    {
    	this(400, 100);
    }
    
    public Memoria(int longMem) 
    {
    	this(longMem, 300);
    }
    
    public Memoria(int longMem, int longStaticMem) 
    {
    	memoria = new DatoPila[longMem];
    	heapIndex = longStaticMem;
    	huecos = new TreeSet<Hueco>(Collections.reverseOrder());
   		huecos.add(new Hueco(longMem - 1, longMem - longStaticMem - 1));
    }
    
    public DatoPila[] getMemoria() 
    {
    	return this.memoria;
    }
    
    public int getHeapIndex() 
    {
    	return this.heapIndex;
    }
    
    /**
     * Desplaza el �ndice que separa la memoria est�tica del heap
     * @param newHeapIndex Nuevo valor del �ndice
     * @return true si se puede desplazar y false en caso contrario
     */
    public boolean setHeapIndex(int newHeapIndex) 
    {
    	int i;
    	Hueco lastHueco = huecos.last();
    	
    	//Si disminuyo la memoria din�mica...
    	if (newHeapIndex > heapIndex)
    	{
    		//Si el �ltimo hueco est� fuera del �rea a disminuir y ocupa todo lo que queda de memoria din�mica
    		// significa que puedo hacer la reducci�n
    		if ((lastHueco.getDireccion() >= newHeapIndex) && (lastHueco.getDireccion() - lastHueco.getTamanno() == heapIndex)) 
        	{
        		huecos.remove(lastHueco);
        		
        		//Si el �ltimo hueco no ocupaba exactamente el �rea reducida, tengo que actualizar su tama�o
        		if (lastHueco.getDireccion() > newHeapIndex)
        		{
        			huecos.add(new Hueco(lastHueco.getDireccion(), lastHueco.getDireccion() - newHeapIndex));
        		}
        		heapIndex = newHeapIndex;
        	}
    	}
    	//Si disminuyo la memoria est�tica...
    	else if (newHeapIndex < heapIndex)
    	{
    		//Compruebo que el espacio a reducir no est� ocupado
    		i = newHeapIndex;
        	while (i <= heapIndex && memoria[i] == null) 
        	{
        		i++;
        	}
        	if (i == heapIndex) 
        	{
        		//Actualizo el �ltimo hueco de la memoria din�mica
        		//Si el �ltimo hueco correspond�a a la parte final de la memoria din�mica, actualizo ese hueco (lo hago mas grande)
        		if (lastHueco.getDireccion() - lastHueco.getTamanno() == newHeapIndex) 
        		{
        			huecos.remove(lastHueco);
        			huecos.add(new Hueco(lastHueco.getDireccion(), lastHueco.getTamanno() + heapIndex - newHeapIndex));
        		}
        		//Si no (si la �ltima parte estaba ocupada) a�ado un nuevo hueco con la parte que le he a�adido
        		else
        		{
        			huecos.add(new Hueco(heapIndex, heapIndex - newHeapIndex));
        		}
        		heapIndex = newHeapIndex;
        	}
    	}

    	return heapIndex == newHeapIndex;
    }
    
    /**
     * Reserva en memoria el tama�o del hueco indicado y devuelve la direcci�n
     * @param hueco Tama�o del hueco buscado
     * @return Direcci�n del primer hueco con ese tama�o
     */
    public int reservar(int hueco) 
    {
    	boolean encontrado = false;
    	Iterator<Hueco> iterator;
    	Hueco huecoAux;
    	int direccion = -1;
    	
    	if (huecos.size() > 0)
    	{
    		iterator = huecos.iterator();
        	while (!encontrado && (iterator.hasNext())) 
        	{
        		huecoAux = iterator.next();
        		if (huecoAux.getTamanno() > hueco) 
        		{
        			direccion = huecoAux.getDireccion();
        			huecos.remove(huecoAux);
        			huecos.add(new Hueco(direccion - hueco -1, huecoAux.getTamanno() - hueco));
        			direccion=direccion-hueco;
        			//FIXME +1???
        			encontrado = true;
        		}
        		else if (huecoAux.getTamanno() == hueco) 
        		{
        			direccion = huecoAux.getDireccion() - hueco;
        			huecos.remove(huecoAux);
        			encontrado = true;
        		}
        	}
    	}  	
    	return direccion;
    }   
    
    /**
     * A�ade un hueco a la lista
     * @param direccion Direcci�n donde comienza el hueco
     * @param tamanno Tama�o del hueco liberado
     */
    public void liberar(int direccion, int tamanno) 
    {
    	Iterator<Hueco> iterator;
    	Hueco huecoAux = null;
    	boolean encontrado = false;
    	
    	//Si existe un hueco a continuaci�n del que quiero insertar, lo elimino e inserto uno que englobe a los dos.
    	if (huecos.contains(direccion - tamanno))
    	{
    		iterator = huecos.iterator();
        	while (!encontrado && (iterator.hasNext())) 
        	{
        		huecoAux = iterator.next();
        		if (huecoAux.getDireccion() == (direccion - tamanno)) 
        		{
        			huecos.remove(huecoAux);
        			encontrado = true;
        		}
        	}
        	if (huecoAux != null)
        		huecos.add(new Hueco(direccion, tamanno + huecoAux.getTamanno()));
        	else
        		huecos.add(new Hueco(direccion, tamanno));
    	} 
    	else
    		huecos.add(new Hueco(direccion, tamanno));
    }
    
    /**
     * Mueve el contenido de tamanno celdas del origen al destino
     * @param origen Direcci�n de origen
     * @param destino Direcci�n de destino
     * @param tamanno N�mero de celdas a mover
     * @return true si la operaci�n se realiza con �xito y false en caso contrario
     */
    public boolean mover(int origen, int destino, int tamanno) 
    {
    	boolean ok = false;
    	
    	if ((origen - tamanno >= heapIndex) && (destino - tamanno >= heapIndex))
    	{
        	for (int i = 0; i < tamanno; i++) 
        	{
        		memoria[destino - i] = memoria[origen - i];
        	}
        	liberar(origen, tamanno);
        	ok = true;
    	}
    	return ok;   	
    }
}
