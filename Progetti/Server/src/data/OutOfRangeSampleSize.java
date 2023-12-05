package data;

/**
 * OutOfRangeSampleSize
 * Estende Exception
 * classe che modella una  eccezione controllata da considerare qualora il numero k di cluster inserito 
 * da tastiera è maggiore maggiore rispetto al numero di centroidi generabili 
 * dall'insieme di transazioni.
 * In tale case l'oggetto eccezione va creato e sollevato nella implementazione del metodo sampling(...).
 * 
 */

public class OutOfRangeSampleSize extends Exception{


    /**
     * costruttore della classe. Chiama il costruttore della superclasse
     * @param message : stringa che rappresenta il significato dell'eccezione lanciata
     */
    
    public OutOfRangeSampleSize(String message) {
        super(message);
    }

}
