package database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;
import exception.DatabaseConnectionException;
import exception.EmptySetException;
import exception.NoValueException;


public class TableData {

	private DbAccess db; // oggetto utile per manipolare una connessione ad un database

	public enum QUERY_TYPE { // identifica il tipo dell'interrogazione aggregata da svolgere
		MIN, MAX
	}
	/**
	 * Permette di modificare l'attributo db della classe
	 * @param db : oggetto utile per manipolare una connnessione ad un database
	 */
	
	public TableData(DbAccess db) {
		this.db=db;
	}

/**
 *  Ricava lo schema della tabella con nome table. Esegue una interrogazione  per estrarre le tuple distinte da tale tabella.
 * @param table : nome della tabella del database di cui ottenere tutte le transazioni distinte
 * @return lista di transazioni distinte memorizzate nella tabella
 * @throws SQLException un eccezione che restituisce errori da sql
 * @throws EmptySetException eccezione controllata da considerare qualora avessimo la restituzione di un resultset vuoto.
 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException{
		
		try {
			db.initConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
				| DatabaseConnectionException e) { 
					e.printStackTrace();
		}
		List<Example> results = new ArrayList<Example>();
		TableSchema tableSchema = new TableSchema(db,table);

	
		String queryString = "SELECT DISTINCT * FROM "+ table;
		Statement statement = db.getConnection().createStatement();
		
		ResultSet rSet = statement.executeQuery(queryString);
		if (rSet == null){
			throw new EmptySetException("Il result set é vuoto"); 
		}

		while(rSet.next()){
			
			Example tupExample = new Example();

			for(int i = 0; i < tableSchema.getNumberOfAttributes(); i++){
				if (tableSchema.getColumn(i).isNumber()) {
					tupExample.add(rSet.getFloat(i+1)); 
				} else{
					tupExample.add(rSet.getString(i+1)); 
				}
			}
			results.add(tupExample);
			
		}
		rSet.close();
		statement.close();
		
		
		return results;
	}

/**
 * 
 * @param table : nome della tabella
 * @param column : nome della colonna della tabella di cui estrarre i valori distinti
 * @return Insieme di valori distinti ordinati in modalità ascendente che l’attributo identificato da nome column 
 * assume nella tabella identificata dal nome table
 * @throws SQLException un eccezione che restituisce errori da sql
 * @throws NoValueException eccezione controllata da considerare qualora ci fosse 
 * l’assenza di un valore all’interno di un resultset.
 */
	public  Set<Object> getDistinctColumnValues(String table,Column column) throws SQLException, NoValueException{

		Set<Object> result = new TreeSet<Object>();
		try {
			db.initConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
				| DatabaseConnectionException e) { 
			e.printStackTrace();
		}
		
		Statement s = db.getConnection().createStatement();
		ResultSet r = s.executeQuery("SELECT DISTINCT " + column.getColumnName() + " FROM " + table + " ORDER BY "+ column.getColumnName()+" ASC");
	
		while(r.next())
		{
			if(r.getString(1)==null)
			{
				s.close();
				r.close();
				db.closeConnection();
				throw new NoValueException("Nessun valore"); 
			}
			else if(column.isNumber()){
				result.add(r.getFloat(1));
			} else{
				result.add(r.getString(1));
			}
		}

		s.close();
		r.close();
		db.closeConnection();
		

		return result;
	}
/**
 * Formula ed esegue una interrogazione SQL per estrarre il valore aggregato (valore minimo o valore massimo) 
 * cercato nella colonna di nome column della tabella di nome table. 
 * @param table : nome della tabella
 * @param column : nome della colonna della tabella 
 * @param aggregate : tipo di query aggregata da svolgere sulla tabella
 * @return aggregato cercato
 * @throws SQLException un eccezione che restituisce errori da sql
 * @throws NoValueException eccezione controllata da considerare qualora ci fosse 
 * l’assenza di un valore all’interno di un resultset.
 */
	public  Object getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate) throws SQLException,NoValueException{
		Object result = new Object();

		try {
			db.initConnection();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
				| DatabaseConnectionException e) { 
			e.printStackTrace();
		}
		
		Statement s = db.getConnection().createStatement();
		ResultSet r = s.executeQuery("SELECT " + aggregate + "(" + column.getColumnName() + ") FROM " + table);

		while (r.next()) {
			
			if(r.getString(1)==null)
			{
				s.close();
				r.close();
				db.closeConnection();
				throw new NoValueException("Nessun valore"); 
			}
			else if(column.isNumber()){
				result = r.getFloat(1);
			} else{
				result = r.getString(1);
			}
		}
		s.close();
		r.close();
		db.closeConnection();

		return result;
	}

}
