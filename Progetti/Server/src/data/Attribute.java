package data;

import java.io.Serializable;

/**
 * Attribute
 * classe che rappresenta la definizione di un'attributo come coppia nome-indice
 */
public abstract class Attribute implements Serializable {

    private String name;
    private int index;

    /**
     * costruttore della classe
     * @param name : nome che identifica l'attributo
     * @param index : indice numerico identificativo dell'attributo
     */
    
     Attribute(String name, int index){
        this.name = new String(name);
        this.index = index;  
        
        
    }

    /**
     * restituisce l'attributo name della classe
     * @return attributo name
     */
    public String getName(){
        return name;
    }

    /**
     * restituisce l'attributo index della classe
     * @return attributo index
     */
    public int getIndex(){
        return index;
    }
    
    /**
     * restituisce l'attributo name sotto forma di stringa
     * @return stringa contenentr l'attributo name
     */
    public String toString(){
        return name;
    }
}