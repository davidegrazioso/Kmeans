package data;

import java.io.Serializable;

/**
 * Tuple:
 * Classe che rappresenta una tupla come sequenza di coppie attributo-valore
 */
public class Tuple implements Serializable {
    
    private Item[] tuple; 

/**
 * Costruttore della classe
 * @param size : lunghezza della tupla
 */
    Tuple(int size){

        tuple = new Item[size];

    }

/**
 * Restituisce la lunghezza della tupla
 * @return  Lunghezza della tupla
 */
    public int getLength(){
        return tuple.length;
    }

/**
 * Restituisce l'item in posizione i
 * @return item nella posizione i-esima
 */
    public Item get(int i){
        
        return  tuple[i];
    }

    /**
     * aggiunge l'item dato in input alla posizione i-esima dell'array
     * @param c item da aggiungere
     * @param i posizione in cui aggiungere l'item
     */
    public void add(Item c,int i){
        tuple[i] = c; 
    }

    /** 
     * Determina la distanza tra la tupla riferita dal parametro obj e la tupla
     * corrente, distanza ottenuta come somma delle distanze tra gli item in posizioni uuguali
     * @param obj : oggetto a item a cui si fa riferimento
     * @return distance : somma fra la distanza di item in posizioni uguali
     *  
    */
    public double getDistance(Tuple obj){
        double distance = 0;

        for(int i = 0; i < tuple.length;i++){
            distance += tuple[i].distance(obj.get(i).getValue());
            
        } 
        
        return distance;

    }
/**
 *   restituisce la media delle distanze tra la tupla 
 *  corrente e quelle ottenibili dalle righe della matrice in data aventi indice in  clusteredData
 * 
 * @param data : insieme di transazioni in cui sono presenti i dati da analizzare
 * @param array : insieme di indici delle righe del cluster da analizzare
 * @return  distanza media calcolata sul cluster
 */

    public double avgDistance(Data data, Object[] array){

        double p=0.0,sumD=0.0;
        for(int i=0;i<array.length;i++){
            double d= getDistance(data.getItemSet((int)array[i]));
            sumD+=d;
        }
        p=sumD/array.length;

        return p;   

    }

}
