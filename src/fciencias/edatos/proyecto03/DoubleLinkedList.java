package fciencias.edatos.proyecto03;  

/**
* Implementación de una lista Doblemente ligada
* @author Pintor Muñoz Pedro Joshue - 420053796
* @version 2.0 Diciembre 2021 (Anterior Noviembre 2021)
* @since Clase de Estructuras de Datos 2022-1
*/
public class DoubleLinkedList<T> implements TDADoubleList<T>{

    private NodoDoble cabeza; // Cabeza de la lista
    private NodoDoble cola; // Cola de la lista
    private int largo; // Tamaño de la lista

    /**
     * Nodo de una lista doblemente ligada.
     */
    public class NodoDoble{

        private T elemento; // El elemento del nodo
        private NodoDoble siguiente; // El nodo siguiente
        private NodoDoble anterior; // El nodo anterior

        /**
        * Constructor cuando no hay nodo
        */
        public NodoDoble(T el){
            this(el,null, null); 
        }
        /**
        * Constructor cuando ya hay nodos
        */
        public NodoDoble(T el, NodoDoble s, NodoDoble a){ // elemento (el), siguiente (s) y anterior (a)
            elemento = el;
            siguiente = s;
            anterior = a;
        }
        /**
        * Asignar anterior
        */
        public void asignarAnterior(NodoDoble nuevoNodoA){
            this.anterior = nuevoNodoA;
        }
        /**
        * Asignar siguiente
        */
        public void asignarSiguiente(NodoDoble nuevoNodoS){
            this.siguiente = nuevoNodoS;
        }
        /**
        * Obtener siguiente
        */
        public NodoDoble obtenerSiguiente(){
            return siguiente;
        }
        /**
        * Obtener anterior
        */
        public NodoDoble obtenerAnterior(){
            return anterior;
        }

        /**
        * Obtener elemento
        */
        public T obtenerElemento(){
            return elemento;
        }
    }

    @Override
    public void add(int i, T elemento)throws IndexOutOfBoundsException{
        
        NodoDoble nuevo = new NodoDoble(elemento);

        // Cuando la lista es vacía
        if(cabeza == null && cola == null){
            cabeza = nuevo;
            cola = nuevo;
            largo++;
            return;
        }

        // Cuando i no está en los rangos definidos
        if(i>largo || i<0)
            throw new IndexOutOfBoundsException("La posición no está dentro de la longitud de la lista.");

        // Cuando se agrega al inicio
        if(i == 0){
            nuevo.asignarSiguiente(cabeza); // siguiente de nuevo se asigna a cabeza
            cabeza.asignarAnterior(nuevo); // anterior de cabeza se asigna a nuevo
            cabeza = nuevo; // La cabeza ahora es nuevo
            largo++;
            return;
        }

        // Cuando se agrega al final
        if(i == largo){
            nuevo.asignarAnterior(cola); // anterior de nuevo se asigna a cola
            cola.asignarSiguiente(nuevo); // siguiente de cabeza se asigna a nuevo
            cola = nuevo; // La cabeza ahora es nuevo
            largo++;
            return;
        }

        // Cuando queremos agregar en el lugar uno
        NodoDoble iterador; 
        if(i==1){
            iterador = cabeza;
            nuevo.asignarSiguiente(iterador.obtenerSiguiente());
            nuevo.asignarAnterior(iterador);
            (iterador.obtenerSiguiente()).asignarAnterior(nuevo);
            iterador.asignarSiguiente(nuevo);
            largo++;
            iterador = cabeza; 
            return;
        }

        // Cuando queremos agregar en el lugar largo-1.
        if(i==largo-2 && i!=1){
            iterador = cola;
            nuevo.asignarAnterior(iterador.obtenerAnterior().obtenerAnterior());
            nuevo.asignarSiguiente(iterador.obtenerAnterior());
            (iterador.obtenerAnterior().obtenerAnterior()).asignarSiguiente(nuevo);
            iterador.obtenerAnterior().asignarAnterior(nuevo);
            largo++;
            iterador = cola; 
            return;
        }

        //Cuando queremos agregar en la posición i.
        if(i-1<=largo-i){ // Comienza la iteración por la cabeza.
            iterador = cabeza;
            for(int k = 0; k<i-1; k++)
                iterador = iterador.obtenerSiguiente();
            
            nuevo.asignarSiguiente(iterador.obtenerSiguiente());
            nuevo.asignarAnterior(iterador);
            (iterador.obtenerSiguiente()).asignarAnterior(nuevo);
            iterador.asignarSiguiente(nuevo);
            largo++;
            iterador = cabeza; 
            return;

        }else{ // Comienza la iteración por la cola.
            iterador = cola;
            for(int k = 0; k<largo-(i+1); k++)
                iterador = iterador.obtenerAnterior();

            nuevo.asignarAnterior(iterador.obtenerAnterior());
            nuevo.asignarSiguiente(iterador);
            (iterador.obtenerAnterior()).asignarSiguiente(nuevo);
            iterador.asignarAnterior(nuevo);
            largo++;
            iterador = cola; 
            return;
        }
    }

    @Override
    public void clear(){

        cabeza=null;
        cola=null;

        largo=0;
    }

    @Override
	public T remove(int i) throws IndexOutOfBoundsException{

        T elementoRegreso = null;

        if(largo==0){ // Está vacía la lista
            System.out.println("La lista es vacía");
            return elementoRegreso;
        }

        if(i>largo || i<0) // Cuando i no está en los rangos definidos
            throw new IndexOutOfBoundsException("La posición no está dentro de la longitud de la lista.");
            

        NodoDoble iterador; 

        if(largo==1){
            elementoRegreso = get(i);
            cabeza=null;
            cola=null;
            largo--;
            return elementoRegreso;
        }

        if(i==0){ // Cuando se borra al inicio
            elementoRegreso = get(i); // 1 2 3 4
            iterador = cabeza.obtenerSiguiente();
            iterador.asignarAnterior(null);
            cabeza = iterador;
            largo--;
            return elementoRegreso;
        }

        if(i==largo-1){ // Cuando se borra al final
            elementoRegreso = get(i);
            iterador = cola.obtenerAnterior();            
            iterador.asignarSiguiente(null);
            cola = iterador;
            largo--;
            return elementoRegreso;
        }
        
        if(i==1){ // Cuando se borra en el lugar uno
            elementoRegreso = get(i);
            iterador = cabeza.obtenerSiguiente();
            iterador = iterador.obtenerSiguiente(); // aqui esta en dos
            cabeza.asignarSiguiente(iterador); // cero a dos
            iterador.asignarAnterior(cabeza); // dos a cero
            largo--;
            return elementoRegreso;
        }

        if(i==largo-2 && i!=1){ // Cuando se borra en el lugar largo -1
            elementoRegreso = get(i);
            iterador = cola.obtenerAnterior(); 
            iterador = iterador.obtenerAnterior(); 
            cola.asignarAnterior(iterador); 
            iterador.asignarSiguiente(cola); 
            largo--;
            return elementoRegreso;
        }

        //Cuando queremos agregar en la posición i.
        if(i<=largo-i){ // Comienza la iteración por la cabeza.
            iterador = cabeza;
            for(int k = 0; k<i-1; k++)
                iterador = iterador.obtenerSiguiente(); // El que está ante 
            elementoRegreso = get(i);
            NodoDoble aux = iterador.obtenerSiguiente(); //[iterador][aux, porEliminar][]
            aux = aux.obtenerSiguiente(); //[iterador][porEliminar][aux]
            iterador.asignarSiguiente(aux);
            aux.asignarAnterior(iterador);
            largo--;
            return elementoRegreso;
        }else{ // Comienza la iteración por la cola.
            iterador = cola;
            for(int k = 0; k<largo-(i); k++)    
                iterador = iterador.obtenerAnterior(); // Uno antes
            elementoRegreso = get(i);
            NodoDoble aux = iterador.obtenerSiguiente(); 
            aux = aux.obtenerSiguiente(); 
            iterador.asignarSiguiente(aux);
            aux.asignarAnterior(iterador);
            largo--;
            return elementoRegreso;
        }
    
        //return elementoRegreso;
    }

    @Override
    public T get(int i) throws IndexOutOfBoundsException{
        NodoDoble iteradorC = cabeza;
        NodoDoble iteradorT = cola;

        T elementoRegreso = iteradorC.obtenerElemento(); // me pide que ya esté iniciada

        // Está vacía 
        if(largo==0){
            System.out.println("La lista es vacía");
        }

        // Cuando i no está en los rangos definidos
        if(i>largo || i<0)
            throw new IndexOutOfBoundsException("La posición no está dentro de la longitud de la lista.");

        // Cabeza o cola
        if(i==0)
            elementoRegreso = iteradorC.obtenerElemento();
        
        if(i==largo-1)
            elementoRegreso = iteradorT.obtenerElemento();
        

        // Otro lugar
        if(largo/2 >= i || largo==1){ // Lado izquierdo
            for(int j=0; j<(largo/2)+1 ; j++ ){ 
                if(i==j){
                    elementoRegreso = iteradorC.obtenerElemento(); // Empieza en cero
                    break;
                }else{
                    iteradorC = iteradorC.obtenerSiguiente();
                }
            }
        }else{ // Lado derecho
            for(int j=0; j<(largo/2)-1 ; j++ ){ 
                if(i==((largo-1)-j)){ // empieza al final, ej: i=6 y la lista mide 10, j lo encuentra en cuatro -> largo-j == i
                    elementoRegreso = iteradorT.obtenerElemento();
                    break;
                }else{
                    iteradorT = iteradorT.obtenerAnterior();
                }
            }
        }    
        return elementoRegreso;
    }

    @Override
    public boolean isEmpty(){
        if(cabeza==null && cola==null)
            return true;
        else
            return false;        
    }

    @Override
    public int size(){
        return largo;        
    }

    @Override
    public String toString(){
        String formato = "";
        NodoDoble iterador = cabeza;
        while(iterador != null){
            formato += iterador.obtenerElemento()+"\n";
            iterador = iterador.obtenerSiguiente();
        }
        return formato;
    }
}
