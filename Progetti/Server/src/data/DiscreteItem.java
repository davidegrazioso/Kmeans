package data;

/**
 * DiscreteItem:
 * classe che estende Item, rappresenta un Item discreto (coppia DiscreteAttribute-valore)
 */

public class DiscreteItem extends Item{

    /**
     * Costruttore della classe. Invoca il costruttore della classe madre
     * @param attribute : attributo discreto coinvolto nell'item
     * @param value : valore assegnato all'attributo
     */
   
    DiscreteItem(DiscreteAttribute attribute, String value){

        super((DiscreteAttribute)attribute,(String) value);
        
    }
    
    /**
     * Restituisce 0 se (getValue().equals(a)) , 1 altrimenti
     * @param a : oggetto su cui verificare la condizione
     * @return 0 se (getValue().equals(a)) , 1 altrimenti
     */
    public double distance(Object a){
       
        return getValue().equals(a) ?  0 : 1; 
    }

}
