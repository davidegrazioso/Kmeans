package exception;

/**
 * OutOfRangeSampleSize
 * Estende Exception
 * classe che modella una  eccezione controllata da considerare qualora il numero k di cluster inserito 
 * da tastiera è maggiore maggiore rispetto al numero di transazioni disponibili
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
