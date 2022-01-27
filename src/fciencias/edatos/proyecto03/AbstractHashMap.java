package fciencias.edatos.proyecto03;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
* Clase para la implementación de un mapa.
* @author Pintor Muñoz Pedro Joshue 420053796.
* @version 1.0 Enero 2022.
* @since Estructuras de Datos 2022-1.
*/
public class AbstractHashMap<K,V> implements Map<K,V>{


	/** Colisiones */
	public static int colision;

	/** Arreglo de elementos. */
	private V[] table;

	/** Capacidad de la tabla. */
	private int capacity;

	/** Factor primo para calcular longitudes. */
	private int prime;

	/** Cantidad del cambio y escala. */
	private long scale, shift;

	/** Cantidad de elementos */ 
	private int tamanio;

	/**
	* Crea un nuevo AbstractHashMap. 
	* @param cap la capacidad de la tabla.
	* @param p el factor primo.
	*/
	public AbstractHashMap(int cap, int p){
		table = (V[]) new Object[cap];
		prime = p;
		capacity = cap;
		Random rn = new Random();
		scale = rn.nextInt(prime-1) + 1;
		shift = rn.nextInt(prime);
	}

	/**
	* Crea un nuevo AbstractHashMap.
	* @param cap la capacidad de la tabla.
	*/
	public AbstractHashMap(int cap){
		this(cap, 109345121);
	}

	/**
	* Crea un nuevo AbstractHashMap.
	*/
	public AbstractHashMap(){
		this(5051);
	}

	@Override
	public int size(){
		return tamanio;
	}

	@Override
	public V get(K key){
		int pos = hashFuction(key);
		return table[pos];
	}

	@Override
	public V put(K key, V value){
		int pos = hashFuction(key);
		//System.out.println("Valor: "+value+"\nPosicion: "+pos);
		V oldValue = table[pos];
		table[pos] = value;

		if(oldValue != null)
			colision++;
		else
			tamanio++;

		return oldValue;
	}

	@Override
	public V remove(K key){
		int pos = hashFuction(key);
		V oldValue = table[pos];
		table[pos] = null;
		tamanio--;
		return oldValue;
	}

	@Override
	public boolean isEmpty(){
		return (tamanio == 0) ? true : false ;
	}

	/**
	 * Funcion hash
	 * @param k la clave
	 * @return un entero asociado a la clave dentro de un rango válido
	 */
	private int hashFuction(K k){
		int hashCode = (int) (Math.abs(k.hashCode() * scale + shift) % prime);
		return hashCode % capacity;
	}

	/**
	 * Método que lee y almacena los datos en un mapa.
	 * @param rutaArchivo la ruta del archivo txt.
	 * @param map el mapa donde se almacenaran los elementos.
	 * */
	public void elementos(String rutaArchivo, AbstractHashMap map){

        String linea = null;     

        try{
            Reader reader = new FileReader(rutaArchivo);                                //Reader permite leer el archivo como un objeto de tipo File
            BufferedReader lector = new BufferedReader(reader);                         //BufferedReader permite leer el contenido en forma de cadenas 
            while((linea = lector.readLine()) != null){                                 //es por eso que el buffer recibe como parametro el reader anterior
                if(linea != null){                                                      //Para toma los datos de ese objeto para ser leidos se usa el método readLine().  
                    String[] cadena = linea.split("[,]");                                 //split Corta las lineas de codigo cada que encuentra un $.
					map.put(cadena[0],cadena[0]); //RECORDATORIO: Integer.parseInt(cadena[1]) ---- Para Que convierta la cadena en un entero.  
                }                                                                       
            }   
            lector.close();            
        }catch(FileNotFoundException fnfe){
            System.out.println("No se encuentra el archivo");
        }catch(IOException ioe){
            System.out.println("Error en la lectura del archivo");
        }

	}

	/**
	 * Método que debuelve el numero de colisiones
	 * */
	public static int getColisiones(){
		return colision;
	}
}