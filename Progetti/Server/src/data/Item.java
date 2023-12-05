package data;
import java.io.Serializable;
import java.util.HashSet;



/**
 * Item
 * classe astratta che modella un generico Item (coppia attributo-valore)
 */


abstract public class Item implements Serializable{

   private Attribute attribute; //attributo coinvolto nell'item
   private Object value; // valore assegnato all'attributo

    /**
     * costruttore della classe. inizializza gli attributi coi parametri dati in input
     * @param attribute : attributo coinvolto nell'item
     * @param value : valore assegnato all'attributo
     */

    Item(Attribute attribute, Object value){

        this.attribute = attribute;
        this.value = value;
        
    }

    /**
     * restituisce l'attributo attribute dell'oggetto
     * @return attribute
     */
    public Attribute getAttribute(){
        return attribute;
    }

    /**
     * restituisce l'attributo value dell'oggetto
     * @return value
     */
    public Object getValue(){
       
        return value;
    }

    /**
     * restituisce l'attributo value dell'oggetto sotto forma di stringa
     * @return stringa contenente value
     */
    public String toString(){
        String str ="" +getValue();
        return str;
    }

    
 
    
 
    /**
     * metodo astratto, implementazione diversa per item continuo e item discreto
     * @param a :  valore con cui calcolare la distanza
     * @return  numero rappresentante la distanza
     */
    abstract double distance(Object a);

    /**
     * Modifica il membro value, assegnandogli il valore 
     *   restituito da data.computePrototype(clusteredData,attribute)
     * @param data :  riferimento ad un oggetto della classe Data
     * @param clusteredData : insieme di indici delle righe della matrice in data che formano il cluster

     */
    public void update(Data data, HashSet<Integer> clusteredData){
        value = data.ComputePrototype(clusteredData,attribute);
    }

    

    
}