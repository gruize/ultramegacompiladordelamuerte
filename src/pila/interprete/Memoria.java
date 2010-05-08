package pila.interprete;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import pila.interprete.datos.DatoPila;

public class Memoria {
    
	private DatoPila[] memoria;
    private int heapIndex; //tamaño de la memoria estática
    /**
     * Esta variable será un árbol ordenado de clave-valor donde la 
     * clave será el índice del hueco en el heap y el valor el número
     * de celdas vacías del hueco
     */
    private TreeSet<Hueco> huecos;
    
    public Memoria() 
    {
    	this(100, 25);
    }
    
    public Memoria(int longMem) 
    {
    	this(longMem, 25);
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
     * Desplaza el índice que separa la memoria estática del heap
     * @param newHeapIndex Nuevo valor del índice
     * @return true si se puede desplazar y false en caso contrario
     */
    public boolean setHeapIndex(int newHeapIndex) 
    {
    	int i;
    	Hueco lastHueco = huecos.last();
    	
    	//Si disminuyo la memoria dinámica...
    	if (newHeapIndex > heapIndex)
    	{
    		//Si el último hueco está fuera del área a disminuir y ocupa todo lo que queda de memoria dinámica
    		// significa que puedo hacer la reducción
    		if ((lastHueco.getDireccion() >= newHeapIndex) && (lastHueco.getDireccion() - lastHueco.getTamanno() == heapIndex)) 
        	{
        		huecos.remove(lastHueco);
        		
        		//Si el último hueco no ocupaba exactamente el área reducida, tengo que actualizar su tamaño
        		if (lastHueco.getDireccion() > newHeapIndex)
        		{
        			huecos.add(new Hueco(lastHueco.getDireccion(), lastHueco.getDireccion() - newHeapIndex));
        		}
        		heapIndex = newHeapIndex;
        	}
    	}
    	//Si disminuyo la memoria estática...
    	else if (newHeapIndex < heapIndex)
    	{
    		//Compruebo que el espacio a reducir no esté ocupado
    		i = newHeapIndex;
        	while (i <= heapIndex && memoria[i] == null) 
        	{
        		i++;
        	}
        	if (i == heapIndex) 
        	{
        		//Actualizo el último hueco de la memoria dinámica
        		//Si el último hueco correspondía a la parte final de la memoria dinámica, actualizo ese hueco (lo hago mas grande)
        		if (lastHueco.getDireccion() - lastHueco.getTamanno() == newHeapIndex) 
        		{
        			huecos.remove(lastHueco);
        			huecos.add(new Hueco(lastHueco.getDireccion(), lastHueco.getTamanno() + heapIndex - newHeapIndex));
        		}
        		//Si no (si la última parte estaba ocupada) añado un nuevo hueco con la parte que le he añadido
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
     * Reserva en memoria el tamaño del hueco indicado y devuelve la dirección
     * @param hueco Tamaño del hueco buscado
     * @return Dirección del primer hueco con ese tamaño
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
        			huecos.add(new Hueco(direccion - hueco, huecoAux.getTamanno() - hueco));
        			encontrado = true;
        		}
        		else if (huecoAux.getTamanno() >= hueco) 
        		{
        			direccion = huecoAux.getDireccion();
        			huecos.remove(huecoAux);
        			encontrado = true;
        		}
        	}
    	}  	
    	return direccion;
    }   
    
    /**
     * Añade un hueco a la lista
     * @param direccion Dirección donde comienza el hueco
     * @param tamanno Tamaño del hueco liberado
     */
    public void liberar(int direccion, int tamanno) 
    {
    	Iterator<Hueco> iterator;
    	Hueco huecoAux = null;
    	boolean encontrado = false;
    	
    	//Si existe un hueco a continuación del que quiero insertar, lo elimino e inserto uno que englobe a los dos.
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
     * @param origen Dirección de origen
     * @param destino Dirección de destino
     * @param tamanno Número de celdas a mover
     * @return true si la operación se realiza con éxito y false en caso contrario
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
