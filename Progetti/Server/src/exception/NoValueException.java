package exception;



/**
 * NoValueException estende Exception, classe che modella una  eccezione controllata da considerare qualora ci fosse 
 * l’assenza di un valore all’interno di un resultset.
 */
public class NoValueException extends Exception {
    
    /**
     * costruttore della classe. Chiama il costruttore della superclasse
     * @param message : stringa che rappresenta il significato dell'eccezione lanciata
     */
    
     public NoValueException(String message) {
        super(message);
    }

}
