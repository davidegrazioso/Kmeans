package exception;


/**
 * EmptySetExprection estende Exception, classe che modella una  eccezione
 * controllata da considerare qualora avessimo
 * la restituzione di un resultset vuoto.
 */
public class EmptySetException extends Exception{
    
    /**
     * costruttore della classe. Chiama il costruttore della superclasse
     * @param message : stringa che rappresenta il significato dell'eccezione lanciata
     */
    
     public EmptySetException(String message) {
        super(message);
    }

}
