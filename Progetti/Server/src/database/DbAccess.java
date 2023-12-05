package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exception.DatabaseConnectionException;

/**
 * DbAccess
 * classe che permette l'accesso al database
 */
public class DbAccess {
    
    String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver"; //nome del driver per accedere al DBMS
    final String DBMS = "jdbc:mysql"; //nome del DBMS attraverso il quale si accede al database
    final String SERVER ="localhost"; //nome del server
    final String DATABASE = "MapDB"; // nome della base di dati
    final String PORT= "3306"; // identificativo della porta su cui avviene la connessione
    final String USER_ID = "MapUser"; // identificativo dell'utente che deve accedere al database
    final String PASSWORD = "map"; // password dell'utente identificato da USER_ID
    Connection conn;   // oggetto che serve a gestire la connessione col database

    /**
     * metodo utile per aprire una connessione col database management system
     * @throws DatabaseConnectionException eccezione controllata da considerare 
     * qualora fallisse la connessione al database.
     * @throws InstantiationException eccezione sollevata quando si vuole instanziare un nuovo oggetto
     * ma non si riesce.
     * @throws IllegalAccessException eccezione sollevata quando si crea un istanza o si prova a chiamare metodi
     * ma non si ha l'accesso.
     * @throws ClassNotFoundException considera un errore in cui non si trova nessuna classe con uno specifico nome
     * @throws SQLException un eccezione che restituisce errori da sql
     */
    void initConnection() throws DatabaseConnectionException, 
    InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        
        try {
			Class.forName(DRIVER_CLASS_NAME).newInstance();
		} catch(ClassNotFoundException e) {
			System.out.println("[!] Driver not found: " + e.getMessage());
			throw new DatabaseConnectionException("Error");
		} catch(InstantiationException e){
			System.out.println("[!] Error during the instantiation : " + e.getMessage());
			throw new DatabaseConnectionException("error");
		} catch(IllegalAccessException e){
			System.out.println("[!] Cannot access the driver : " + e.getMessage());
			throw new DatabaseConnectionException("error");
		}
		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
					+ "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";

		try {			
			conn = DriverManager.getConnection(connectionString);
		} catch(SQLException e) {
			System.out.println("[!] SQLException: " + e.getMessage());
			System.out.println("[!] SQLState: " + e.getSQLState());
			System.out.println("[!] VendorError: " + e.getErrorCode());
			throw new DatabaseConnectionException("Error: " + e.getErrorCode());
		}
        
    }

    /**
     * Restituisce l' oggetto Connection
     * @return oggetto di tipo Connection utile a gestire la connessione col database
     */
    Connection getConnection(){
        return conn;
    }

    /**
     * chiude la connessione col database
     */
    void closeConnection(){

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
