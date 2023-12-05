package exception;


/**
 * ServerException estende Exception classe 
 * che modella una  eccezione controllata da considerare 
 * qualora fallisse la connessione al server.
 */
public class ServerException extends Exception {

      /**
     * Costruttore della classe. Chiama il costruttore della superclasse
     * @param message : stringa che rappresenta il significato dell'eccezione lanciata
     */
    public ServerException(String message) {
        super(message);
    }

    
}
