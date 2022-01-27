package fciencias.edatos.proyecto03;  

/**
* TDA para listas Doblemente ligadas.
* @author Pedro Joshue Pintor Muñoz
* @version 1.0 Noviembre 2021.
* @since Estructuras de datos 2022-1.
*/
public interface TDADoubleList<T>{

	/**
	 * Inserta un nuevo elemento <i>e</i> en la posición <i>i</i>.
	 * @param i la posición donde agregar el elemento.
	 * @param e el elemento a insertar.
	 * @throws IndexOutOfBoundException si el índice está fuera de rango.
	 */
	public void add(int i, T e) throws IndexOutOfBoundsException;

	/**
	 * Limpia la lista. Elimina todos los elementos.
	 */
	public void clear();


	/**
	 * Obtiene el elemento en la posición <i>i</i>.
	 * @param i el índice a obtener elemento.
	 * @throws IndexOutOfBoundException si el índice está fuera de rango.
	 */
	public T get(int i) throws IndexOutOfBoundsException;

	/**
	 * Verifica si la lista está vacía.
	 * @return true si la lista no contiene elementos, false en otro caso.
	 */
	public boolean isEmpty();

	/**
	 * Regresa la cantidad de elementos contenidos en la lista.
	 * @return la cantidad de elementos contenidos.
	 */
	public int size();

	/**
	* Elimina el elemento en la posición <i>i</i>.
	* @param i el índice del elemento a eliminar.
	* @return el elemento eliminado.
	* @throws IndexOutOfBoundException si el índice está fuera de rango.
	*/
	public T remove(int i) throws IndexOutOfBoundsException;

	/**
	 * Da una cadena con los elementos contenidos en la lista.
	 * @return una representación de la lista.
	 */
	public String toString();

}
