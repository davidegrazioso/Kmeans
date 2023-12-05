
package data;
/**
 * ContinuousAttribute: classe che rappresenta un attributo "continuo" ovvero che assume valori numerici
 * estende la classe Attribute aggiungendo due attributi che indicano il minimo valore e il massimo valore assumibili
 * dall'attribute
 */

public class ContinuousAttribute extends Attribute{
    private double max;
    private double min;
    

    /**
     * costruttore della classe. Chiama il costruttore della superclasse e inizializza gli attributi aggiunti per estensione
     *  coi valori dati in input
     * @param name : nome identificativo dell'attributo
     * @param index : indice numerico identificativo dell'attributo
     * @param min : valore minimo assumibile da un'occorrenza dell'attributo
     * @param max : valore massimo assumibile da un'occorrenza dell'attributo
     */
    
    ContinuousAttribute(String name, int index, double min, double max){
        super(name,index);
        this.max = max;
        this.min = min;
    }
    
    /**
     * Calcola e restituisce il valore normalizzato del parametro 
     *  passato in input. La normalizzazione ha come codominio lo intervallo [0,1]. 
     * 
     * @param v : valore del parametro da normalizzare
     * @return valore normalizzato del parametro (compreso tra 0 e 1)
     */
    public double getScaledValue(double v){
        return (v - min) / (max - min);
    }

    
}
