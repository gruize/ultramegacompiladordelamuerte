package pila.interprete;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import pila.interprete.datos.DatoPila;
import pila.interprete.datos.Entero;
import sun.awt.SunGraphicsCallback.PrintHeavyweightComponentsCallback;

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
    	//inicializamos la parte estática para que no de errores a la hora 
    	//de salvar displays que son nulos.
    	//for (int i=0;i<longStaticMem;i++) memoria[i]=new Entero(-1);
    	heapIndex = longStaticMem;
    	huecos = new TreeSet<Hueco>();
   		huecos.add(new Hueco(longStaticMem,longMem-longStaticMem));
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
    	//TODO Sin hacer conforme a los últimos cambios.
    	return false;
  
//    	int i;
//    	Hueco lastHueco = huecos.last();
//    	
//    	//Si disminuyo la memoria din�mica...
//    	if (newHeapIndex > heapIndex)
//    	{
//    		//Si el �ltimo hueco est� fuera del �rea a disminuir y ocupa todo lo que queda de memoria din�mica
//    		// significa que puedo hacer la reducci�n
//    		if ((lastHueco.getDireccion() >= newHeapIndex) && (lastHueco.getDireccion() - lastHueco.getTamanno() == heapIndex)) 
//        	{
//        		huecos.remove(lastHueco);
//        		
//        		//Si el �ltimo hueco no ocupaba exactamente el �rea reducida, tengo que actualizar su tama�o
//        		if (lastHueco.getDireccion() > newHeapIndex)
//        		{
//        			huecos.add(new Hueco(lastHueco.getDireccion(), lastHueco.getDireccion() - newHeapIndex));
//        		}
//        		heapIndex = newHeapIndex;
//        	}
//    	}
//    	//Si disminuyo la memoria est�tica...
//    	else if (newHeapIndex < heapIndex)
//    	{
//    		//Compruebo que el espacio a reducir no est� ocupado
//    		i = newHeapIndex;
//        	while (i <= heapIndex && memoria[i] == null) 
//        	{
//        		i++;
//        	}
//        	if (i == heapIndex) 
//        	{
//        		//Actualizo el �ltimo hueco de la memoria din�mica
//        		//Si el �ltimo hueco correspond�a a la parte final de la memoria din�mica, actualizo ese hueco (lo hago mas grande)
//        		if (lastHueco.getDireccion() - lastHueco.getTamanno() == newHeapIndex) 
//        		{
//        			huecos.remove(lastHueco);
//        			huecos.add(new Hueco(lastHueco.getDireccion(), lastHueco.getTamanno() + heapIndex - newHeapIndex));
//        		}
//        		//Si no (si la �ltima parte estaba ocupada) a�ado un nuevo hueco con la parte que le he a�adido
//        		else
//        		{
//        			huecos.add(new Hueco(heapIndex, heapIndex - newHeapIndex));
//        		}
//        		heapIndex = newHeapIndex;
//        	}
//    	}
//
//    	return heapIndex == newHeapIndex;
    }
    
    /**
     * Reserva en memoria el tamaño del hueco indicado y devuelve la direccion
     * @param hueco Tamaño del hueco buscado
     * @return Direccion del primer hueco con ese tamaño
     */
    public int reservar(int hueco) 
    {
    	Iterator<Hueco> iterator=huecos.iterator();
    	Hueco huecoAux;
    	int direccion = -1;
    	
    	while (iterator.hasNext()) 
    	{
    		huecoAux = iterator.next();
    		if (huecoAux.getTamanno() > hueco) 
    		{
    			direccion = huecoAux.getDireccion() + huecoAux.getTamanno() - hueco;
    			//el tamaño es el que tenía menos lo que le quitamos
    			huecoAux.setTamanno(huecoAux.getTamanno()-hueco);
    			break;
    		}
    		else if (huecoAux.getTamanno() == hueco) 
    		{
    			direccion = huecoAux.getDireccion();
    			huecos.remove(huecoAux);
    			break;
    		}
    	}
    	return direccion;
    }   
    
    /**
     * Añade espacio a un hueco a la lista o crea un nuevo hueco
     * @param direccion Direcci�n donde comienza el hueco
     * @param tamanno Tama�o del hueco liberado
     */
    public void liberar(int direccion, int tamanno) 
    {
    	Iterator<Hueco> iterator=huecos.iterator();
    	Hueco huecoAux=null;
    	Hueco pred;
    	//buscar un hueco que acabe donde nosotros empezamos
    	while (iterator.hasNext()){
    		pred=huecoAux;
    		huecoAux=iterator.next();
    		if (huecoAux.getDireccion()+huecoAux.getTamanno() == direccion){
    			//el hueco acaba dónde nosotros empezamos
    			huecoAux.setTamanno(huecoAux.getTamanno()+tamanno);
    			if (pred!=null){
    				int tam=pred.getTamanno();
    				//intentamos fusionar huecos
    				if (huecoAux.getDireccion()+huecoAux.getTamanno() == pred.getDireccion()){
    					huecos.remove(pred);
    					huecoAux.setTamanno(huecoAux.getTamanno()+tam);
    				}    					
    			}
    			return;
    		}
    	}
    	//buscar un hueco que empiece donde nosotros acabamos
    	iterator=huecos.iterator();
    	while (iterator.hasNext()){
    		huecoAux=iterator.next();
    		if (huecoAux.getDireccion() == direccion + tamanno){
    			huecoAux.setDireccion(direccion);
    			huecoAux.setTamanno(huecoAux.getTamanno()+tamanno);
    			return;
    		}
    	}
    	//si no funciona nada, creamos un nuevo hueco
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
    	if (origen + tamanno >= memoria.length || destino+tamanno >= memoria.length) 
    		return false;
    	for (int i=0; i<tamanno;i++){
    		memoria[destino+i]=memoria[origen+i];
    	}
    	return true;
    }
    
    public void printHuecos(){
    	System.out.println("\n\n****Huecos****");
    	Iterator<Hueco> it=huecos.iterator();
    	while (it.hasNext()) {
    		Hueco h=it.next();
    		System.out.println("Hueco: dir:"+h.getDireccion()+", tam:"+h.getTamanno());
    	}
    }
    
    public static void main(String[] args){
    	//pruebas memoria
    	Memoria m=new Memoria(500, 100);
    	m.reservar(100);
    	m.reservar(50);
    	m.reservar(50);
    	m.printHuecos();
    	m.liberar(400, 100);
    	m.printHuecos();
    	m.liberar(350, 50);
    	m.printHuecos();
    	m.liberar(300, 50);
    	m.printHuecos();
    }
}






