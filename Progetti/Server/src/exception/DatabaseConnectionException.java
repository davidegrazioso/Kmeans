package exception;


/**
 * DatabaseConnectionException estende Exception classe 
 * che modella una  eccezione controllata da considerare 
 * qualora fallisse la connessione al database.
 */
public class DatabaseConnectionException extends Exception {
    
    /**
     * costruttore della classe. Chiama il costruttore della superclasse
     * @param message : stringa che rappresenta il significato dell'eccezione lanciata
     */
    
     public DatabaseConnectionException(String message) {
        super(message);
    }

}
