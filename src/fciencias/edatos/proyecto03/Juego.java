package fciencias.edatos.proyecto03;    

import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.File;
import java.io.Reader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;

/**
 * Clase principal donde se ejecuta el juego.
 * @author Pintor Muñoz Pedro Joshue - 420053796
 * @version 1.0 Enero 2022
 * @since Clase de Estructuras de Datos 2022-1
 */

public class Juego extends Thread{

    /** Contador para cronometro */
    public static int contador;

    /** Palabras ingresadas */
    public static int numP;

    /** Lista de palabras del usuario */
    public static String[] juegoPal = new String[50];

    public static TDADoubleList<Juego> todo = new DoubleLinkedList<Juego>();
    public static String[] listaS = new String[3];
    public static int[] puntoSt = new int[3];

    public Palabras p = new Palabras();

    public void comienza(){
        p.start();
    }

    public static void cronometro(){

        String rojo = "\u001B[31m" , blanco = "\u001B[37m"; 

        Thread t = new Thread();
            try{
                System.out.println(rojo+"Quedan 60 segundos"+blanco);
                t.sleep(30000);
                System.out.println(rojo+"Quedan 30 segundos"+blanco);
                contador++; 

                t.sleep(20000);
                System.out.println(rojo+"Quedan 10 segundos"+blanco);
                contador++;

                t.sleep(10000);
                System.out.println(rojo+"Se acabo el tiempo"+blanco);
                contador++;

            }catch(InterruptedException ie){}
        }

    public class Palabras extends Thread{
        @Override
        public void run(){

            Thread t = new Thread();            
            Scanner sc = new Scanner(System.in);

            while(contador != 3)                
                juegoPal[numP++] = sc.nextLine();
                         
            t.stop();
        }
    }
    /* ---------------------------------------------------------------------------------------------------- */
    
    private int pun;
    private String let;

    public Juego(String let, int pun){
        this.pun = pun;
        this.let = let;
    }
    public Juego(){
    }

    /* ---------------------------------------------------------------------------------------------------- */

    /**
     * Método que determina si las letras del juego las da la maquina o el usuario
     * @param opcion numero que determina quien de los 2 da las letras
     * */
    public static String[] seleccionLetras(int opcion){


        String cyan = "\u001B[36m", blanco = "\u001B[37m", rojo = "\u001B[31m"; 

        if(opcion == 2){        //Selecciona la computadora

            String alfabeto = "qwertyuiopasdfghjklzxcvbnmñ";
            String[] seleccion = new String[9];
            Random rn = new Random();

            for(int i=0 ; i<9 ; i++){
                seleccion[i] = alfabeto.charAt(rn.nextInt(27))+"";          //Se podria mejorar para meter mas prioridad
            }

            System.out.println(rojo+"La seleccion de letras para el juego es la siguiente: "+blanco);
            for(int i=0 ; i<seleccion.length ; i++)
                System.out.print(seleccion[i]+"  ");

            System.out.println("\n");
            return seleccion;


        } else {                //Selecciona el jugador

            String alfabeto = "qwertyuiopasdfghjklzxcvbnmñ";
            String[] seleccion = new String[9];
            String letra = new String();
            Scanner sc = new Scanner(System.in);
            int conteoLetra = 1;

            for(int i=0 ; i<9 ; i++){

                System.out.println(cyan+"Ingresa una letra para añadir al juego ("+conteoLetra+")"+blanco);
                letra = sc.nextLine();
                letra.toLowerCase();
                conteoLetra++;
                if(!(alfabeto.contains(letra)) || letra.length()>1  ){
                    if(letra.length()>1){
                        System.out.println(rojo+"Solo debe ingresar una letra a la vez"+blanco);
                        i--;
                        conteoLetra--;
                        try{
                            Thread.sleep(2000);
                        }catch(InterruptedException ie){} 
                        continue;
                    }
                    System.out.println(rojo+"Caracter incorrecto, ingresa una letra"+blanco);
                    i--;
                    conteoLetra--;
                    try{
                        Thread.sleep(2000);
                    }catch(InterruptedException ie){} 
                }
                else
                    seleccion[i] = letra;
            }

            System.out.println(rojo+"La seleccion de letras para el juego es la siguiente: "+blanco);
            for(int i=0 ; i<seleccion.length ; i++)
                System.out.print(seleccion[i]+"  ");

            System.out.println("\n\nCuando estés listo preciona enter");
            sc.nextLine();
            
            return seleccion;
        }
    }

    /**
     * Método que valida las palabras que el usuario ingreso.
     * @param listaPal La lista de palabras que el usuario ingreso.
     * @param listaLet La lista de las letras validas en el juego.
     * @param map La lista completa de palabras validas para el juego.
     * @return El puntuaje total del usuario.
     * */
    public static int validaPalabras(String[] listaPal, String[] listaLet, AbstractHashMap map){

        String[] palabrasValidas = new String[listaPal.length];
        String[] alfabeto = listaLet;
        int puntuacion = 0 , conteoPal = 0;      
        String rojo = "\u001B[31m", blanco = "\u001B[37m", cyan = "\u001B[36m"; 

        //Filtra todas las palabras dadas por el usuario y se queda con las validas
        for(int i=0 ; i<listaPal.length ; i++){
            
            listaPal[i] = listaPal[i].toLowerCase();
            
            String[] letAux = Arrays.copyOf(listaLet,9);
            int tamanio = listaPal[i].length();
            int conteoValido = 0;            
            
            for(int k=0; k<listaPal[i].length() ; k++){     //LETRAS DE LA PALABRA

                for(int j=0; j<9 ; j++){         //LETRAS VALIDAS

                    if((listaPal[i].charAt(k)+"").equals(letAux[j])){
                        if(listaPal[i].contains(letAux[j])){
                            letAux[j] = "";
                            conteoValido++;
                            break;
                        }                      
                    }

                    if((listaPal[i].charAt(k)+"").equals("á")){                              
                        int temp = -1;
                        for(int l=0; l<9 ;l++){                       
                            if(letAux[l].equals("a")){
                                temp = l;
                            }
                        }
                        if(temp != -1){
                            letAux[temp] = "";
                            conteoValido++;
                            break;          
                        }
                    }

                    if((listaPal[i].charAt(k)+"").equals("é")){                              
                        int temp = -1;
                        for(int l=0; l<9 ;l++){                       
                            if(letAux[l].equals("e")){
                                temp = l;
                            }
                        }
                        if(temp != -1){
                            letAux[temp] = "";
                            conteoValido++;
                            break;          
                        }
                    }

                    if((listaPal[i].charAt(k)+"").equals("í")){                              
                        int temp = -1;
                        for(int l=0; l<9 ;l++){                       
                            if(letAux[l].equals("i")){
                                temp = l;
                            }
                        }
                        if(temp != -1){
                            letAux[temp] = "";
                            conteoValido++;
                            break;          
                        }
                    }

                    if((listaPal[i].charAt(k)+"").equals("ó")){                              
                        int temp = -1;
                        for(int l=0; l<9 ;l++){                       
                            if(letAux[l].equals("o")){
                                temp = l;
                            }
                        }
                        if(temp != -1){
                            letAux[temp] = "";
                            conteoValido++;
                            break;          
                        }
                    }

                    if((listaPal[i].charAt(k)+"").equals("ú") || (listaPal[i].charAt(k)+"").equals("ü")){                              
                        int temp = -1;
                        for(int l=0; l<9 ;l++){                       
                            if(letAux[l].equals("u")){
                                temp = l;
                            }
                        }
                        if(temp != -1){
                            letAux[temp] = "";
                            conteoValido++;
                            break;          
                        }
                    }
                }
            }

            if(tamanio == conteoValido)
                palabrasValidas[conteoPal++] = listaPal[i];
            else
                System.out.println(listaPal[i]+rojo+" 0 puntos."+blanco);
        }

        String[] palabraRep = new String[palabrasValidas.length];
        int cont = 0;
        int noValido = 0;

        //Busca y puntua la palabra segun su longitud
        for(int i=0 ; i<palabrasValidas.length ; i++){
            noValido=0;
            if(palabrasValidas[i]!=null)
                if(map.get(palabrasValidas[i]) != null){
                    for(int j=0 ; j<palabraRep.length ; j++){
                        if(palabrasValidas[i].equals(palabraRep[j])){
                            System.out.println(palabrasValidas[i]+rojo+" 0 puntos."+blanco);
                            noValido++;
                        }
                    }
                    if(noValido==0){
                        puntuacion = puntuacion + (palabrasValidas[i].length() * palabrasValidas[i].length());
                        System.out.println(palabrasValidas[i]+" "+rojo+(palabrasValidas[i].length() * palabrasValidas[i].length())+" puntos."+blanco);
                        palabraRep[cont++] = palabrasValidas[i];
                    }
                }
        }

        System.out.println("\n"+cyan+"Tu puntuación es: "+rojo+puntuacion+blanco);
        return puntuacion;  
    }

    /* ---------------------------------------------------------------------------------------------------- */

    /**
     * Método que lee puntuaciones.
     * @param rutaArchivo la ruta del archivo txt.
     * @param todo Lista con las estadisticas
     * */
    public void cargaTodosPuntos(String rutaArchivo, TDADoubleList todo){

        String linea = null;     

        try{
            Reader reader = new FileReader(rutaArchivo);                                //Reader permite leer el archivo como un objeto de tipo File
            BufferedReader lector = new BufferedReader(reader);                         //BufferedReader permite leer el contenido en forma de cadenas 
            while((linea = lector.readLine()) != null){                                 //es por eso que el buffer recibe como parametro el reader anterior
                if(linea != null){                                                      //Para toma los datos de ese objeto para ser leidos se usa el método readLine().  
                    String[] cadena=linea.split("[$]");                                 //split Corta las lineas de codigo cada que encuentra un $.
                    Juego nuevo = new Juego(cadena[0],Integer.parseInt(cadena[1]));
                    todo.add(todo.size()-1,nuevo); //RECORDATORIO: Integer.parceInt(cadena[1]) ---- Para Que convierta la cadena en un entero.                                             
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
     * Metodo que se encarga de escribir las puntuaciones mas altas en un archivo txt.
     * @param rutaArchivo Es la ruta que tiene el archivo.
     * @param lista Es la lista de letras del juego.
     * @param puntos Es la puntuación del juego.
     * */
    public void escribeTodosPuntos(String rutaArchivo, String lista, int puntos){
        try{
            File directorio = new File(rutaArchivo);
            File archivo = new File(directorio,"puntuaciones_todas.txt");

            FileWriter escritor = new FileWriter(archivo,true);
            BufferedWriter escritorBuff = new BufferedWriter(escritor);

            escritorBuff.write(lista+"$"+puntos);

            escritorBuff.close(); 

        }catch(IOException ioe){
                System.out.println("Error en la lectura del archivo");
        }
    }

    /**
     * Metodo que se muestra el contenido en un archivo txt.
     * @param rutaArchivo Es la ruta que tiene el archivo.
     * */
    public void muestraTodosPuntos(String rutaArchivo){
        String linea = null;     
        try{
            Reader reader = new FileReader(rutaArchivo);                               
            BufferedReader lector = new BufferedReader(reader);                        
            while((linea = lector.readLine()) != null){
                String[] cadena=linea.split("[$]");
                System.out.println("Letras: "+cadena[0]+"    Puntuacion: "+cadena[1]);                                                                     
            }   
            lector.close();            
        }catch(FileNotFoundException fnfe){
            System.out.println("No se encuentra el archivo");
        }catch(IOException ioe){
            System.out.println("Error en la lectura del archivo");
        }
    }

    /**
     * Metodo que se encarga de escribir las puntuaciones mas altas en un archivo txt.
     * @param rutaArchivo Es la ruta que tiene el archivo.
     * @param lista Es la lista de letras del juego.
     * @param puntos Es la puntuación del juego.
     * */
    public void escribePuntosMayor(String rutaArchivo, String lista, int puntos){

        int mayor = 0, medio = 0, menor = 0, tamanio=0;


        if(puntoSt[0]!=0){
            mayor = puntoSt[0];
            tamanio++;

            if(puntoSt[1]!=0){
                medio = puntoSt[1];
                tamanio++;

                if(puntoSt[2]!=0){
                    menor = puntoSt[2];
                    tamanio++;
                }
            }
        }

        if(tamanio == 3){
            if(puntos>menor){
                if(puntos>medio){
                    if(puntos>mayor){
                        listaS[2] = listaS[1];
                        puntoSt[2] = puntoSt[1];
                        listaS[1] = listaS[0];
                        puntoSt[1] = puntoSt[0];
                        listaS[0] = lista;
                        puntoSt[0] = puntos;
                    }
                } else {
                    listaS[2] = listaS[1];
                    puntoSt[2] = puntoSt[1];
                    listaS[1] = lista;
                    puntoSt[1] = puntos;
                }
            }else{
                listaS[2] = lista;
                puntoSt[2] = puntos;
            }
        }

        if(tamanio == 2){
            if(puntos>medio){
                if(puntos>mayor){
                    listaS[2] = listaS[1];
                    puntoSt[2] = puntoSt[1];
                    listaS[1] = listaS[0];
                    puntoSt[1] = puntoSt[0];
                    listaS[0] = lista;
                    puntoSt[0] = puntos;
                    tamanio++;
                }
            } else {
                listaS[2] = lista;
                puntoSt[2] = puntos;
                tamanio++;
            } 
        }

        if(tamanio == 1){
            if(puntos>mayor){
                listaS[1] = listaS[0];
                puntoSt[1] = puntoSt[0];
                listaS[0] = lista;
                puntoSt[0] = puntos;
                tamanio++;
            }else{
                listaS[1] = lista;
                puntoSt[1] = puntos;
                tamanio++;
            }
        }

        String directorioA = rutaArchivo;

        try{
            File directorio = new File(directorioA);
            File archivo = new File(directorio,"puntuacion_mas_alta.txt");

            FileWriter escritor = new FileWriter(archivo);
            BufferedWriter escritorBuff = new BufferedWriter(escritor);

            for(int i=0; i<tamanio ; i++){
                escritorBuff.write(listaS[i]+"$"+puntoSt[i]+"\n");            
            }  
            escritorBuff.close();     

        }catch(IOException ioe){
            System.out.println("Error en la lectura del archivo");
        }
    }

    /**
     * Método que lee puntuaciones.
     * @param rutaArchivo la ruta del archivo txt.
     * @param lista La lista de letras validas dentro del juego.
     * @param puntos Los puntos obtenidos despues del juego.
     * */
    public void leePuntosMay(String rutaArchivo){

        int cont1 = 0 , cont2 = 0;
        String linea = null;     

        try{
            Reader reader = new FileReader(rutaArchivo);                                //Reader permite leer el archivo como un objeto de tipo File
            BufferedReader lector = new BufferedReader(reader);                         //BufferedReader permite leer el contenido en forma de cadenas 
            while((linea = lector.readLine()) != null){                                 //es por eso que el buffer recibe como parametro el reader anterior
                if(linea != null){                                                      //Para toma los datos de ese objeto para ser leidos se usa el método readLine().  
                    String[] cadena = linea.split("[$]");                               //split Corta las lineas de codigo cada que encuentra un $.
                    listaS[cont1++] = (cadena[0]); //RECORDATORIO: Integer.parseInt(cadena[1]) ---- Para Que convierta la cadena en un entero.  
                    puntoSt[cont2++] = (Integer.parseInt(cadena[1]));
                }                                                                       
            }   
            lector.close();            
        }catch(FileNotFoundException fnfe){
            System.out.println("No se encuentra el archivo");
        }catch(IOException ioe){
            System.out.println("Error en la lectura del archivo");
        }
    }



    public static void main(String[] args){

        String directorio = "src/fciencias/edatos/proyecto03/palabras_todas.txt";
        String cyan = "\u001B[36m", blanco = "\u001B[37m", rojo = "\u001B[31m"; 
        AbstractHashMap<String, String> map = new AbstractHashMap<>(109631577);
        Scanner sc = new Scanner(System.in);
        Juego a = new Juego();
        
        int opc = 0;

        System.out.println(cyan+"PROYECTO 03 --- Juego de vocabulario."+blanco+"\n\n"+cyan+"Un grupo de amigos estaba discutiendo sobre quién tenı́a mayor vocabulario,"+
                            "ası́"+blanco+"\n"+cyan+"que decidieron competir de la siguiente manera: "+blanco+"\n\n"+cyan+"A partir de una secuencia "+
                            "aleatoria de 9 letras, tenı́an un minuto para escribir"+blanco+"\n"+cyan+"la mayor cantidad de palabras."+
                            " Mientras más larga la palabra, más puntos valı́a"+blanco+"\n"+cyan+"de tal forma que al final"+
                            " del minuto el que tenga la puntuación más alta serı́a el ganador."+blanco);

        System.out.println("\n──▒▒▒▒▒▒───▄████▄\n─▒─▄▒─▄▒──███▄█▀ \n─▒▒▒▒▒▒▒─▐████──█─█ \n─▒▒▒▒▒▒▒──█████▄ \n─▒─▒─▒─▒───▀████▀ \n");

        /* CARGA LOS DATOS DEL TXT */               
        map.elementos(directorio, map);

        System.out.println("\nPresiona Enter para comenzar.");
        sc.nextLine();
        
        /* SELECCIONAR LAS LETRAS CON LAS QUE SE VA A JUGAR */
        do{      
            try{
                
                if(opc!=1 && opc!=2 && opc!=0)
                    System.out.println(rojo+"OPCION NO VALIDA"+blanco+"\n");
                else{
                    System.out.println("El juego nos dá la posibilidad de elegir nuestras propias letras con las que jugar.\n"+
                                        "O quizá prefieras que la maquina escoja por tí. \n\n"+
                                        cyan+"     1) "+rojo+"Escoger yo mis propias letras. "+cyan+"(Recomendado)"+blanco+"\n"+
                                        cyan+"     2) "+rojo+"Que la maquina escoja las letras del juego."+blanco+"\n");
                }

                opc = sc.nextInt();
                
            }catch(InputMismatchException ime){
                    System.out.println(rojo+"ERROR: Ingresa un numero."+blanco);                    
            }
        }while(opc!=1 && opc!=2);

        String[] listaLetras = new String[9];

        if(opc==2){
            System.out.println("Cuando estés listo preciona enter");
            sc.nextLine();
            sc.nextLine();
            listaLetras = seleccionLetras(opc);
        }else {listaLetras = seleccionLetras(opc);}
        
        System.out.println("\nCOMIENZA EL JUEGO\n");

        /*CRONOMETRO Y DONDE EL USUARIO DA LAS PALABRAS*/
        a.comienza();
        cronometro();        
        
        /* Copia la lista de palabras en una nueva reducida*/
        System.out.println("\nTiempo terminado presiona enter para continuar");
        sc.nextLine();
        sc.nextLine();
        String[] copiaReducida = Arrays.copyOfRange(juegoPal,0,numP);

        /* PUNTUACIÓN POR LAS PALABRAS DEL USUARIO */
        int puntuacion = validaPalabras(copiaReducida, listaLetras, map);

        try{
            Thread.sleep(3000);
        }catch(InterruptedException ie){}

        /* String de letras para agregar a las estadisticas */
        String lista = "";
        for(int i=0; i<9 ;i++)
            lista = lista+listaLetras[i] + " ";

        /* Se encarga de leer y escribir los mejores puntuajes */
        a.leePuntosMay("src/fciencias/edatos/proyecto03/puntuacion_mas_alta.txt");
        a.escribePuntosMayor("src/fciencias/edatos/proyecto03",lista,puntuacion);

        a.cargaTodosPuntos("src/fciencias/edatos/proyecto03/puntuaciones_todas.txt",todo);
        a.escribeTodosPuntos("src/fciencias/edatos/proyecto03",lista,puntuacion);

        System.out.println("\nMEJORES PUNTUAJES\n");
        for(int i=0; i<3; i++)
            System.out.println("Letras: "+listaS[i]+"    Puntuacion: "+puntoSt[i]);        

        try{
            Thread.sleep(3000);
        }catch(InterruptedException ie){}


        /* Pregunta si quiere ver el historial de puntuaciones */
        do{      
            try{
                System.out.println("\n\n"+cyan+"1) "+rojo+"Mostrar historial de puntuaciones"+blanco+"\n"+
                           cyan+"2) "+rojo+"Salir del programa"+blanco+"\n");
                opc = sc.nextInt();
                
                switch(opc){

                    case 1:                        
                        a.muestraTodosPuntos("src/fciencias/edatos/proyecto03/puntuaciones_todas.txt");
                        break;
                    case 2:
                        break;
                }
            }catch(InputMismatchException ime){
                    System.out.println(rojo+"ERROR: Ingresa un numero."+blanco);                    
            }
        }while(opc!=2); 
    }
}