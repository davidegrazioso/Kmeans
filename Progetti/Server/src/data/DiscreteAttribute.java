package data;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * DiscreteAttribute:
 * estende Attribute e rappresenta un attributo categorico, ovvero un attributo non numerico che può assumere
 * solo una serie di valori discreti definiti 
 * 
 */


public class DiscreteAttribute extends Attribute implements Iterable<String>{
    private TreeSet<String> values;

    /**
     * Costruttore della classe. Invoca il costruttore della classe madre e inizializza il membro values coi parametri dati in
     * input.
     * @param name : nome identificativo dell'attributo
     * @param index : indice numerico identificativo dell'attributo
     * @param values : insieme dei possibili valori discreti assumibili da un'occorrenza dell'attributo
     */

    DiscreteAttribute(String name, int index, TreeSet<String> values){
        super(name,index);
        this.values = new TreeSet<String>(values);
    }

    /**
     * restituisce il numero di elementi presenti nell'array values
     * @return lunghezza dell'array values
     */
    public int getNumberOfDistinctValues(){
        
        return values.size();
    }

    
    /** 
     * Restituisce l'iteratore per la classe
     * @return Iteratore
     */
    public Iterator<String> iterator(){
        return values.iterator();
    }
   
    

    /**
     * Determina il numero di volte che il valore v compare  in corrispondenza dell'attributo corrente (indice di colonna) negli 
     * esempi memorizzati in data e indicizzate (per riga) da idList
     * 
     * @param data : insieme di transazioni su cui determinare la frequenza
     * @param idList :  arraySet indicante la presenza della transazione nel cluster
     * @param val :  valore di cui trovare la frequenza nella tabella
     * @return frequenza del valore v nella matrice "data"
     */
    public int Frequency(Data data, HashSet<Integer> idList, String val){
        
        int freq = 0;
        for(int i=0; i < data.getNumberOfExamples(); i++){

            if(idList.contains(i)) {

                if(val.equals(data.getAttributeValue(i,this.getIndex()))) {
               
                 freq++;    
                }

            }
        }
        return freq;
    }

}

